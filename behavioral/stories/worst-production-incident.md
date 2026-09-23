# B3 — The Worst Production Incident I Owned: Celebrity Voice India Launch

## Context

At Amazon, I spent a year as the lead technical program manager — and also the software development manager — for the launch of Celebrity Voice in India. My job was making sure the launch went smoothly and on time. The timing was critical: we'd spent heavily securing PR for a fixed window, and missing it meant that money was wasted.

My role meant getting go/no-go from everyone — about 11 teams, 140 people. That part matters, because the miss, ultimately, was mine.

## What happened

Everything went to plan until we started dialing up and warming the servers. As traffic ramped, downstream services started throttling us. We'd load-tested, but not at the scale production was about to absorb.

The assumption going in was that capacity was already scaled to production traffic. It wasn't. We were on elastic capacity, and since production traffic had been light the previous days, it had quietly scaled down. The teams never flagged the change — and I never checked. The go/no-go was my call, and that check wasn't on my list.

## The decision

We had two options: delay the launch by several hours and lose the PR money, or push through the throttling and risk the servers. These weren't just our servers — they served all of Alexa. Bringing them down meant millions of dollars an hour, and worse, it would have destroyed the trust of the team behind the launch.

The business side was pressing to launch on time. The technical side was pressing not to take the servers down. I chose to delay — by about six hours.

## How I sold the delay

It was pure math, and I was blunt about it. I told business leadership there were only two possible outcomes. Either the launch was a massive success — in which case traffic would spike further and create a downward spiral that took the service down — or nobody cared, in which case a six-hour delay inside a 24-hour window didn't matter. I put the chance of the bad outcome above 70%. Either way, delaying was the right call. Losing six hours of the window beat risking all of Alexa.

## What I did in those six hours

Four things ran in parallel. First, we shipped a quick fix that captured incoming users at the top of the funnel, so we didn't lose them — we could reach them later and pull them back in. Second, I worked with the technical team to double capacity by securing more hosts, so we'd survive even above expected traffic. Third, I worked with the business team on a plan for the customers who did come in during the window. Fourth, on comms: I was the single point responding to every query. My team stayed heads-down; leadership got regular updates from me.

## Result

The launch went through after the delay. We lost six hours of a 24-hour window, but we kept the funnel and we kept the service up.

## What I learned

I wrote the correction-of-error doc — what happened, what went wrong, what we'd do differently. The biggest lesson: it doesn't matter if every to-do item is checked off. The basics decide it — test downstream services at the peak traffic you actually expect, not the traffic you assume. The next four launches, including Melissa McCarthy and Samuel L. Jackson in the US, went smoothly with no service issues.

And the personal one: when you own the go/no-go, the check that's missing from your list is your miss. Not the team's. Mine.

---
*Practiced 2026-09-22. Score: 8/10. Drill: lead with ownership in the first telling.*
