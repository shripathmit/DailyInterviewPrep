# Paxos — Study Notes

Paxos is a family of distributed consensus algorithms designed to solve one
foundational problem: enabling a network of untrusted, fallible machines to
agree on a single value or sequence of actions — even when messages are delayed,
reordered, or lost, and machines periodically crash.

Formulated by **Leslie Lamport**, Paxos underpins almost every major cloud
control plane and storage system, including Google Chubby and Google Spanner.

## Why consensus is hard: the core dilemma

If networks never partitioned and nodes never died, consensus would be trivial.
Real distributed environments face severe constraints:

- **Asynchronous networks.** There is no shared global clock and no upper bound
  on network latency. You cannot distinguish a server that has crashed from one
  that is simply responding slowly — so you can never safely "wait for everyone."
- **Brain-split risk.** If two nodes independently decide they are in charge,
  they issue contradictory updates, corrupting databases and control-plane state.

## The quorum invariant: why split-brain is impossible

The heart of Paxos is the **Pigeonhole Principle applied to majorities**.

Take a 5-node cluster: {A, B, C, D, E}. A strict majority needs ⌊5/2⌋ + 1 = 3 nodes.

- Quorum 1: {A, B, C}
- Quorum 2: {C, D, E}

Node C belongs to both. **Any two majorities must intersect in at least one node.**
In a cluster of **2F + 1** nodes, the system tolerates up to **F** simultaneous
crashes — and because any two quorums overlap, the overlapping node ensures two
conflicting decisions can never both reach a quorum.

Paxos leverages the intersection through a strict acceptor rule: **acceptors are
stateful and reject the past.** When a proposer prepares proposal n₂ (n₂ > n₁), it
queries a majority. That majority overlaps with whoever accepted proposal n₁, so
at least one node speaks up:

> "I already accepted value v at round n₁."

The newer proposer is **forced to adopt v** instead of its own value. Consequence:
once a value reaches a majority, no conflicting value can ever be chosen. The
decision is locked for eternity. One-sentence version: *a later round can never
overturn a value a majority already accepted, because every majority intersects
and acceptors remember.*

## The three roles

A node can play one or more of three roles:

- **Proposers** — advocate for a client request (e.g., "set VM state to RUNNING").
- **Acceptors** — the consensus memory and voting body. They store promises and
  accepted values **on disk** (durability is load-bearing: the safety proof
  collapses if an acceptor forgets).
- **Learners** — observers that execute or read the decided value once consensus
  is locked.

## How Basic Paxos works: the two phases

Basic Paxos decides a **single value** across two phases (2 network round trips).

```
Proposer                       Acceptor Quorum
   │                                  │
   │─── Phase 1a: Prepare(n) ────────>│  (n = unique, increasing proposal number)
   │<── Phase 1b: Promise(max_v) ─────│  ("I won't accept anything < n;
   │                                  │    here is what I accepted before")
   │                                  │
   │─── Phase 2a: Accept(n, value) ──>│  (own value, or the previously
   │<── Phase 2b: Accepted! ──────────│   accepted value if one was returned)
   ▼                                  ▼
```

**Phase 1 — Prepare and Promise.**

- *Phase 1a (Prepare):* the proposer picks a unique, monotonically increasing
  number `n` and broadcasts `Prepare(n)` to a majority of acceptors.
- *Phase 1b (Promise):* each acceptor checks whether it has seen a proposal
  number higher than `n`. If not, it replies with a promise: it will **reject all
  future proposals numbered lower than `n`**, and it returns the highest-numbered
  proposal and value it has already accepted, if any.

**Phase 2 — Accept and Commit.**

- *Phase 2a (Accept):* once the proposer has promises from a quorum, it chooses
  a value. **If any acceptor returned a previously accepted value in Phase 1b,
  the proposer must adopt that existing value** — this is the rule that enforces
  the quorum invariant. Only if no previous values were returned may it use its
  own client's value. It broadcasts `Accept(n, value)`.
- *Phase 2b (Accepted):* acceptors receive `Accept(n, value)`. As long as an
  acceptor hasn't promised to a higher proposal number in the interim, it accepts
  the value and notifies the proposer and learners. The value is committed.

## The flaw of Basic Paxos: the livelock duel

Basic Paxos survives node crashes but has an Achilles' heel: **dueling proposers**.

```
P₁ sends Prepare(n=1) to a quorum.   Acceptors promise: nothing < 1.
P₂ sends Prepare(n=2) to a quorum.   Acceptors promise: nothing < 2.
P₁ sends Accept(n=1).                Rejected — acceptors promised ≥ 2.
P₁ retries: Prepare(n=3).            Acceptors promise: nothing < 3.
P₂ sends Accept(n=2).                Rejected — acceptors promised ≥ 3.
P₂ retries: Prepare(n=4).            ...and the cycle repeats forever.
```

