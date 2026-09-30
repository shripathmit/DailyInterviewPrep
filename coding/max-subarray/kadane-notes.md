# Maximum Subarray — Kadane's Algorithm (Practice 2026-09-29)

## Problem
Given an integer array, find the contiguous subarray with the largest sum.

## The core rule (the one insight that replaces the quadratic idea)
At each element, the best subarray ending here is either **the element alone**
or **the element plus the sum being carried**. Take the max of those two, and
track the best seen overall. One pass, no re-scanning.

## My solution approach
- Started by considering enumerating sliding-window sizes — correct but O(n²).
- Moved to the single-pass insight above and coded it directly.
- Added index tracking (start/end/tempStart) so the actual subarray can be
  rebuilt, not just the sum.

## Concepts used
1. **Kadane's algorithm** — single-pass dynamic programming. The subproblem
   "best subarray ending at index i" has an optimal-substructure recurrence:
   `currentMax(i) = max(nums[i], nums[i] + currentMax(i-1))`.
2. **Greedy/D.P. state split** — two variables: `currentMax` (best subarray
   ending at i) vs `maxSoFar` (best subarray seen anywhere so far). Keeping
   them separate is what makes the single pass possible.
3. **Candidate tracking for reconstruction** — `tempStart` marks where the
   current run began; only committed to `start/end` when a new global max is
   found. Same pattern as tracking argmax alongside max.
4. **Edge-case-safe initialization** — initializing from `inputA[0]` instead
   of 0 makes all-negative arrays work (answer = least-negative element).
   Strict `>` comparisons keep the *first* maximal subarray on ties.
5. **Materializing the result** — boundaries + `ArrayList` rebuild the output
   subarray in O(k) instead of copying arrays during the scan.

## Trace (input: -2, 1, -3, 4, -1)
| i | x  | currentMax | maxSoFar | start,end |
|---|----|------------|----------|-----------|
| 0 | -2 | -2         | -2       | 0,0       |
| 1 |  1 | 1 (restart)| 1        | 1,1       |
| 2 | -3 | -2         | 1        | 1,1       |
| 3 |  4 | 4 (restart)| 4        | 3,3       |
| 4 | -1 | 3          | 4        | 3,3       |

Output: `[4]`, sum `4`.

## Complexity
- Time: O(n) — one pass.
- Space: O(1) auxiliary (+ O(k) for the returned subarray).

## Gaps to close
- No empty-array guard: `inputA[0]` throws on `length == 0`. In an interview,
  state the assumption or guard up front.
- Name the DP framing out loud ("optimal substructure: best ending at i"),
  not just the code mechanics — that's the signal interviewers score.
- Worth a follow-up drill: maximum *product* subarray (sign flips), which
  breaks the "restart on negative" intuition and forces tracking both min
  and max.

## Score: 9/10
Correct first submission, clean index tracking, all-negative safe. One point
off for the missing empty-input guard.
