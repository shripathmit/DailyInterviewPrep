# Cutting Meeting Load from 40% to 15%: Area Leads, Deep Dives, and Earning Buy-In

Scrubbed from interview transcript, 2026-09-29. Sid's own words, cleaned up.

## The team, in plain language

At Moderna I led a group that at peak had 40 engineers and 2 managers, split across data science, data engineering, and software engineering. Our charter: reduce the cost and the time it takes to manufacture drugs. About 80% of the team had been at Moderna for 4-5 years. Pre-COVID, Moderna ran like a true startup: titles and roles were undefined, everyone did everything, and that was the right way to operate then. After COVID there was an explosion in growth, in headcount and in the number of drugs in the pipeline (around 40). The startup habits survived the scale-up, and the most visible symptom was meetings.

## The story

**Situation.** "Almost everyone was in almost every meeting. Sometimes they contributed, sometimes they were just there to know what was happening. The data science team had 7 members, and on average 4 of them would show up to the same meeting, essentially doing the same thing: understanding customer requirements and thinking through technical challenges. When a problem spanned all three stacks, which happened about once a month, you'd see 4 people from data science, 4 from data engineering, and 4 from software engineering in one heavy meeting. A lot of expensive time was being wasted."

**Task.** "Free up the team's time for actual building, without losing knowledge continuity and without breaking the business relationships that depended on having our people in the room."

**Action.** "First I tried to understand why it was happening instead of just banning meetings. What I found: there was no good mechanism to share knowledge, so the meeting had become the knowledge store. That had been the norm for years.

So I did four things. One, I identified key folks per area and made them pseudo-leads, rotated every quarter. When a meeting came in related to document parsing, the document-parsing lead for that quarter represented our team. Two, I started weekly team deep dives: the lead, or anyone, picks a topic and talks through everything they learned that week, informally, gathering the team's thoughts. That way knowledge stays distributed and the bus factor drops. Three, I made the roster public: a wiki listing every area, its current lead, and the backup if the lead is unavailable. We have since formalized it with PagerDuty, where area owners get paged if something goes wrong, which is rare, maybe once every two weeks.

Four, and this was the hard part, I got buy-in before enforcing anything. Coming from Amazon, I was used to a mechanism-heavy environment: name something and there is a process for it. At Moderna I learned that a logically perfect process dies without people buy-in. Some leaders felt sidelined when they stopped seeing a big crowd in meetings, as if the meeting had become less important. So I worked with them directly. One specific instance: a senior leader was unhappy that our lead couldn't give instant answers in a meeting. I asked them to give us their questions up front, we came back offline with answers, and I showed them the data: for a similar past project the old way took 4 meetings over a month; the new way took 2 meetings in 2 weeks. Less people, more effective.

Later we got a technology tailwind. We got Copilot licenses and started recording team meetings. One of the data scientists built a pipeline as a hobby project that takes the meeting transcripts and updates our Confluence wiki automatically, and we added a ChatGPT connector on top. Nobody likes writing wikis, so this removed the friction. Business questions dropped because people could now query the knowledge store directly."

**Result.** "Engineers were spending 40% of their week in meetings, measured empirically through one-on-ones. After the change it dropped to 15-20%. That is 4-6 hours back per person per week, redirected to building and personal learning. The number of projects and deliverables went up, and even with around 20% natural attrition we kept a healthy backlog. Our quarterly happiness survey has a specific question, 'are you getting time to develop products or spending most of your time in meetings,' and that metric went up too. There was a dip for 3-4 months while people adjusted to consuming knowledge asynchronously instead of sitting in the room. I don't touch what's working, but I'm sure it will break in some new form eventually."

**What I'd do differently.** "Get the buy-in even earlier and more explicitly. I learned the people lesson the hard way: at Amazon the mechanism carried the authority, at Moderna the relationships carry it. Also, start the async knowledge capture sooner, because the dip in those first months was real."

## Where this story plays

- "Tell me about a process improvement you drove" - the core story
- "Tell me about a time you influenced without authority" - the senior-leader buy-in arc
- "Tell me about a time you faced resistance to change" - leaders feeling sidelined by smaller meetings
- "How do you scale a team / manage a growing org" - startup habits meeting post-COVID scale
- "Tell me about a failure or something you'd do differently" - the 3-4 month dip, the buy-in learning

## Follow-ups, with the honest answers

- *"How did you measure the 40% to 15-20%?"* - "Empirical data collected in one-on-ones, plus the quarterly happiness survey question on time-to-build vs time-in-meetings."
- *"Didn't knowledge get siloed with single leads?"* - "That's what the weekly deep dives, the quarterly rotation, the public wiki with backups, and later the transcript-to-wiki pipeline were for. The bus factor went down, not up."
- *"What happened with the senior leader who wanted instant answers?"* - "We agreed they'd send questions ahead, we'd answer offline, and the data won them over: 2 meetings in 2 weeks beat 4 meetings in a month."
- *"Can you attribute the productivity gain to this change?"* - "Honestly, no, not directly. Productivity is hard to measure. What I can point to: more projects, more deliverables, a healthy backlog despite attrition, and happier engineers."
