## All Math: Ratio and proportions, LCM, HCF and GCD, Euclid's Algorithm, Factorial, Prime no, Logarithms, Sieve of Eratosthenes, Fibonacci Sequence, Quadratic equations (done), Set, Relation and function, Matrix, Graph (basic) and vector, Arithmetic progression and geometric progression, permutation and combination, linear equation, boolean algebra, statistics and probability (basic), calculus 1, bitwise operators and number theory, geometry and trigonometry

## SDE Essential: Boolean Algebra & Logic, Linear Equation, Statistics and Probability (basic), Geometry and Trigonometry

N.B: All essential Math topics needed (especially for Software development) are within `discrete math` & so it's enough.

## Good to know: Number Theory and prime numbers (including Sieve of Eratosthenes), Fibonacci Sequence, Euclid's Algorithm, LCF and HCF and Logarithms

## specific domain: Matrix, Graph (basic) and Vector, Calculus-1, AP, GP, Set, Relation and Function

## Animation: Geometry, Trigonometry, Linear Algebra and Interpolation and Easing Function

## ALL Math: Discrete Math(Fundamental 2), Number theory (DS), Algebra(Fundamental 1), Liner algebra (ML), Mathematical Induction (Recursion), Basic Probability and Statistic (ML), Graph Theory(DS), Boolean Algebra, Calculus(ML and Simulation), Solving Equations(DS), Logarithms(DS), Functions solving (DS), Prime numbers, GP and AP, Geometry and Trigonometry, Exponential(DS), Modulus(DS), Factorial(DS), Recurrence Relation and Series (DS: Recursion), Matrices and Matrix operations (DS)

