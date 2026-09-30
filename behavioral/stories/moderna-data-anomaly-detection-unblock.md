# Unblocking the Data Anomaly Project: From 99% Dreams to 85% Reality

Scrubbed from interview transcript, 2026-09-29. Sid's own words, cleaned up.

## The project, in plain language

Lab instruments produce data that flows through a pipeline of 3-4 systems. Each system transforms the data, and at the end a report gets generated. Sometimes the report contains corrupted data the business didn't expect. The project's goal: identify where in the pipeline the data got corrupted, and catch it as early as possible. This matters because the jobs run nightly: every stage you catch the corruption late costs roughly one day of rerun. Catch it at stage 3 instead of stage 4 and report correction drops from 4 days to 3. Catch it at the source system and it's near real time.

## The story

**Situation.** "The team wanted to catch every anomaly, and we set what looked like a realistic goal: 99% of data has no anomaly, and we catch everything except 1%. The business was happy, we were happy. A month in, we hit a wall: nobody had well-defined rules for what counts as good or bad data at each intermediate stage. We knew what good looked like at the end of the pipeline, but breaking that judgment into per-stage rules was something nobody had ever written down. The team could identify good vs bad at stage 4, but that didn't solve the purpose, which was catching corruption early."

**Task.** "Unblock the team, define the missing rules with the business, and reset expectations honestly: on the goal, the timeline, and what 'done' looks like."

**Action.** "Three things. First, I set up what we call workshops: you sit with the business for a dedicated 4-hour stretch, you bring a quick proof of concept running in your local environment, they give you feedback in the moment on what is right and wrong, and you take it back. It's co-working with the business person, not a status meeting. My job was aligning the right business folks with the right developers and carving out that uninterrupted time, which is a big ask: in this case 6 business people from 4 different groups for a 20-person engineering team.

Second, I reset the target. We could not get to 99%. The realistic number, based on what the rules could actually express, was around 85%. I set that expectation with the business directly.

Third, this was going to delay the project, so I reset the timeline with business leadership, explained what was going on and why, and shielded the developers from the churn that always follows a slipped date."

**Result.** "We now have the first set of anomaly rules running. Random sampling shows 82-85% of anomalies caught, against a 65% baseline before this work. More importantly, we catch them at stage 3 now instead of stage 4, so a correction cycle is 3 days instead of 4. The next goals are stage 2, then stage 1, then the source system. This is the second place we've applied the workshop method: the first was a smaller stability data product with 5 engineers and 1 business person. Next year we have 3 similar projects with about 60 people lined up for it. My paper-napkin math: if we do this well, it saves roughly 300 developer hours across Moderna in 2027. The current default is still weekly UAT for mature requirements, which should stay. The workshops are for immature requirements, where the choice is spending endless cycles on requirement docs or working with half-baked requirements in a fast loop."

**What I'd do differently.** "Probe for the missing rules on day one instead of discovering them a month in. The question 'who can tell us what good looks like at stage 2' should have been asked before we committed to 99%."

## Where this story plays

- "Tell me about a time your team was blocked" - the core story
- "Tell me about a time you dealt with ambiguity" - no rules existed; we had to co-create them
- "Tell me about a time you had to reset expectations / deliver bad news" - 99% to 85%, plus the timeline reset
- "How do you work with non-technical stakeholders" - the workshops, carving out business time
- "Tell me about managing up" - the timeline conversation with business leadership

## Follow-ups, with the honest answers

- *"Why couldn't the team define the rules themselves?"* - "They're technical rules about business meaning. Only the business people who live with the data know what 'good' looks like at each stage, and they'd never been asked to write it down per stage before."
- *"How did the business react to 99% becoming 85%?"* - "They accepted it because we showed our work: here's what the rules can express, here's the sampling data, here's the baseline we came from."
- *"Has the workshop method spread?"* - "Second application now, three more projects planned for 2027. The first one was deliberately small so we could learn cheaply."
- *"Where does the 300 hours number come from?"* - "Paper-napkin math, I'd say so openly. It's the avoided rework across 3 projects if early-catch works the way it has so far."
