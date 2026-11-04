# Google Manager, Agent Engineer, Applied AI: Interview Question Bank

Role: Google Cloud forward-deployed. Embedded with key accounts, customer discovery, partnering with sales, deploying agents on the Customer Engagement Suite. Resume angle: conversational agents at scale (Alexa multi-agent switching, Voice Shopping across 10 countries), Moderna agentic deployments (tools, multi-agent workflows, RAG).

## A. Behavioral and leadership (5)

1. Tell me about a time you turned around an underperforming team. What was your diagnosis, and what did you change first?
2. Tell me about a time you got a team that did not report to you to adopt a technical direction you believed in, without an executive mandate.
3. Tell me about a launch where the deadline moved up and the scope grew at the same time. What did you cut, and what did you protect?
4. Tell me about a hire you got wrong. What did your hiring bar miss, and what did you change in the loop afterward?
5. Tell me about a time you fought a commitment made above you, lost the argument, and then had to drive it with your team.

## B. Customer-facing and forward-deployed (4)

6. Walk me through how you ran customer discovery for a deployment that had to work in the customer's environment, not yours. How did discovery change what you built?
7. Tell me about a time a key account asked for something your product could not do. How did you handle it with the customer, and what did you tell your own team?
8. How do you partner with a sales organization without your team becoming their engineering contractor? Where do you draw the line, and how do you hold it?
9. Tell me about a deployment that went sideways at a customer site. How did you find the root cause, who owned the fix, and what did the customer hear from you while it was broken?

## C. Agent engineering depth (5)

10. Walk me through the architecture of your multi-agent conversational switching framework. How did agents hand off state, and what broke first at scale?
11. How did you evaluate agent quality in production? What metrics did you trust, and what did your eval harness look like?
12. When a tool call returned bad data or timed out mid-conversation, what did the agent do? How did you design for tool failure?
13. For your production RAG systems: how did you chunk, retrieve, and keep answers grounded? What failed when the corpus grew?
14. What did your agents remember across sessions, and how did you keep memory from going stale or leaking sensitive data?

## D. System design (3)

15. Design an agent deployment for a bank's contact center handling 10M calls a month across voice and chat: human handoff, evals, guardrails, and a safe rollout plan.
16. Design the evaluation and red-teaming harness you would run before putting an agent in front of a customer's end users. How do you gate the release on results?
17. Design multi-tenant agent infrastructure where each customer's data and tools are strictly isolated but the platform underneath is shared.

## E. Coding (2)

18. LRU Cache (LeetCode 146): design and implement it with O(1) get and put. Then explain where you would use it in an agent serving stack.
19. Given a stream of tool-call logs (tool name, timestamp, success or failure), return the top-k failing tools in the last hour, updating as new logs arrive.

## F. Role fit (1)

20. You are a Senior Director today. This is a Manager role, and it is customer-facing field work. Why this role, why now?
