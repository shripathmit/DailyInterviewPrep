# Debugging a Metric Drop: The Interaction Trap

Practice date: 2026-10-04. Problem-solving type: analytical reasoning. Worked interactively, interviewer-style. Score: 8/10.

## The scenario

An agentic customer-support copilot's task-completion rate dropped 12 points overnight (78% → 66%) after a Thursday evening deploy that touched three things: the retrieval stack, the guardrail policy bundle, and the model's system prompt. Traffic roughly flat; infra green (no error spike, no latency regression).

## The worked solution

**1. Is the drop real? (before anything else)**
Confirm it's a rate drop, not a volume trick: traffic flat, so the 12 points are real. Check infra health first — green means it's a quality regression, not an outage. Never hunt causes before establishing this.

**2. Segment before hypothesizing.**
- **Regions:** all dropped equally → not a regional deploy. (Deployments are often regional; this cut is always worth the 30 seconds.)
- **Intent types:** simple Q&A intents flat; the entire drop concentrates in multi-turn troubleshooting intents — long, retrieval-heavy conversations.
- **Funnel, traced backwards from the user:** tool-call success flat, but agents abandoning/refusing mid-conversation far more often. The break is in the handoff between turns — context not transferring, or something interrupting.

**3. Ask whether the metric is lying.**
A real possibility: the new model is more efficient (fewer turns per task) and the metric misreads efficiency as failure. Check turns-per-completed-task and human-eval a sample before concluding it's a true regression.

**4. Form the interaction hypothesis.**
Two suspects touch the failing surface: retrieval output changed, guardrail refusals are up on the same intents. Hypothesis: the new retrieval stack returns noisier/longer context, the stricter guardrail bundle fires on that noise, conversations die mid-flight. Note the shape: it's the *combination*, not either change alone.

**5. Mechanism before experiment.**
Before any revert, sample ~50 refused conversations and read them. You'd see the guardrail firing on retrieval content directly — cheaper and faster than any traffic experiment, and it names the interaction instead of just a correlation.

**6. The 2x2.**
When two changes ship together, the experiment is old/new retrieval × old/new guardrails. Four arms; the (new retrieval + old guardrails) arm tells you whether the retrieval change is safe on its own. **Single-variable reverts cannot identify interactions** — reverting either change alone makes the drop disappear, which is how on-call "proves" the wrong culprit.

**7. The fix is recalibration, not revert.**
Options: fix retrieval quality, or retune the guardrail threshold to the new output distribution (a flag flip, not a deploy). Add guardrail-fire-rate as a canary metric so the next joint deploy gets caught at 10%. Also check customer impact first to choose between a patch and a revert — a temporary threshold relaxation can hold the line while the real fix ships.

## The trap (what the interviewer was testing)

On-call reverts the retrieval stack on 10% of traffic; the drop disappears; they declare victory. The correct response: **stop the ship.** The revert "working" proves nothing about which change is at fault when two interact. Use the 10% arm as data — check how the new guardrails behave against the old retrieval output — but don't ship on a single-variable result.

## Delivery & executive presence coaching

This session was voice, so these notes are about how the thinking *landed*, not just what it contained. The reasoning was strong; the packaging is where senior-leadership presence is won or lost.

**What already works:**
- You open with questions, not answers. "Is traffic flat? Any error spike?" — that's executive calm: establish facts before reacting.
- Customer-impact framing came unprompted ("how much is it impacting customers, patch vs. revert"). That's the single most senior sentence pattern in incident reasoning — keep it.
- Clear "no" under pressure. "I would not let them ship" was crisp, no hedging. That's the model for every decision moment.

**What to sharpen:**

1. **Lead with the headline number.** In the first 60 seconds, quantify blast radius: "12-point drop, concentrated in multi-turn troubleshooting — that's roughly X% of traffic, ~N thousand users per hour." You never quantified unprompted. Numbers are what make a room trust the analysis; without them, even correct reasoning sounds like opinion. Build the reflex: every segment cut gets a number attached.

2. **Headline → evidence → decision.** Your answers buried the decision inside the reasoning — three possible moves listed, conclusion somewhere in the middle. Flip it: state the call first ("Stop the ship"), then the two facts that support it. Executives decide; analysts enumerate.

3. **Replace hedging with calibrated confidence.** "That would be my hypothesis" and "that would be the starting point" soften every claim. Try: "My hypothesis is X. The one check that confirms or kills it is Y." Rank your moves instead of listing them — "first this, because...".

4. **Name the framework.** Saying "2x2 factorial" lands instantly; describing four experiment arms without the name makes the interviewer assemble it themselves. Named frameworks (funnel analysis, 2x2, canary metrics) are shorthand for seniority — use them.

5. **Speak in short paragraphs.** Voice answers meandered with mid-sentence self-corrections ("actually, let me go back..."). Technique: pause, then deliver one short paragraph per idea — "Three cuts: regions, intents, funnel. Regions are clean, so it's not the deploy geography..." Short sentences survive voice; long ones don't.

**The one-line summary:** your judgment is already senior — the work now is packaging it so a room *feels* senior in the first 90 seconds. Numbers first, decision first, frameworks named.