[[##]] Prerequisites of Integration: Algebra + discrete math + basic calculus -> Integrations

[[##]] Prerequisites of Recurrence Relation: Basic Principles of Sequence and series -> Summation and series formulas -> Inductive Reasoning and Mathematical Induction -> Number Theory -> Generating Functions -> Newton's method (for value of p)

# Q: What type of recursion and what kind of recurrence relation it has? [ An approach is selected based on these two ]

Reminder: There's no single math topic that can handle all types of recursion, depending on "recursion type" and "recurrence relations"; the different approach is required

## Recursive functions/Recurrence relations: Master Theorem (divide and conquer recursions) or Akra-Bazzi (same and versatile)

https://www.youtube.com/watch?v=DTERgFa9jJc&ab_channel=Mathispower4u
https://www.youtube.com/watch?v=l5BG_c62CCo&ab_channel=itechnica
https://www.youtube.com/watch?v=honkPbYEc7Q&ab_channel=randerson112358
https://www.youtube.com/watch?v=7mhvA5L7KqY&ab_channel=TrevTutor
https://www.youtube.com/watch?v=OgNH3xrhtdg&ab_channel=MathFortress
https://www.youtube.com/watch?v=5zi5eG5Ui-Y&ab_channel=AcademicLesson
https://www.youtube.com/watch?v=PUB0TaZ7bhA&ab_channel=TheOrganicChemistryTutor

[[##]] Algorithms and complexity: it isn't a math topic but a concept i.e. Big O Notation

## MATH: Discrete Math + Linear Algebra is enough for DSA (VVIP) and optional basic probalities and statistics and basic graph and basic calculus

## upper bound: maximum time or space to be take by the algo (it won't be more than that never) or in a sorted array if given a target the first greater element than target [1, 2, 3, 4, 5] target = 2; upper bound here is 3

## lower bound: minimum time or space by algorithm and in a sorted array first less element [1, 2, 3, 4, 5] t = 3; lower = 2

## worst time complexities based on the given input's length

## leetcoding: take note of specific algo being used for specific problems and patterns

#### https://levelup.gitconnected.com/dont-just-leetcode-follow-the-coding-patterns-instead-4beb6a197fdb

#### https://www.techinterviewhandbook.org/algorithms/array/

+, - , \*, /, %, bitwise, Math.log(), Math.pow(), Math.sqrt(), Math.min(), Math.max(), Math.min()

-- Prime numbers, Hermonic progression of prime numbers,

---

Array(ArrayList), String, Stack/Queue, LinkedList, Binary Search Tree, Heap, Graph and Trie

#### solving: what are the steps and its cases? how: programmatically possible (VILA) + Mathematically possible + assumption

#### concept memorization + mix and match for any new problem by mapping it any previous concept(s) or question(s)

#### How to actually solve (or try to solve) leetcode new (and unique) problems?

Discrete mathematics - Susana Epp or Kohlmann
Number theory- Joseph silver man
Graph theory- Richard Trudeu
Combinatorics-Alan tucker
Algorithms -Jeff Erickson or Dasgupta or grokking

## Every question should have a conception overivew (if don't know ask chatgpt) then draw / visualize from conceptual overview

1. Concept memorization (not code) e.g how Binary search truly works and when should I used it (and its template)

2. and this is what will help solve/brainstorm new problems along with below a, b

a. Best leetcode questions to understand specific algorithmic technique e.g. backtracking / BFS / DFS, Matrix Traversal, merge sort etc.
b. Most common leetcode patterns when the input is string

# Top 5 data structures to must do: 1) Graph 2) Stack / Queue 3) HashMap 4) Binary Tree 5) Heap

# Top 5 algorithm to must do: 1) BFS and DFS (BT & Graph) 2) DP 3) Backtracking 4) Sliding Window 5) Top K elements

## when to use (main data structure) Heap with Binary Tree (helper data structure)? when to use Binary Tree with hashmap?

## Solving a new DSA problem during interview

https://www.youtube.com/watch?v=DIR_rxusO8Q&ab_channel=FullstackAcademy

https://masaischool.com/blog/7-step-approach-to-solve-any-coding-problem-important-for-interviews

Sure! Let's take a medium-level LeetCode problem and go through each step mentioned in the approach. We'll use "3Sum" (LeetCode Problem #15) as our example.

### Problem Statement

Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

### Steps to Solve the Problem

#### 1. Read the Problem Statement Carefully

- Ensure you understand the requirements: Find triplets in the array that sum to zero.
- Identify inputs: an integer array `nums`.
- Identify outputs: a list of lists containing the triplets.

#### 2. Visualize the Problem

- Drawing the problem helps clarify the requirement. For instance, for `nums = [-1, 0, 1, 2, -1, -4]`, you can visualize pairs and their sums to see which combinations might work.

```
nums: [-1, 0, 1, 2, -1, -4]
possible triplets: [-1, 0, 1], [-1, -1, 2], ...
```

#### 3. Write Pseudocode

- Plan your steps in plain language before coding.

```
sort(nums)
initialize result list
for each index i in nums:
    if i > 0 and nums[i] == nums[i-1], continue to next i
    set left to i + 1, right to last index
    while left < right:
        calculate current_sum = nums[i] + nums[left] + nums[right]
        if current_sum == 0, add triplet to result, move left and right pointers
        if current_sum < 0, move left pointer right
        if current_sum > 0, move right pointer left
return result list
```

#### 4. Break Down the Problem

- Split the task into smaller steps.
  1. **Sort the Array**: Makes it easier to avoid duplicates and manage the pointers.
  2. **Use Three Pointers**: One for each element in the triplet.
  3. **Avoid Duplicates**: Skip duplicate values to ensure unique triplets.
  4. **Move Pointers**: Adjust pointers based on the sum.

#### 5. Identify Edge Cases

- An empty array or an array with less than three elements.
- Arrays where no triplets sum to zero.

#### 6. Think of Data Structures and Algorithms

- Sorting the array first simplifies the two-pointer approach.
- Using a list to store results ensures we return the correct format.

### Code Implementation

```python
def threeSum(nums):
    nums.sort()
    result = []
    for i in range(len(nums)):
        if i > 0 and nums[i] == nums[i - 1]:
            continue
        left, right = i + 1, len(nums) - 1
        while left < right:
            current_sum = nums[i] + nums[left] + nums[right]
            if current_sum == 0:
                result.append([nums[i], nums[left], nums[right]])
                left += 1
                right -= 1
                while left < right and nums[left] == nums[left - 1]:
                    left += 1
                while left < right and nums[right] == nums[right + 1]:
                    right -= 1
            elif current_sum < 0:
                left += 1
            else:
                right -= 1
    return result
```

### Summary

- **Read Carefully**: Understand the problem requirements and constraints.
- **Visualize**: Use diagrams to see how elements can form triplets.
- **Pseudocode**: Plan your solution in plain language.
- **Break Down**: Tackle smaller parts of the problem sequentially.
- **Edge Cases**: Consider special scenarios and ensure they are handled.
- **Data Structures**: Use sorting and two-pointer techniques for efficient solutions.

By following these steps, you can systematically approach and solve new or unknown LeetCode problems.

Let's take a hard-level LeetCode problem and go through each step mentioned in the approach. We'll use "Merge k Sorted Lists" (LeetCode Problem #23) as our example.

### Problem Statement

You are given an array of `k` linked-lists `lists`, each linked-list is sorted in ascending order. Merge all the linked-lists into one sorted linked-list and return it.

### Steps to Solve the Problem

#### 1. Read the Problem Statement Carefully

- Ensure you understand the requirements: Merge `k` sorted linked-lists into one sorted linked-list.
- Identify inputs: an array of `k` linked-lists.
- Identify outputs: a single merged and sorted linked-list.

Let's take a hard-level LeetCode problem and go through each step mentioned in the approach. We'll use "Merge k Sorted Lists" (LeetCode Problem #23) as our example.

### Problem Statement

You are given an array of `k` linked-lists `lists`, each linked-list is sorted in ascending order. Merge all the linked-lists into one sorted linked-list and return it.

### Steps to Solve the Problem

#### 1. Read the Problem Statement Carefully

- Ensure you understand the requirements: Merge `k` sorted linked-lists into one sorted linked-list.
- Identify inputs: an array of `k` linked-lists.
- Identify outputs: a single merged and sorted linked-list.

#### 2. Visualize the Problem

- Drawing the problem helps clarify the requirement. For example, for `lists = [[1,4,5], [1,3,4], [2,6]]`, you can visualize the merging process.

```
lists: [[1,4,5], [1,3,4], [2,6]]
merged: [1,1,2,3,4,4,5,6]
```

#### 3. Write Pseudocode

- Plan your steps in plain language before coding.

```
initialize a min-heap
for each linked list, add the head node to the heap
initialize a dummy node to build the result list
while the heap is not empty:
    extract the minimum node from the heap
    add this node to the result list
    if the extracted node has a next node, add the next node to the heap
return the result list starting from the dummy node's next
```

#### 4. Break Down the Problem

- Split the task into smaller steps.
  1. **Initialize a Min-Heap**: To always extract the minimum element efficiently.
  2. **Add Head Nodes**: Add the head nodes of all linked-lists to the heap.
  3. **Extract Min and Add to Result**: Continuously extract the smallest node and add it to the result list.
  4. **Add Next Nodes**: If the extracted node has a next node, add it to the heap.

#### 5. Identify Edge Cases

- An empty array of lists.
- Lists where some or all linked-lists are empty.

#### 6. Think of Data Structures and Algorithms

- Use a min-heap (priority queue) to efficiently get the minimum element.
- Use a linked-list to store the result.

### Code Implementation

```python
from heapq import heappush, heappop
from typing import List, Optional

class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

    def __lt__(self, other):
        return self.val < other.val

def mergeKLists(lists: List[Optional[ListNode]]) -> Optional[ListNode]:
    min_heap = []
    for l in lists:
        if l:
            heappush(min_heap, l)

    dummy = ListNode()
    current = dummy

    while min_heap:
        smallest_node = heappop(min_heap)
        current.next = smallest_node
        current = current.next
        if smallest_node.next:
            heappush(min_heap, smallest_node.next)

    return dummy.next
```

### Summary

- **Read Carefully**: Understand the problem requirements and constraints.
- **Visualize**: Use diagrams to see how linked-lists can be merged.
- **Pseudocode**: Plan your solution in plain language.
- **Break Down**: Tackle smaller parts of the problem sequentially.
- **Edge Cases**: Consider special scenarios and ensure they are handled.
- **Data Structures**: Use a min-heap for efficient merging.

By following these steps, you can systematically approach and solve new or unknown LeetCode problems, even at the hard difficulty level.

#### 3. Write Pseudocode

- Plan your steps in plain language before coding.

```
initialize a min-heap
for each linked list, add the head node to the heap
initialize a dummy node to build the result list
while the heap is not empty:
    extract the minimum node from the heap
    add this node to the result list
    if the extracted node has a next node, add the next node to the heap
return the result list starting from the dummy node's next
```

#### 4. Break Down the Problem

- Split the task into smaller steps.
  1. **Initialize a Min-Heap**: To always extract the minimum element efficiently.
  2. **Add Head Nodes**: Add the head nodes of all linked-lists to the heap.
  3. **Extract Min and Add to Result**: Continuously extract the smallest node and add it to the result list.
  4. **Add Next Nodes**: If the extracted node has a next node, add it to the heap.

#### 5. Identify Edge Cases

- An empty array of lists.
- Lists where some or all linked-lists are empty.

#### 6. Think of Data Structures and Algorithms

- Use a min-heap (priority queue) to efficiently get the minimum element.
- Use a linked-list to store the result.

### Code Implementation

```python
from heapq import heappush, heappop
from typing import List, Optional

class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

    def __lt__(self, other):
        return self.val < other.val

def mergeKLists(lists: List[Optional[ListNode]]) -> Optional[ListNode]:
    min_heap = []
    for l in lists:
        if l:
            heappush(min_heap, l)

    dummy = ListNode()
    current = dummy

    while min_heap:
        smallest_node = heappop(min_heap)
        current.next = smallest_node
        current = current.next
        if smallest_node.next:
            heappush(min_heap, smallest_node.next)

    return dummy.next
```

### Summary

- **Read Carefully**: Understand the problem requirements and constraints.
- **Visualize**: Use diagrams to see how linked-lists can be merged.
- **Pseudocode**: Plan your solution in plain language.
- **Break Down**: Tackle smaller parts of the problem sequentially.
- **Edge Cases**: Consider special scenarios and ensure they are handled.
- **Data Structures**: Use a min-heap for efficient merging.

By following these steps, you can systematically approach and solve new or unknown LeetCode problems, even at the hard difficulty level.

Your approach outlines a solid problem-solving method, but let's add a bit more structure to help you systematically deduce the steps needed. Here's a more detailed breakdown of a systematic approach you can follow:

### Systematic Approach to Problem Solving

1. **Understand the Problem:**

- **Carefully read the problem statement.**
- **Identify input and output:** Note the data types and structures.
- **Constraints and edge cases:** Pay attention to any special conditions or limits.

2. **Explore Examples:**

- **Work through simple examples:** Understand how the problem behaves with basic input.
- **Identify patterns:** Look for repeated operations or structures in the examples.

3. **Choose an Initial Approach:**

- **Start with brute force:** Ensure you can solve the problem in a straightforward way.
- **Check correctness:** Make sure the brute force solution produces the correct output for all test cases.

4. **Optimize the Approach:**

- **Identify inefficiencies:** Look at your brute force solution to find areas that can be improved.
- **Choose the right technique:** Decide on an algorithm or data structure that fits the problem (e.g., sliding window, two pointers, dynamic programming, etc.).
- **Break down the solution:** Create a step-by-step plan for the optimized approach.

5. **Write Pseudocode:**

- **Outline the logic:** Convert your step-by-step plan into pseudocode to clarify your thoughts.

6. **Implement the Solution:**

- **Translate pseudocode into code:** Write the actual code for your solution.
- **Use meaningful variable names:** Make your code readable and maintainable.

7. **Test the Solution:**

- **Run test cases:** Use a variety of inputs, including edge cases.
- **Debug as needed:** Fix any issues that arise during testing.

8. **Review and Refine:**

- **Analyze performance:** Consider the time and space complexity.
- **Refactor if needed:** Make the code cleaner and more efficient if possible.

### Example: Applying the Systematic Approach ↓↓

**Problem: Find the longest substring without repeating characters.**

1. **Understand the Problem:**

- Input: A string `s`.
- Output: An integer representing the length of the longest substring without repeating characters.
- Constraints: The length of the string can be up to 10^5.

2. **Explore Examples:**

- Example: For `s = "abcabcbb"`, the longest substring without repeating characters is `"abc"` with length 3.
- Example: For `s = "bbbbb"`, the longest substring without repeating characters is `"b"` with length 1.

3. **Choose an Initial Approach:**

- Brute force: Check all substrings and determine if they have repeating characters. This is not efficient but helps understand the problem.

4. **Optimize the Approach:**

- **Sliding Window + HashMap:**
  - Initialize `start`, `end`, and `maxLength`.
  - Use a hash map to track characters and their positions.
  - Expand the window by moving the `end` pointer.
  - If a character is repeated, move the `start` pointer.
  - Update `maxLength` as needed.

5. **Write Pseudocode:**

   ```text
   Initialize start = 0, end = 0, maxLength = 0
   Initialize charMap as an empty dictionary

   For end from 0 to length of s:
       If s[end] is in charMap:
           Move start to max(start, charMap[s[end]] + 1)
       Update charMap with s[end] = end
       Update maxLength to max(maxLength, end - start + 1)

   Return maxLength
   ```

6. **Implement the Solution:**

   ```python
   def lengthOfLongestSubstring(s: str) -> int:
       charMap = {}
       start = 0
       maxLength = 0

       for end in range(len(s)):
           if s[end] in charMap:
               start = max(start, charMap[s[end]] + 1)
           charMap[s[end]] = end
           maxLength = max(maxLength, end - start + 1)

       return maxLength
   ```

7. **Test the Solution:**

- Test with various inputs: `"abcabcbb"`, `"bbbbb"`, `"pwwkew"`, `""`, `"a"`, `" "`, `"au"`.
- Ensure all edge cases are covered and the output is correct.

8. **Review and Refine:**

- Analyze the complexity: The time complexity is O(n) because each character is processed at most twice.
- Ensure the code is clean and maintainable.

By following this structured approach, you'll have a clearer path to solving problems and can systematically deduce the steps needed for a solution.

### Tips for Determining the Approach for any DSA problem

It's completely normal to struggle with identifying the optimal approach (Step 4) when solving algorithmic problems, especially if you're still gaining experience in problem-solving techniques. Here are some tips that can help you improve your ability to determine the right approach:

### Tips for Determining the Approach

1. **Practice and Familiarity:**

- The more problems you solve, the more familiar you'll become with common techniques like sliding window, two pointers, hash maps, etc.
- Reviewing solutions and explanations for different types of problems can help build your understanding.

2. **Identify Patterns:**

- Look for keywords or phrases in the problem statement that might suggest a particular approach (e.g., "subarray" might hint at a sliding window).
- Recognize similar problems you've solved before and apply similar techniques.

3. **Start with Brute Force:**

- If you're unsure of the optimal approach, begin with a straightforward brute force solution.
- This can help you understand the problem better and often leads to insights on how to optimize.

4. **Consider Constraints and Trade-offs:**

- Evaluate the constraints of the problem (e.g., time complexity, space complexity).
- Choose an approach that balances efficiency with clarity and ease of implementation.

5. **Break Down the Problem:**

- Break the problem into smaller parts or subproblems.
- Think about how you would solve each part individually and then integrate them into a cohesive solution.

6. **Draw Diagrams or Use Examples:**

- Visual aids such as diagrams or examples can help you understand the problem better and brainstorm potential solutions.

7. **Discuss and Collaborate:**

- Discussing problems with others or reading different approaches can provide new perspectives and insights.
- Online forums, coding communities, or study groups can be valuable resources.

### Example Application

Let's apply these tips to the problem of finding the longest substring without repeating characters:

1. **Understand the Problem:**

- Input: A string `s`.
- Output: An integer representing the length of the longest substring without repeating characters.

2. **Explore Examples:**

- Example: For `s = "abcabcbb"`, the longest substring without repeating characters is `"abc"` with length 3.
- Example: For `s = "bbbbb"`, the longest substring without repeating characters is `"b"` with length 1.

3. **Start with Brute Force:**

- Check all possible substrings and determine if they contain repeating characters. This might involve nested loops and isn't optimal but can provide insights.

4. **Optimize the Approach (Sliding Window + HashMap):**

- Identify the need to track characters and their positions using a hash map (`charMap`).
- Use a sliding window approach with two pointers (`start` and `end`) to efficiently find the longest substring without repeating characters.

5. **Write Pseudocode:**

   ```text
   Initialize start = 0, end = 0, maxLength = 0
   Initialize charMap as an empty dictionary

   For end from 0 to length of s:
       If s[end] is in charMap:
           Move start to max(start, charMap[s[end]] + 1)
       Update charMap with s[end] = end
       Update maxLength to max(maxLength, end - start + 1)

   Return maxLength
   ```

6. **Implement the Solution:**

   ```python
   def lengthOfLongestSubstring(s: str) -> int:
       charMap = {}
       start = 0
       maxLength = 0

       for end in range(len(s)):
           if s[end] in charMap:
               start = max(start, charMap[s[end]] + 1)
           charMap[s[end]] = end
           maxLength = max(maxLength, end - start + 1)

       return maxLength
   ```

7. **Test and Refine:**

- Test the solution with different inputs and edge cases.
- Ensure the code handles all scenarios correctly and efficiently.

### Practice Makes Progress

Remember, problem-solving is a skill that improves with practice and persistence. As you work through more problems and analyze different approaches, you'll become more adept at identifying patterns and determining the most suitable techniques. Don't hesitate to review explanations and seek help when needed—it's all part of the learning process!

---

#### Generalized Steps to solve any programming problem: https://www.youtube.com/watch?v=roanIWKtGMY&t=358s&ab_channel=NeetCodeIO

1. *Understanding problem (I can imagine/map it to the real world situation i.e. related/unrelated strictly + visualize) + *test or simplified inputs + time constraints (skip or not) + keywords (if given)

2. Is output explicit or implicit? Some answer is explicitly given (or part of the input), otherwise need to find the output (visually) and when doing it I will be able to find out STEP(s) and required algo/patterns `This is a must STEP`

N.B: [ during the STEP 2 if stuck i.e. can't get to the output with my approach(es) (that could be vary for each person e.g. 10/15/20/30min ]; write comments on things I am unable to solve & now straight up look for solution ↓

N.B: Even If I look at the solution; I must ensure 2 things 1) why developer opt for this (data structure and algo) in this way to solve my commented queries? 2) Understand enough to apply to another problem later (although this may or may not another problem like this)

3. Divide the problem into smaller ones or individual steps and each step could utilize (a. example inputs b. are helper/additional data structure(s)/algo(s) needed? c. possible cases and edge cases d. pseudocode)

4. Get Unstuck - Fix Bugs/Errors (even if i need to look at solution a. why does the solution work? b. what could be the intuition/thought process to come up with this solution?)

5. If you really want to think like a programmer

https://www.youtube.com/watch?v=e6wWDe7_-Ag&ab_channel=MisoTech%28MichaelSong%29 (VVIP)
https://chat.openai.com/c/9c5fa7fd-71a0-488c-ba64-b54dbfa92494 (VVIP)

## Design a real problem: making my own question and deciding what data structure and algorithm to be used there (it could be related to a project or standalone question)

requirement@hdfcergo.com
proposal no = 202403120182258

## How to build intuition ?

ask chatgpt: since "Numbers with same consecutive differences" question doesn't really explicitly say anything about to use DFS, how should I build to intuition or think to use DFS + backtracking?

ask chatgpt: Understand the Benefits of DFS and Backtracking like this list out the benefits of all and most common data structure and their relative algorithms/leetcode and when they are used especially when the question doesn't say anything explicitly

ask chatgpt: Understand the Benefits of DFS and Backtracking like this list out the benefits of all and most common algorithms/leetcode and when they are used especially when the question doesn't say anything explicitly

These are my opinions.

- My first thought on this is. If you can sort without moving those in the banned position. Though that was wrong.

  1.) The importance of simplifying or making a problem understandable.
  I bet most people spend 30 mins or more just figuring out the problem statement.
  1.2) The importance of naming things.  
  Like maybe banned is actually called banned_index or k should be called differently.
  2.) Good examples are a must. I don't know if this is hard because of bad examples or bad because of too few good examples.
  3.) Companies should understand not everyone wants to be a competitive programmer or want to be a leetcode solver.
  It's fine if you want it. But don't make it a priority for qualification.
  DS and Algo should be prioritize more.
  4.) The benefit for the problem. If you give this type of problem to your applicants. Is there a benefit if someone solves this problem?
  Or are you just giving hard problem for the sake of it? If your company always or most of the times encounter this problem go for it.
  But if not, it is just a waste of time for you and your applicants.

# Recursion : Types and everything else

To get well-versed with Fibonacci-type recursion, especially for FAANG interviews, it’s important to tackle a range of problems that cover various scenarios and nuances of Fibonacci-based recursion. Here’s a comprehensive list of LeetCode problems that will help you master this topic, including some non-obvious applications of the Fibonacci pattern:

### LeetCode Problems on Fibonacci-Based Recursion

#### Basic Fibonacci and Variants

1. **Fibonacci Number (Easy, Problem 509)**

   - **Link:** [Fibonacci Number](https://leetcode.com/problems/fibonacci-number/)
   - **Description:** Compute the nth Fibonacci number using recursion and memoization.

2. **Nth Tribonacci Number (Easy, Problem 1137)**

   - **Link:** [Nth Tribonacci Number](https://leetcode.com/problems/n-th-tribonacci-number/)
   - **Description:** Calculate the nth Tribonacci number, where each term is the sum of the three preceding ones.

3. **Climbing Stairs (Easy, Problem 70)**

   - **Link:** [Climbing Stairs](https://leetcode.com/problems/climbing-stairs/)
   - **Description:** Count distinct ways to reach the top by taking 1 or 2 steps at a time.

4. **Min Cost Climbing Stairs (Easy, Problem 746)**
   - **Link:** [Min Cost Climbing Stairs](https://leetcode.com/problems/min-cost-climbing-stairs/)
   - **Description:** Find the minimum cost to reach the top of the floor, with costs assigned to each step.

#### Dynamic Programming with Fibonacci Pattern

5. **House Robber (Medium, Problem 198)**

   - **Link:** [House Robber](https://leetcode.com/problems/house-robber/)
   - **Description:** Maximize the amount of money robbed without robbing adjacent houses, utilizing previous states.

6. **House Robber II (Medium, Problem 213)**

   - **Link:** [House Robber II](https://leetcode.com/problems/house-robber-ii/)
   - **Description:** Extend the House Robber problem to circular streets, adding complexity to the choices.

7. **House Robber III (Medium, Problem 337)**

   - **Link:** [House Robber III](https://leetcode.com/problems/house-robber-iii/)
   - **Description:** Rob houses in a binary tree structure, where each node represents a house.

8. **Decode Ways (Medium, Problem 91)**

   - **Link:** [Decode Ways](https://leetcode.com/problems/decode-ways/)
   - **Description:** Count the number of ways to decode a string encoded with A=1 to Z=26, dealing with subproblem overlaps.

9. **Decode Ways II (Hard, Problem 639)**
   - **Link:** [Decode Ways II](https://leetcode.com/problems/decode-ways-ii/)
   - **Description:** Extend Decode Ways with wildcard '\*' characters, introducing additional complexities.

#### Grid Path Problems

10. **Unique Paths (Medium, Problem 62)**

    - **Link:** [Unique Paths](https://leetcode.com/problems/unique-paths/)
    - **Description:** Count paths in an m x n grid, moving only down or right.

11. **Unique Paths II (Medium, Problem 63)**

    - **Link:** [Unique Paths II](https://leetcode.com/problems/unique-paths-ii/)
    - **Description:** Extend Unique Paths with obstacles in the grid.

12. **Minimum Path Sum (Medium, Problem 64)**
    - **Link:** [Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)
    - **Description:** Find the path with the minimum sum in an m x n grid.

#### Combinatorial Counting and Partitioning

13. **Domino and Tromino Tiling (Medium, Problem 790)**

    - **Link:** [Domino and Tromino Tiling](https://leetcode.com/problems/domino-and-tromino-tiling/)
    - **Description:** Count ways to tile a 2 x n board using dominoes and trominoes.

14. **Tiling a Rectangle with the Fewest Squares (Hard, Problem 1240)**

    - **Link:** [Tiling a Rectangle with the Fewest Squares](https://leetcode.com/problems/tiling-a-rectangle-with-the-fewest-squares/)
    - **Description:** Tile a rectangle using the fewest number of square tiles.

15. **Partition Equal Subset Sum (Medium, Problem 416)**

    - **Link:** [Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/)
    - **Description:** Determine if the array can be partitioned into two subsets with equal sum.

16. **Integer Break (Medium, Problem 343)**
    - **Link:** [Integer Break](https://leetcode.com/problems/integer-break/)
    - **Description:** Break a positive integer into the sum of at least two positive integers and maximize their product.

#### Optimal Substructure and Miscellaneous

17. **Longest Increasing Subsequence (Medium, Problem 300)**

    - **Link:** [Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)
    - **Description:** Find the longest increasing subsequence in an array.

18. **Paint House (Medium, Problem 256)**

    - **Link:** [Paint House](https://leetcode.com/problems/paint-house/)
    - **Description:** Minimize the painting cost of houses, ensuring no two adjacent houses have the same color.

19. **Paint Fence (Medium, Problem 276)**

    - **Link:** [Paint Fence](https://leetcode.com/problems/paint-fence/)
    - **Description:** Count the number of ways to paint a fence with k colors and n posts.

20. **Paint House II (Hard, Problem 265)**

    - **Link:** [Paint House II](https://leetcode.com/problems/paint-house-ii/)
    - **Description:** Extend Paint House to allow multiple colors per house with optimized costs.

21. **Delete and Earn (Medium, Problem 740)**

    - **Link:** [Delete and Earn](https://leetcode.com/problems/delete-and-earn/)
    - **Description:** Maximize points by deleting elements, where deleting an element removes its neighbors.

22. **Maximum Product Subarray (Medium, Problem 152)**

    - **Link:** [Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)
    - **Description:** Find the contiguous subarray with the maximum product.

23. **Ugly Number II (Medium, Problem 264)**

    - **Link:** [Ugly Number II](https://leetcode.com/problems/ugly-number-ii/)
    - **Description:** Find the nth ugly number, where an ugly number has prime factors of 2, 3, or 5.

24. **Burst Balloons (Hard, Problem 312)**

    - **Link:** [Burst Balloons](https://leetcode.com/problems/burst-balloons/)
    - **Description:** Find the maximum coins obtained by bursting balloons wisely.

25. **Jump Game (Medium, Problem 55)**

    - **Link:** [Jump Game](https://leetcode.com/problems/jump-game/)
    - **Description:** Determine if you can reach the last index of the array.

26. **Jump Game II (Medium, Problem 45)**
    - **Link:** [Jump Game II](https://leetcode.com/problems/jump-game-ii/)
    - **Description:** Find the minimum number of jumps needed to reach the last index.

### Strategies for Mastering Fibonacci-Based Recursion

1. **Understand the Recursive Structure:**

   - Recognize problems where the solution builds incrementally, similar to Fibonacci, where the nth solution depends on previous solutions.

2. **Apply Memoization:**

   - Use memoization to cache intermediate results, optimizing recursive solutions by avoiding redundant calculations.

3. **Identify Overlapping Subproblems:**

   - Look for subproblems that recur frequently and can be solved once and reused, a hallmark of dynamic programming.

4. **Practice Optimal Substructure:**

   - Ensure that the problem exhibits optimal substructure, where optimal solutions of subproblems can construct an optimal solution for the whole.

5. **Use Dynamic Programming Iteratively:**
   - Transition from recursive to iterative dynamic programming to improve time and space efficiency for large inputs.

### Tips for Solving These Problems

- **Start with Base Cases:** Clearly define base cases to avoid infinite recursion and ensure correctness.
- **Think Recursively:** Break down the problem into smaller subproblems and think about how they build up to the solution.
- **Use Visual Aids:** Draw recursion trees or state diagrams to visualize problem-solving strategies and understand recursion flow.
- **Optimize with Memoization:** Implement memoization or tabulation to optimize solutions by storing and reusing previously computed results.
- **Test Edge Cases:** Consider edge cases and constraints to ensure robustness and handle special scenarios correctly.

By working through these problems and understanding their underlying recursive structure, you'll develop a strong grasp of when and how to apply Fibonacci-based recursion in various problem-solving contexts.

Sure! Here’s a detailed list of LeetCode problems categorized by different types of recursion, along with relevant LeetCode questions:

### **Types of Recursion**

1. **Fibonacci-Type Recursion**

   - **Classic Fibonacci Problems**
     - **[Fibonacci Number](https://leetcode.com/problems/fibonacci-number/) (Easy, Problem 509):** Find the nth Fibonacci number.
   - **Staircase Problems**
     - **[Climbing Stairs](https://leetcode.com/problems/climbing-stairs/) (Easy, Problem 70):** Count distinct ways to reach the top.
   - **Dynamic Programming Transitions**
     - **[House Robber](https://leetcode.com/problems/house-robber/) (Medium, Problem 198):** Maximize the amount of money robbed without robbing adjacent houses.
   - **Recursive Tree or Graph Traversals**
     - **[Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/) (Medium, Problem 96):** Count the number of unique BSTs that can be constructed with `n` nodes.
   - **Subproblem Overlap Problems**
     - **[Decode Ways](https://leetcode.com/problems/decode-ways/) (Medium, Problem 91):** Count the number of ways to decode a string encoded with A=1 to Z=26.
   - **Combinatorial Counting Problems**
     - **[Domino and Tromino Tiling](https://leetcode.com/problems/domino-and-tromino-tiling/) (Medium, Problem 790):** Count the number of ways to tile a 2xN board with dominoes and trominoes.
   - **Fibonacci-like Sequence Problems**
     - **[Tribonacci Number](https://leetcode.com/problems/tribonacci-number/) (Easy, Problem 1137):** Calculate the nth Tribonacci number where each number is the sum of the three preceding numbers.
   - **Path Counting in Grids**
     - **[Unique Paths](https://leetcode.com/problems/unique-paths/) (Medium, Problem 62):** Count the number of unique paths from the top-left to the bottom-right of a grid.
   - **Partitioning Problems**
     - **[Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) (Medium, Problem 416):** Determine if the array can be partitioned into two subsets with equal sum.
   - **Optimal Substructure Problems**
     - **[Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/) (Medium, Problem 300):** Find the length of the longest increasing subsequence.

2. **Longest Common Subsequence (LCS)**

   - **Longest Common Subsequence**
     - **[Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) (Medium, Problem 1143):** Find the length of the longest subsequence common to two sequences.
   - **Distinct Subsequences**
     - **[Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/) (Hard, Problem 115):** Count distinct subsequences of `s` that match `t`.
   - **Interleaving String**
     - **[Interleaving String](https://leetcode.com/problems/interleaving-string/) (Hard, Problem 97):** Determine if a string is an interleaving of two other strings.
   - **Edit Distance**
     - **[Edit Distance](https://leetcode.com/problems/edit-distance/) (Hard, Problem 72):** Compute the minimum number of operations to convert one string to another.

3. **Catalan Numbers**

   - **Unique Binary Search Trees**
     - **[Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/) (Medium, Problem 96):** Count the number of unique BSTs that can be constructed with `n` nodes.
   - **Unique Binary Search Trees II**
     - **[Unique Binary Search Trees II](https://leetcode.com/problems/unique-binary-search-trees-ii/) (Medium, Problem 95):** Generate all unique BSTs that can be constructed with `n` nodes.
   - **Different Ways to Add Parentheses**
     - **[Different Ways to Add Parentheses](https://leetcode.com/problems/different-ways-to-add-parentheses/) (Medium, Problem 241):** Generate all possible results from adding parentheses to an expression.

4. **Matrix-Based DP**

   - **Maximum Subarray**
     - **[Maximum Subarray](https://leetcode.com/problems/maximum-subarray/) (Easy, Problem 53):** Find the contiguous subarray with the maximum sum.
   - **Maximum Product Subarray**
     - **[Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/) (Medium, Problem 152):** Find the contiguous subarray with the maximum product.
   - **Minimum Path Sum**
     - **[Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/) (Medium, Problem 64):** Find the minimum path sum from top-left to bottom-right of a grid.
   - **Longest Increasing Path in a Matrix**
     - **[Longest Increasing Path in a Matrix](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/) (Hard, Problem 329):** Find the length of the longest increasing path in a matrix.

5. **Unbounded Knapsack**

   - **Coin Change**
     - **[Coin Change](https://leetcode.com/problems/coin-change/) (Medium, Problem 322):** Find the minimum number of coins needed to make a given amount.
   - **Complete Knapsack**
     - **[Coin Change II](https://leetcode.com/problems/coin-change-ii/) (Medium, Problem 518):** Count the number of ways to make a given amount with different denominations.
   - **Word Break II**
     - **[Word Break II](https://leetcode.com/problems/word-break-ii/) (Hard, Problem 140):** Return all possible sentences from a string using a dictionary of words.

6. **0/1 Knapsack**
   - **Partition Equal Subset Sum**
     - **[Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) (Medium, Problem 416):** Determine if a set can be partitioned into two subsets with equal sum.
   - **Best Time to Buy and Sell Stock**
     - **[Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) (Easy, Problem 121):** Maximize profit from buying and selling stock with constraints.
   - **Maximum Profit in Job Scheduling**
     - **[Maximum Profit in Job Scheduling](https://leetcode.com/problems/maximum-profit-in-job-scheduling/) (Hard, Problem 1235):** Find the maximum profit from scheduling jobs with deadlines and profits.

This list includes a comprehensive range of problems across various recursion types and dynamic programming patterns, offering a well-rounded preparation for FAANG interviews.

## DP

Sure, here’s a comprehensive list of dynamic programming (DP) patterns, including relevant LeetCode questions for each pattern:

### **Dynamic Programming Patterns and Relevant LeetCode Questions**

1. **Fibonacci-Type Recursion**
   - **Classic Fibonacci Problems**
     - **[Fibonacci Number](https://leetcode.com/problems/fibonacci-number/) (Easy, Problem 509):** Find the nth Fibonacci number.
   - **Staircase Problems**
     - **[Climbing Stairs](https://leetcode.com/problems/climbing-stairs/) (Easy, Problem 70):** Count distinct ways to reach the top.
   - **Dynamic Programming Transitions**
     - **[House Robber](https://leetcode.com/problems/house-robber/) (Medium, Problem 198):** Maximize the amount of money robbed without robbing adjacent houses.
   - \*\*Recursive

Sure! Here’s a comprehensive list of dynamic programming (DP) patterns and types of recursion, along with relevant LeetCode questions for each pattern:

### **Dynamic Programming Patterns and Relevant LeetCode Questions**

1. **Fibonacci-Type Recursion**

   - **Classic Fibonacci Problems**
     - **[Fibonacci Number](https://leetcode.com/problems/fibonacci-number/) (Easy, Problem 509):** Find the nth Fibonacci number.
   - **Staircase Problems**
     - **[Climbing Stairs](https://leetcode.com/problems/climbing-stairs/) (Easy, Problem 70):** Count distinct ways to reach the top.
   - **Dynamic Programming Transitions**
     - **[House Robber](https://leetcode.com/problems/house-robber/) (Medium, Problem 198):** Maximize the amount of money robbed without robbing adjacent houses.
   - **Recursive Tree or Graph Traversals**
     - **[Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/) (Medium, Problem 96):** Count the number of unique BSTs that can be constructed with `n` nodes.
   - **Subproblem Overlap Problems**
     - **[Decode Ways](https://leetcode.com/problems/decode-ways/) (Medium, Problem 91):** Count the number of ways to decode a string encoded with A=1 to Z=26.
   - **Combinatorial Counting Problems**
     - **[Domino and Tromino Tiling](https://leetcode.com/problems/domino-and-tromino-tiling/) (Medium, Problem 790):** Count the number of ways to tile a 2xN board with dominoes and trominoes.
   - **Fibonacci-like Sequence Problems**
     - **[Tribonacci Number](https://leetcode.com/problems/tribonacci-number/) (Easy, Problem 1137):** Calculate the nth Tribonacci number where each number is the sum of the three preceding numbers.
   - **Path Counting in Grids**
     - **[Unique Paths](https://leetcode.com/problems/unique-paths/) (Medium, Problem 62):** Count the number of unique paths from the top-left to the bottom-right of a grid.
   - **Partitioning Problems**
     - **[Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) (Medium, Problem 416):** Determine if the array can be partitioned into two subsets with equal sum.
   - **Optimal Substructure Problems**
     - **[Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/) (Medium, Problem 300):** Find the length of the longest increasing subsequence.

2. **Longest Common Subsequence (LCS)**

   - **Longest Common Subsequence**
     - **[Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) (Medium, Problem 1143):** Find the length of the longest subsequence common to two sequences.
   - **Distinct Subsequences**
     - **[Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/) (Hard, Problem 115):** Count distinct subsequences of `s` that match `t`.
   - **Interleaving String**
     - **[Interleaving String](https://leetcode.com/problems/interleaving-string/) (Hard, Problem 97):** Determine if a string is an interleaving of two other strings.
   - **Edit Distance**
     - **[Edit Distance](https://leetcode.com/problems/edit-distance/) (Hard, Problem 72):** Compute the minimum number of operations to convert one string to another.

3. **Catalan Numbers**

   - **Unique Binary Search Trees**
     - **[Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/) (Medium, Problem 96):** Count the number of unique BSTs that can be constructed with `n` nodes.
   - **Unique Binary Search Trees II**
     - **[Unique Binary Search Trees II](https://leetcode.com/problems/unique-binary-search-trees-ii/) (Medium, Problem 95):** Generate all unique BSTs that can be constructed with `n` nodes.
   - **Different Ways to Add Parentheses**
     - **[Different Ways to Add Parentheses](https://leetcode.com/problems/different-ways-to-add-parentheses/) (Medium, Problem 241):** Generate all possible results from adding parentheses to an expression.

4. **Matrix-Based DP**

   - **Maximum Subarray**
     - **[Maximum Subarray](https://leetcode.com/problems/maximum-subarray/) (Easy, Problem 53):** Find the contiguous subarray with the maximum sum.
   - **Maximum Product Subarray**
     - **[Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/) (Medium, Problem 152):** Find the contiguous subarray with the maximum product.
   - **Minimum Path Sum**
     - **[Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/) (Medium, Problem 64):** Find the minimum path sum from top-left to bottom-right of a grid.
   - **Longest Increasing Path in a Matrix**
     - **[Longest Increasing Path in a Matrix](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/) (Hard, Problem 329):** Find the length of the longest increasing path in a matrix.

5. **Unbounded Knapsack**

   - **Coin Change**
     - **[Coin Change](https://leetcode.com/problems/coin-change/) (Medium, Problem 322):** Find the minimum number of coins needed to make a given amount.
   - **Complete Knapsack**
     - **[Coin Change II](https://leetcode.com/problems/coin-change-ii/) (Medium, Problem 518):** Count the number of ways to make a given amount with different denominations.
   - **Word Break II**
     - **[Word Break II](https://leetcode.com/problems/word-break-ii/) (Hard, Problem 140):** Return all possible sentences from a string using a dictionary of words.

6. **0/1 Knapsack**
   - **Partition Equal Subset Sum**
     - **[Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) (Medium, Problem 416):** Determine if a set can be partitioned into two subsets with equal sum.
   - **Best Time to Buy and Sell Stock**
     - **[Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) (Easy, Problem 121):** Maximize profit from buying and selling stock with constraints.
   - **Maximum Profit in Job Scheduling**
     - **[Maximum Profit in Job Scheduling](https://leetcode.com/problems/maximum-profit-in-job-scheduling/) (Hard, Problem 1235):** Find the maximum profit from scheduling jobs with deadlines and profits.

This list covers a range of DP patterns and recursion types, providing a solid foundation for preparing for FAANG interviews.

## Backtracking

Certainly! Here’s a comprehensive list of backtracking patterns and relevant LeetCode questions that cover various concepts and nuances of backtracking:

### **Backtracking Patterns and Relevant LeetCode Questions**

1. **Combination Problems**

   - **Combination Sum**
     - **[Combination Sum](https://leetcode.com/problems/combination-sum/) (Medium, Problem 39):** Find all unique combinations of numbers that add up to a target.
   - **Combination Sum II**
     - **[Combination Sum II](https://leetcode.com/problems/combination-sum-ii/) (Medium, Problem 40):** Find all unique combinations where numbers can be used multiple times, and there are no duplicates.
   - **Combinations**
     - **[Combinations](https://leetcode.com/problems/combinations/) (Medium, Problem 77):** Find all possible combinations of `k` numbers out of `n` numbers.

2. **Permutation Problems**

   - **Permutations**
     - **[Permutations](https://leetcode.com/problems/permutations/) (Medium, Problem 46):** Generate all possible permutations of a list of numbers.
   - **Permutations II**
     - **[Permutations II](https://leetcode.com/problems/permutations-ii/) (Medium, Problem 47):** Generate all possible unique permutations with possible duplicates.
   - **Letter Combinations of a Phone Number**
     - **[Letter Combinations of a Phone Number](https://leetcode.com/problems/letter-combinations-of-a-phone-number/) (Medium, Problem 17):** Generate all possible letter combinations from a phone number.

3. **Subset Problems**

   - **Subsets**
     - **[Subsets](https://leetcode.com/problems/subsets/) (Medium, Problem 78):** Generate all possible subsets of a set.
   - **Subsets II**
     - **[Subsets II](https://leetcode.com/problems/subsets-ii/) (Medium, Problem 90):** Generate all possible subsets, including duplicates.
   - **Generate Parentheses**
     - **[Generate Parentheses](https://leetcode.com/problems/generate-parentheses/) (Medium, Problem 22):** Generate all combinations of well-formed parentheses.

4. **N-Queens Problems**

   - **N-Queens**
     - **[N-Queens](https://leetcode.com/problems/n-queens/) (Hard, Problem 51):** Solve the N-Queens problem by placing N queens on an N×N chessboard.
   - **N-Queens II**
     - **[N-Queens II](https://leetcode.com/problems/n-queens-ii/) (Hard, Problem 52):** Return the number of distinct solutions to the N-Queens problem.

5. **Pathfinding Problems**

   - **Word Search**
     - **[Word Search](https://leetcode.com/problems/word-search/) (Medium, Problem 79):** Determine if a word exists in a 2D board of letters by tracing a path.
   - **Sudoku Solver**
     - **[Sudoku Solver](https://leetcode.com/problems/sudoku-solver/) (Hard, Problem 37):** Solve a Sudoku puzzle by filling in empty cells.

6. **Partition Problems**

   - **Partition to K Equal Sum Subsets**
     - **[Partition to K Equal Sum Subsets](https://leetcode.com/problems/partition-to-k-equal-sum-subsets/) (Medium, Problem 416):** Determine if you can partition the array into `k` subsets with equal sum.
   - **Palindrome Partitioning**
     - **[Palindrome Partitioning](https://leetcode.com/problems/palindrome-partitioning/) (Medium, Problem 131):** Partition a string into all possible palindromic substrings.

7. **Combination of Sum with Constraints**
   - **Combination Sum III**
     - **[Combination Sum III](https://leetcode.com/problems/combination-sum-iii/) (Medium, Problem 216):** Find all possible combinations of `k` numbers that add up to a target number, with constraints.

### Example LeetCode Problems

Here are some specific LeetCode problems that fit these categories:

- **[Combination Sum](https://leetcode.com/problems/combination-sum/) (Medium, Problem 39):** Find all unique combinations of numbers that add up to a target.
- **[Permutations](https://leetcode.com/problems/permutations/) (Medium, Problem 46):** Generate all possible permutations of a list of numbers.
- **[Subsets](https://leetcode.com/problems/subsets/) (Medium, Problem 78):** Generate all possible subsets of a set.
- **[N-Queens](https://leetcode.com/problems/n-queens/) (Hard, Problem 51):** Solve the N-Queens problem by placing N queens on an N×N chessboard.
- **[Word Search](https://leetcode.com/problems/word-search/) (Medium, Problem 79):** Determine if a word exists in a 2D board of letters by tracing a path.
- **[Sudoku Solver](https://leetcode.com/problems/sudoku-solver/) (Hard, Problem 37):** Solve a Sudoku puzzle by filling in empty cells.

This list covers a range of backtracking problems and provides a solid foundation for preparing for FAANG interviews.

## DP with 1D/2D array

Here’s a comprehensive list of LeetCode problems that cover the various concepts and nuances of 1D and 2D dynamic programming (DP). This list includes problems related to common DP patterns and techniques.

### **1D Dynamic Programming Problems**

1. **Classic DP Problems**

   - **[Climbing Stairs](https://leetcode.com/problems/climbing-stairs/) (Easy, Problem 70):** Calculate distinct ways to reach the top of a staircase.
   - **[House Robber](https://leetcode.com/problems/house-robber/) (Medium, Problem 198):** Maximize the amount of money robbed from non-adjacent houses.
   - **[Maximum Subarray](https://leetcode.com/problems/maximum-subarray/) (Easy, Problem 53):** Find the contiguous subarray with the maximum sum.
   - **[Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) (Easy, Problem 121):** Find the maximum profit from buying and selling stock once.

2. **Subsequence/Substring Problems**

   - **[Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/) (Medium, Problem 300):** Find the length of the longest increasing subsequence.
   - **[Edit Distance](https://leetcode.com/problems/edit-distance/) (Hard, Problem 72):** Compute the minimum number of operations required to convert one string to another.
   - **[Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/) (Medium, Problem 5):** Find the longest palindromic substring in a given string.

3. **Knapsack/Combination Problems**

   - **[Unbounded Knapsack](https://leetcode.com/problems/coin-change/) (Medium, Problem 322):** Find the minimum number of coins needed to make up a given amount.
   - **[Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) (Medium, Problem 416):** Determine if the array can be partitioned into two subsets with equal sum.

4. **Optimization Problems**
   - **[Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/) (Medium, Problem 64):** Find the minimum path sum from the top-left to the bottom-right corner of a grid.
   - **[Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/) (Medium, Problem 152):** Find the contiguous subarray with the maximum product.

### **2D Dynamic Programming Problems**

1. **Grid/Matrix Problems**

   - **[Unique Paths](https://leetcode.com/problems/unique-paths/) (Medium, Problem 62):** Find the number of unique paths from the top-left to the bottom-right corner of a grid.
   - **[Unique Paths II](https://leetcode.com/problems/unique-paths-ii/) (Medium, Problem 63):** Find unique paths with obstacles.
   - **[Maximum Square](https://leetcode.com/problems/maximal-square/) (Medium, Problem 221):** Find the largest square containing only 1s in a binary matrix.

2. **Substring/Substring Problems**

   - **[Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) (Medium, Problem 1143):** Find the length of the longest common subsequence between two strings.
   - **[Edit Distance](https://leetcode.com/problems/edit-distance/) (Hard, Problem 72):** Compute the minimum number of operations required to convert one string to another (also included in 1D).

3. **Interval/Range Problems**

   - **[Range Sum Query 2D - Immutable](https://leetcode.com/problems/range-sum-query-2d-immutable/) (Medium, Problem 304):** Compute the sum of elements in a submatrix.
   - **[Maximal Rectangle](https://leetcode.com/problems/maximal-rectangle/) (Hard, Problem 85):** Find the largest rectangle containing only 1s in a binary matrix.

4. **Pathfinding Problems**
   - **[Minimum Falling Path Sum](https://leetcode.com/problems/minimum-falling-path-sum/) (Medium, Problem 931):** Find the minimum path sum starting from any element in the top row of a matrix to any element in the bottom row.

### **Example LeetCode Problems**

- **[Climbing Stairs](https://leetcode.com/problems/climbing-stairs/) (Easy, Problem 70)**
- **[House Robber](https://leetcode.com/problems/house-robber/) (Medium, Problem 198)**
- **[Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/) (Medium, Problem 300)**
- **[Edit Distance](https://leetcode.com/problems/edit-distance/) (Hard, Problem 72)**
- **[Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/) (Medium, Problem 5)**
- **[Unique Paths](https://leetcode.com/problems/unique-paths/) (Medium, Problem 62)**
- **[Maximum Square](https://leetcode.com/problems/maximal-square/) (Medium, Problem 221)**
- **[Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) (Medium, Problem 1143)**
- **[Maximal Rectangle](https://leetcode.com/problems/maximal-rectangle/) (Hard, Problem 85)**

This list provides a broad coverage of key 1D and 2D dynamic programming concepts and problems. Solving these problems will help in understanding and applying different DP techniques effectively.

## Set Bit : The first occurrence of 1 from the left side e.g. 00000[1]001

0 -> 000
1 -> 001
2 -> 010
3 -> 011
4 -> 100
5 -> 101
6 -> 110
7 -> 111
8 -> 1000
9 -> 1001

# Eiter searching/sorting or in which ds to store the data as needed
