# TrueTime — Study Notes

Google Cloud Spanner's TrueTime: the engineering breakthrough that orders transactions
across the globe without endless coordination locks.

## The problem it solves

In a single machine, ordering events is trivial: timestamp 10:00:00.001 came before
10:00:00.002. In distributed systems this breaks completely:

- Every machine has its own quartz oscillator clock; quartz drifts with temperature,
  voltage, and age — a typical server clock drifts **several seconds per week**.
- NTP periodically syncs clocks over the network, but variable congestion leaves an
  unavoidable uncertainty gap. Two servers in the *same rack* can disagree by tens
  of milliseconds (e.g., one thinks it's 12:00:00.050, the other 12:00:00.010).
- **The dilemma:** you cannot determine causality purely from timestamps. If Tokyo
  updates a firewall rule at its local 10:00:01 and Iowa reads it at its local
  10:00:00, no timestamp comparison proves which happened first — so you'd need an
  expensive consensus round (Paxos / 2PC) across the Pacific.

## Concept 1: Time as an interval, not a point

Most systems treat time as an exact integer. That is fundamentally a lie.

TrueTime treats time as **an interval with bounded uncertainty**. `TT.now()` does not
return a timestamp `t` — it returns a range:

```
        t_earliest                   t_latest
            [─────────────*─────────────]
                          ▲
                   Absolute Time
                   (Somewhere in here)
            |<─────────── 2ε ──────────>|
```

TrueTime **mathematically guarantees** that actual absolute (UTC) time lies inside
that window. The width of the window is `2ε`, where ε is the uncertainty bound.

## Concept 2: Keeping ε small — atomic clocks + GPS

To keep ε tiny (typically **under 7 ms**, often **under 1 ms** in modern Google data
centers), Google built dedicated time infrastructure:

- **Dual reference sources** in every data center cluster's time masters:
  - **GPS receivers** — extremely accurate, but vulnerable to antenna failures,
    satellite sync loss, or jamming.
  - **Rubidium atomic clocks** — independent of external signals, but drift
    predictably over long periods.
- **Cross-checking:** if a GPS antenna fails or drifts, the atomic clock flags the
  discrepancy; if an atomic clock degrades, the GPS receivers overrule it.
- **Worst-case drift guarantees:** if a server loses contact with its time masters,
  it assumes its quartz clock drifts at the **maximum possible rate (200 µs/s)**.
  As time passes without a sync, the server's reported ε **expands dynamically**
  until it resyncs. The guarantee stays honest even when the infrastructure fails.

## Concept 3: Commit Wait — the magic

How does an uncertainty interval produce **strict serializability (external
consistency)** worldwide? Spanner's rule is called **Commit Wait**.

Say transaction T₁ commits, then T₂ starts anywhere on Earth after T₁ finishes.
The system must guarantee T₂'s timestamp is greater than T₁'s. The commit sequence:

```
Step 1: T₁ requests commit.
        Leader queries TrueTime: TT.now() returns [100, 108]  (ε = 4ms).

Step 2: Leader picks the absolute ceiling as the commit timestamp:
        s = 108

Step 3: COMMIT WAIT — the system deliberately sleeps.
        The leader refuses to release locks or return success to the client
        until TrueTime guarantees the present time has passed 108,
        i.e. it waits until TT.now().earliest > 108.

Step 4: Locks released. T₁ is visible to the world.
```

By sleeping for ~2ε (a few milliseconds), the system **forces physical time to pass
the recorded timestamp**. So any T₂ that starts after T₁ is visible gets
`earliest > 108` — its commit timestamp is guaranteed larger.

Causality is preserved globally using **pure timestamps**. No cross-region
coordination at commit time.

## Concept 4: What TrueTime unlocks

- **Lock-free consistent reads.** Read transactions take no locks. They read data
  at a past timestamp `t`; because the storage layer keeps versioned records
  (MVCC), replicas serve strongly consistent reads **locally at full speed** —
  no leader contact, no WAN latency penalty.
- **Consistent global snapshots.** Backups, analytics scans, and DR audits can
  read a consistent snapshot of the *entire world's* state at an exact microsecond
  in history — without freezing ongoing customer mutations.

Without TrueTime, every cross-region read would need distributed locks or a
central authority check — hundreds of milliseconds of WAN latency per query.

## Why not the alternatives?

- **NTP alone:** uncertainty is unbounded in practice; no honest ε to build on.
- **Logical clocks (Lamport / vector):** capture *causality* between events that
  communicate, but not *external real-time ordering* — they can't say "this
  transaction committed at 10:00:00.108 UTC."
- **Spanner's trade-off:** writes pay ~2ε of commit-wait latency (a few ms);
  reads pay nothing and never coordinate. Given read-heavy workloads, that's a
  winning trade.

## Numbers to memorize

- ε typically **< 7 ms**, often **< 1 ms** in modern data centers
- Worst-case quartz drift when disconnected: **200 µs/s** (ε grows dynamically)
- Commit wait ≈ **2ε**

## Interview traps

- TrueTime is **not** "NTP with better hardware." The breakthrough is the
  *interval semantics* plus *commit wait* — honest uncertainty, then waiting it out.
- Timestamps alone don't order anything. The deliberate sleep is what makes the
  guarantee real.
- ε is **dynamic**, not a constant — it expands when a server can't reach its
  time masters. Don't quote 7 ms as a fixed number.
- The term Spanner uses is **external consistency** (strict serializability
  with real-time ordering); know both names.

## One-line summary

TrueTime turns uncertain physical time into a trustworthy interval, then spends a
few milliseconds of commit wait to convert that interval into globally consistent
transaction ordering — with no cross-region coordination at all.
