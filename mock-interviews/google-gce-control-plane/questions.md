# Google — Senior Software Engineering Manager, GCE Control Plane
## Interview Question Bank

Prepared 2026-09-18 for the Kirkland, WA posting. Role themes from the JD: cellular architectures and multi-dimensional sharding, blast-radius containment, customer-perceived telemetry with AI-driven anomaly detection and automated rollbacks, dynamic throttling / adaptive quotas for AI workloads, continuous automated RPO/RTO validation, "Reliable by Design" culture, multi-regional failure prevention, automated continuous compliance.

---

## System Design (5)

**SD1. Design a cellular architecture for the GCE control plane.**
Shift from a global control plane to cells with multi-dimensional sharding. What you must cover: how you choose sharding dimensions (region, customer tier, workload type), blast-radius containment guarantees, what happens when a cell fails, cross-cell coordination and metadata, control plane vs. data plane separation, and how you migrate the existing fleet without downtime.
*Probes: staff-level partitioning judgment, failure-domain design, migration strategy.*

**SD2. Design customer-perceived telemetry with AI-driven anomaly detection and automated rollbacks.**
Move telemetry from machine-centric metrics to customer-perceived sensitivity. Cover: SLI/SLO design from the user's perspective, the anomaly-detection pipeline (features, models, training/serving), how you prevent false positives from triggering rollback storms, canary analysis, and the rollback execution path with safety interlocks.
*Probes: ML-for-ops judgment, safe automation, SLI design.*

**SD3. Design an automated disaster recovery system for control-plane datastores with strict RPO/RTO.**
Cover: backup and restore architecture, regional failover mechanics, and — the part the JD calls out explicitly — continuous automated validation pipelines that *prove* RPO/RTO objectives are met rather than asserted. Include chaos/fault-injection validation and how recovery is rehearsed without customer impact.
*Probes: disaster recovery depth, "trust but verify" culture.*

**SD4. Design dynamic throttling and adaptive quotas for AI workloads.**
Hyper-accelerated AI fleet (GPUs/TPUs) with dynamic capacity assurance. Cover: quota model (static vs. adaptive), admission control and preemption, priority classes, burst handling, fairness across tenants, and how throttling decisions propagate from control plane to the fleet in near-real time.
*Probes: capacity systems, fairness vs. utilization tradeoffs.*

**SD5. Design hitless maintenance / safe rollout for control-plane services.**
Zero-downtime upgrades of the control plane across multiple international sites. Cover: rollout strategies (staged, canary, blue-green at control-plane scale), health gating and automatic halt, version skew between control plane and agents, schema/data migrations, and containing deployment blast radius.
*Probes: release engineering at planetary scale, risk containment.*

---

## Coding (10)

Classic Google-flavored, with two infra-flavored picks tied to the role.

1. **LRU Cache** — implement `get`/`put` in O(1). (LeetCode 146)
2. **Merge Intervals** — merge overlapping intervals; follow-up: insert interval. (56 / 57)
3. **Number of Islands** — grid DFS/BFS; follow-up: count with union-find. (200)
4. **Course Schedule II** — topological sort returning a valid order. (210)
5. **Median of Data Stream** — two-heap design. (295)
6. **Word Ladder** — shortest transformation sequence, BFS. (127)
7. **Trapping Rain Water** — two-pointer optimal. (42)
8. **Serialize and Deserialize Binary Tree** — design the codec. (297)
9. **Top K Frequent Elements** — bucket sort vs. heap tradeoffs. (347)
10. **Token-bucket rate limiter** — implement a thread-safe rate limiter with refill; follow-up: how would you distribute it across control-plane cells? (Role tie-in: dynamic throttling theme from the JD.)

---

## Behavioral (5) — Googleyness + Engineering Management

**B1. Building a reliability culture.** "Tell me about a time you built a reliability culture in an organization that didn't have one." → Maps directly to the JD's "Reliable by Design" mandate. Have the mechanisms ready: SLOs, error budgets, blameless postmortems, what changed in behavior.

**B2. Leading technical transformation.** "Tell me about a time you led a technical transformation across multiple teams — how did you get buy-in?" → Maps to the preferred qual on scaling orgs through growth/transformation. Nautilus decoupling story fits; so does any platform migration.

**B3. Owning a major incident.** "Walk me through the worst production incident you owned. What broke, and what did you change structurally afterward?" → Google's blameless-postmortem culture. They want the systemic fix, not heroics.

**B4. Staff-level judgment call.** "Tell me about a time you chose the slower, more correct technical path over shipping fast — or the reverse. How did you decide?" → Maps to "staff-level technical judgment for large-scale back-end systems."

**B5. Managing managers / rebuilding a team.** "Tell me about a time you managed a struggling manager, or had to turn around an underperforming team." → The JD explicitly prefers experience managing managers. Have the coaching-vs-exit arc ready.

---

## Other questions worth preparing (6)

1. **Telemetry shift:** "How would you move a telemetry organization from machine-centric metrics to customer-perceived sensitivity? Where do you start?" (Straight from the JD.)
2. **AI anomaly detection rollout:** "How would you introduce AI-driven anomaly detection into an existing control plane — and how do you guard against false-positive-driven rollbacks?"
3. **Proving RPO/RTO:** "How do you prove, not assert, that a datastore meets its stated RPO/RTO? Design the validation loop."
4. **90-day plan:** "You inherit the GCE control plane org and the AI fleet is doubling every 6 months. What's your 90-day plan?" (Org scaling + strategy.)
5. **Continuous compliance:** "How would you automate continuous compliance for regulated workloads running on GCE?" (Your Moderna regulated-environment story is the answer.)
6. **Googleyness / pushback:** "Tell me about a time you pushed back on leadership or a partner team and what the outcome was."