No node crashes. Zero message loss. **Zero progress.** The system starves — a
livelock, not a deadlock. Basic Paxos guarantees safety (never a wrong decision)
but **cannot guarantee liveness** under contention. This is the FLP impossibility
result showing up in practice: in an asynchronous network, no deterministic
algorithm guarantees both safety and liveness.

## Multi-Paxos: from one decision to a high-speed log

Basic Paxos decides a single variable. Real systems need an append-only log of
thousands of operations per second (state machine replication) — running two
round trips per log entry is intolerable, and dueling proposers livelock.

Production systems implement **Multi-Paxos**, which collapses the algorithm into
two distinct stages:

```
[ Phase 1: Leadership Acquisition ]
  - Executed ONCE, at startup or failover.
  - Elects a single "Distinguished Leader" proposer.
  - Establishes acceptor promises for an infinite stream of future log indices.

                  │
                  ▼  (normal operation)

[ Phase 2: High-Speed Replicated Write Stream ]
  - Client writes hit the leader directly.
  - Leader appends the entry to its local log at index i.
  - Leader broadcasts: Accept(index=i, value=V).
  - Quorum replies: Accepted(index=i).
  - Leader commits and notifies the client.  → 1 RTT total.
```

Phase 1 (the expensive prepare/promise round) is amortized across the leader's
entire tenure; Phase 2 is a pure pipeline at **one round trip per write**.
Decoupling election from transaction processing turns consensus from a
per-decision negotiation into a continuous replicated log.

**Raft** makes this leader-centric model the explicit core abstraction from the
start — which is why Raft is usually considered easier to understand than raw
Paxos. Same destination, clearer map.

## Production realities: what the pseudocode leaves out

**A. Leader leases and brain-split prevention.**
If the leader suffers a GC pause or network hiccup, the cluster may elect a new
one. If the old leader wakes up and serves reads/writes before realizing it was
deposed, you get stale or split-brain state. The fix: **leader leases**.

- The leader holds a **time-bounded lease** granted by the quorum.
- Acceptors promise not to vote for a new leader until the lease expires.
- The leader renews the lease periodically; if communication breaks, the cluster
  waits out the lease before electing. (Same pattern as TrueTime's commit wait:
  a time bound used as a correctness tool, not an optimization.)

**B. Log compaction.**
A replicated log can't grow forever — a rebooting node can't replay 10 billion
entries. Systems periodically take a **consistent snapshot** of the state machine
(e.g., the full VM inventory), persist it durably, then **garbage-collect and
truncate** all earlier log entries. Snapshot + truncated suffix = the whole state.

**C. Asymmetric quorums and multi-region placement.**
Five replicas across continents means WAN latency on every write. Production
designs cheat with **witness replicas / flexible quorums**: 3 voting replicas sit
close together for low-latency consensus, while async followers stream the log
cross-region for disaster recovery — off the hot path, never blocking commits.

## Paxos in the GCE control plane

In hyper-scale systems like Google Cloud, Paxos is used wherever split-brain
states are intolerable:

- **Leader leases** — ensuring only one scheduler or VM-manager instance is
  actively mutating hypervisor state for a given cell.
- **Metadata consistency** — powering replicated transaction logs inside Spanner.
  When a user creates a disk or updates a firewall rule, that mutation commits
  via a Paxos group spanning multiple availability zones.
- **Configuration storage** — cluster membership and routing tables that must
  survive hardware maintenance and regional network isolations without losing
  state.

## Numbers and rules to memorize

- Majority = ⌊N/2⌋ + 1; cluster of 2F + 1 tolerates F crashes
- Any two majorities intersect in ≥ 1 node — the entire safety argument
- Basic Paxos = 2 RTT per decision; Multi-Paxos steady state = **1 RTT**
- Dueling proposers → livelock: safety holds, liveness doesn't (FLP in practice)
- Leader leases bound the split-brain window when a deposed leader wakes up

## Interview traps

- Saying "Paxos elects a leader" — no, **Basic Paxos has no leader**; the
  distinguished leader is a Multi-Paxos optimization. Know which one you're
  describing.
- Confusing safety with liveness: Paxos *never* decides wrong, but Basic Paxos
  can *never decide at all* under contention.
- Forgetting acceptors are stateful and durable — the proof collapses if an
  acceptor forgets past promises (that's why promises live on disk, not RAM).
- "Why not just use a single leader without Paxos?" — because leader election
  itself needs consensus; that's the recursion Phase 1 solves.
- The DynamoDB attribution is loose: the original Dynamo paper (2007) deliberately
  avoided Paxos in favor of sloppy quorums, vector clocks, and anti-entropy.
  Say "Chubby and Spanner" with confidence; say "DynamoDB" only with the caveat.

## One-line summary

Paxos lets fallible machines agree despite lost and reordered messages by forcing
every decision through intersecting majorities of stateful acceptors — then makes
it fast by electing one leader once and pipelining all later writes through it,
with leases and log compaction handling everything the theory hand-waves away.
