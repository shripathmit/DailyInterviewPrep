# Paxos — Study Notes

The engine beneath distributed consensus: the invariant that makes it
indestructible, why Basic Paxos breaks in practice, and how production systems
adapt it for high throughput.

## Concept 1: The quorum invariant — why split-brain is impossible

The heart of Paxos is the **Pigeonhole Principle applied to majorities**.

Take a 5-node cluster: {A, B, C, D, E}. A strict majority needs ⌊5/2⌋ + 1 = 3 nodes.

- Quorum 1: {A, B, C}
- Quorum 2: {C, D, E}

Node C belongs to both. **Any two majorities must intersect in at least one node.**
That overlap is the whole trick.

Paxos leverages the intersection through a strict acceptor rule: **acceptors are
stateful and reject the past.** When a proposer prepares proposal n₂ (n₂ > n₁), it
queries a majority. That majority overlaps with whoever accepted proposal n₁, so
at least one node speaks up:

> "I already accepted value v at round n₁."

The newer proposer is **forced to adopt v** instead of its own value. Consequence:
once a value reaches a majority, no conflicting value can ever be chosen. The
decision is locked for eternity. That's the safety invariant — stated in one
sentence: *a later round can never overturn a value a majority already accepted,
because every majority intersects and acceptors remember.*

## Concept 2: The flaw of Basic Paxos — the livelock duel

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

## Concept 3: Multi-Paxos — separating election from the write path

Production systems kill the duel and cut round-trip latency by collapsing the
algorithm into two distinct stages:

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
entire tenure. Phase 2 is a pure pipeline: **one round trip per write**.
Decoupling election from transaction processing turns consensus from a
per-decision negotiation into a continuous replicated log — which is exactly what
systems like etcd (Raft), Spanner, and Chubby actually run.

## Concept 4: Production realities — what the pseudocode leaves out

**A. Leader leases and brain-split prevention.**
If the leader suffers a GC pause or network hiccup, the cluster may elect a new
one. If the old leader wakes up and serves reads/writes before realizing it was
deposed, you get stale or split-brain state. The fix: **leader leases**.

- The leader holds a **time-bounded lease** granted by the quorum.
- Acceptors promise not to vote for a new leader until the lease expires.
- The leader renews the lease periodically; if communication breaks, the cluster
  waits out the lease before electing. (Notice the parallel with TrueTime's
  commit wait: time bounds used as a correctness tool, not just an optimization.)

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

## Numbers and rules to memorize

- Majority = ⌊N/2⌋ + 1; any two majorities intersect in ≥ 1 node
- Basic Paxos = 2 RTT per decision (prepare + accept); Multi-Paxos steady state = **1 RTT**
- Dueling proposers → livelock: safety holds, liveness doesn't (FLP in practice)
- Leader leases: old leader can't act past lease expiry — bounds the split-brain window

## Interview traps

- Saying "Paxos elects a leader" — no, **Basic Paxos has no leader**; the
  distinguished leader is a Multi-Paxos optimization. Know which one you're
  describing.
- Confusing the safety invariant with the liveness story: Paxos *never* decides
  wrong (safety), but Basic Paxos can *never decide at all* under contention.
- Forgetting acceptors are stateful — the whole proof collapses if acceptors
  don't remember past promises and accepted values (durable storage matters).
- "Why not just use a single leader without Paxos?" — because leader election
  itself needs consensus; that's the recursion Paxos Phase 1 solves.

## One-line summary

Paxos makes conflicting decisions impossible through intersecting majorities and
stateful acceptors, then makes the protocol fast by electing one leader once and
pipelining all later writes through it — with leases and log compaction handling
everything the theory hand-waves away.
