# ZipRecruiter Screen Debrief — Product Leader (Scott), 2026-10-06

Source: full transcript provided by Shridhar. Screen was metrics/KPI-heavy — ZipRecruiter describes itself as very metrics-oriented.

## Questions asked and talking points

**1. Intro / why looking.**
He said: at Moderna leading data science, data engineering, and software engineering; ex-Amazon (SDE → TPM → SDM), Seattle to Boston 3.5 years ago for his wife's residency; MBA May 2026. Reason for looking: getting bored at Moderna, itch to return to core tech, and a ceiling argument — "deep down, I'm not a bio person; beyond a point you need deep bio knowledge, which I don't have and am not much interested in."

**2. Product metrics deep-dive (celebrity voices, Shaq).**
Strong. Uber-metric framing (downstream impact: device owners buy 15-20% more), time-spent hypothesis (15 → 30 min/day), measurement via wake-word-to-last-utterance, DAU/MAU, interactions/day, domain breadth as horizontal-vs-single-trick signal. → Banked as STAR story: `celebrity-voices-shutdown-story.md`.

**3. Results and kill decision.**
Honest: 6-7% power users, false wakes eroded trust (unplugged devices), trials didn't convert, 99 cents converted but royalty math failed, shut down after 3 years. Hindsight: beta launch + two-tier deal + revenue sharing over lump sum.

**4. Technical challenge, explained simply.**
Voice-model data bias: training data came from metro Amazon offices (~60% Asian employees) while the actual Shaq-fan user base was the reverse — so pronunciation variation ("Hey, Shaq" said four ways) wasn't in the training distribution and the device wouldn't wake. Fixed for the next launch (Melissa McCarthy) by collecting data from the target population via booths. The name-pronunciation demo (Shridhar/Shredder/Sridhar) is an excellent plain-language explainer — reuse it.

**5. Hacky vs. robust.**
~6,000-7,000 hardcoded rule variations for sensitive interactions (e.g., self-harm: don't play psychiatrist, point to resources). Shipped fast under time pressure; knowingly rejected genuine traffic. Clear-eyed tradeoff, well told.

**6. His question to them (AI agents as users).**
Good question, got a substantive answer — useful intel: ZipRecruiter's subscription model (vs. LinkedIn/Indeed per-application pricing) aligns incentives to *embrace* agentic applications; competitors face customer pushback on agentic volume. They're actively working on it, including partnerships with OpenAI/Google. Their application flow has UI/backend tightly coupled — an integration opening.

## Coaching notes

- **Reframe "bored at Moderna."** Honest, but it can read as restless. Better: "I've hit the ceiling of what computer-science knowledge can do in biotech — the next hard problems for me are in core tech." Same truth, forward-looking.
- **The ceiling argument is strong** — "beyond a point you need deep bio knowledge" is a crisp, credible reason for the pivot. Keep it; it's the best line in the intro.
- **Metrics fluency is the differentiator here.** Downstream impact, wake-word-to-last-utterance measurement, domain breadth — this is exactly what a KPI-oriented company wants to hear. Lead with it.
- **Failure honesty landed well.** "Did people pay? No." Plain, no defensiveness. Don't sand this down in retelling.
- Watch the royalty-deal hindsight — it's good, but make sure it doesn't sound like blaming the business side; frame as "what I'd structure differently next time."
