# Interview Talking Points: Moderna (and Alexa) Stories

Scrubbed from the interview transcript of 2026-09-29. Quick-reference sheet for behavioral interviews: the 30-second versions, the numbers, and which story answers which question. Full STAR versions live in the companion story files.

## Tell me about yourself (30 seconds)

"Started as a software development engineer, grew into executive leadership over 12 years at Amazon, then joined Moderna about 3.5 years ago to lead data science, data engineering, and software engineering. My team's charter is reducing the cost and time of drug manufacturing. I came in as a pure technologist with no bio background, which was the point: apply technology without bio bias. While here I also did my EMBA to deepen my business and product understanding."

## Why are you looking to move (60 seconds)

"Two things. One, I'm at a stage where I want to keep learning and growing, and the EMBA clarified that for me. Two, the US funding environment changed and Moderna decided to slow digital growth: keep the status quo for the next couple of years, concentrate on drug development, less on digital. We achieved the goals we set, and I like it here, but if I want to grow further I have to leave. I'm looking for a place closer to technology, growing, where I can apply what I've learned."

## The org in 60 seconds

- Moderna has 3 big verticals: R&D, CMC (chemistry, manufacturing, control), Commercial.
- R&D: find a drug candidate. CMC: trials, quality, regulatory compliance (FDA), manufacturing at scale and optimal cost. Commercial: ship, distribute, advertise.
- I'm in CMC digital. Digital core provides infrastructure (AWS, S3). My group builds business logic on top and spins up new infra with them when needed.
- Peak: 40 engineers, 2 managers, across 3 functions: data science, data engineering, software engineering.
- Cost optimization moved part of the org to Poland; now 14 in my group, the rest on dotted line operating independently.
- Scope: 40+ software systems in manufacturing and quality control (in-house + off-the-shelf); my group owns 28, mostly in-house.
- Current focus (last 6 months): document parsing, knowledge base, Q&A over ~400,000 non-digitized documents (manufacturing parameters, deviations, SOPs), combined with instrument data. Plus company-wide data products.

## Customers and stakeholders (all internal)

- Scientists, product/business owners, manufacturing leads, lab instrument owners: how is manufacturing performing, is scheduling on time, is equipment utilized.
- Commercial: when will manufacturing complete, so they can plan marketing campaigns and fulfillment.
- Regulatory group: consumes GxP reports (good manufacturing practice etc., per FDA guidance). Human-in-the-loop: a person pulls reports from our systems, verifies them, compiles into the FDA's format (email, print, portal). We never send directly to FDA.

## Alexa background (if asked about Amazon)

- Joined Alexa when it was 100-200 people; came from expanding Amazon retail in Singapore (personalization, marketing).
- Alexa shopping: "find the best protein bar for me" -> identify 1-2 relevant products, voice-friendly titles. Tech lead: 5 engineers, plus a manager with 4 more.
- Last role: multi-agent voice experiences. Celebrity voices: exercise with Shaquille O'Neal, Hollywood quiz with Melissa McCarthy. Owned TTS, STT, and multi-agent orchestration groups.
- Data mix: at Alexa 20% data science/engineering, 80% software engineering. The big jump into data came at Moderna.

## Story cards: question -> story -> 3 bullets

**"Tell me about a process improvement you drove"** -> Meeting overload story (companion file)
- Everyone in every meeting; 40% of the week in meetings.
- Area leads (rotated quarterly) + weekly deep dives + public wiki, later PagerDuty; got leader buy-in with data (2 meetings in 2 weeks beat 4 in a month).
- 40% -> 15-20% meeting time; 4-6 hrs/week back per person; more shipped despite 20% attrition.

**"Tell me about a time your team was blocked"** -> Data anomaly story (companion file)
- Pipeline of 3-4 systems; goal 99% anomaly catch; discovered no per-stage rules existed.
- 4-hour co-working workshops with business; reset target to 85%; reset timeline and shielded devs.
- 65% -> 82-85% caught, now at stage 3 (3-day correction instead of 4); ~300 dev-hours projected savings in 2027.

**"Tell me about influencing without authority / resistance to change"** -> the buy-in arc
- Amazon was mechanism-heavy; Moderna needed relationships first.
- Senior leader unhappy without instant answers in meetings; agreed on questions-up-front, answered offline, proved it with data.
- Led to the business creating a formal intake process (didn't exist before); I now spend 30-40% of my time on intake with business, no engineers in the room.

**"Tell me about dealing with ambiguity"** -> the anomaly rules gap, or the 400k-document Q&A (rules and structure had to be invented with the business).

**"Tell me about a failure / what you'd do differently"** -> the 3-4 month dip after the meeting change (people weren't used to async knowledge); or committing to 99% before probing whether per-stage rules existed.

**"How do you stay technical as a leader"** -> vibe coding: built the Jira-story-points-to-ChatGPT MCP connector myself as a low-stakes hobby project; encourage the team to do the same (wiki pipeline by a data scientist, DS-learning project by a data engineer). All in-house.

## Questions for them (from the transcript's close)

"From the outside there's a big matching problem: thousands of candidates, many using AI agents to apply, and AI agents on the recruiting side too. When the core offering may change like that, how are you and your team thinking about it? What is changing in your org because of this shift?"

## Metrics bank (all numbers in one place)

- 12 years at Amazon; 3.5 years at Moderna
- Peak: 40 engineers, 2 managers; now 14 (rest moved to Poland, dotted line)
- 40+ manufacturing/QC systems; my group owns 28
- ~400,000 non-digitized documents in the parsing project
- Meeting time: 40% -> 15-20% (4-6 hrs/week per person saved)
- Attrition during that period: ~20%
- Anomaly catch: 65% baseline -> 82-85%; target reset 99% -> 85%
- Correction cycle: 4 days -> 3 days (stage 4 -> stage 3 catch)
- Projected: ~300 developer hours saved in 2027 across 3 projects (60 people)
- Intake work: 30-40% of my time
- GxP: human-in-the-loop before anything reaches the FDA

## Flags: verify before using in a real interview

- "Becoming furniture at Amazon" is honest but flip; consider "I had stopped growing" for formal settings.
- The 300-hour savings is paper-napkin math: label it as such if asked.
- Poland move: frame as cost optimization you executed, not something done to you.
