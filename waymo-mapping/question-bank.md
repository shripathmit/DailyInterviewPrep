# Waymo Mapping Interview Question Bank

Role: Senior Staff Technical Lead Manager, Mapping (Mountain View, hybrid).
Interviewer: Peter Pawlowski, Principal Software Engineer (Director), responsible for Mapping (Software and Operations) and parts of Fleet Response. At Waymo since 2014, came up the IC ladder. Expect him to probe technical judgment, not just management.

What Peter shared in round 1 (Nov 18): this is a people manager role, manager of managers, with teams spread across the globe and roughly eight to ten SDEs. The previous people manager left because he wanted an IC role. The team owns infra, road graph, and data. The goal is to scale mapping, moving from manual labeling toward agentic systems, and to bring in coding agents as a force multiplier. The team grew a lot in the last year and is now stabilizing. They want someone who brings energy and calm to chaos. Shridhar used the phrase "calm in the chaos" and Peter repeated it back as the kind of person they are looking for.

Questions only, no answers. Practice plan: draw from this bank in the daily packs for the next few days, then practice answers live in mock sessions.

## A. Manager of managers and people leadership

1. You inherit a team of eight to ten senior engineers, through managers, after the previous people manager left to go back to an IC role. What do you do in the first 30 days?
2. Two managers on your team disagree on priorities and the disagreement is slowing the org down. How do you resolve it without taking every decision yourself?
3. How do you grow a first time manager who is technically strong but struggling with the people side of the job?
4. A senior engineer on one of your teams delivers strong work but is difficult to work with across teams. How do you handle it?
5. How do you keep a globally distributed team aligned when half the org is asleep during your working hours?
6. The team grew fast in the last year and is now stabilizing. How do you keep momentum and energy up when the growth phase is over?
7. Tell me about a time you had to deliver hard news to your org, a reorg, a cancelled project, or a missed goal. How did you do it?

## B. Scaling mapping and the agentic transition

8. The team is moving from manual labeling to agentic systems. How do you lead that transition without breaking quality or losing the labelers trust?
9. How do you decide what to automate first in a mapping pipeline that is still largely manual?
10. Coding agents are a force multiplier, but they also produce confident mistakes faster. What guardrails do you put around them?
11. How do you measure whether the agentic transition is actually working, not just producing more output?
12. A model update improves coverage but degrades precision in a new city. How do you decide whether to ship it?
13. The org wants to scale mapping to ten new cities this year. How do you sequence the work across the infra, road graph, and data teams?

## C. Technical depth: mapping systems

14. Design the pipeline that takes raw sensor data from a fleet and turns it into a high definition map a car can trust.
15. How do you validate a map update against ground truth before it reaches the car, and who owns the sign off?
16. A road changes after construction. Walk me through how the map detects the drift, how fast the fix propagates, and how you know the fix is right.
17. Design the versioning system for a map that updates continuously while cars are driving on it.
18. How do you trade off coverage versus precision when bootstrapping mapping in a new city with no prior data?
19. The road graph and the data pipeline disagree about a lane closure. How do you find the root cause, and who owns the fix?

## D. Technical judgment and production ML

20. An ML model for lane detection passes offline evals but degrades in production. How do you debug it?
21. How do you set an eval bar for a model whose ground truth is expensive to collect?
22. You have to choose between a more accurate model that is slower and a faster model that is slightly worse. How do you decide?
23. A vendor announces a native feature that duplicates something your team built. How do you decide whether to kill your build?

## E. Role fit

24. You have no mapping background. Why should we trust you to lead Mapping?
25. What does "calm in the chaos" mean to you as a leader, and where have you shown it?

## F. Coding (light coverage, unlikely but possible)

26. Given a road network as a graph, find the shortest path between two intersections.
27. Detect whether a directed graph of map build dependencies contains a cycle.
28. Merge overlapping map tile update intervals.
