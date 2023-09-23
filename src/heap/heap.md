# Heap

it helps to find maximum and minimum element from array

# when to use it?

it makes getting the maximum and minimum value easy and
so when needs to access min or max value = trees.heap

# Binary Tree: a node could have only two children

# Complete Binary Tree

Where all level has completely filled except last level's right side

# Incomplete Binary Tree

Where all level has completely filled except last level's left side

# Priority Queue

it allows us to find min/max element from a collection at O(1) time

it supports following operations:

1) insert(key): insert a key into the Priority Queue
2) deleteMin()/deleteMin(): return and remove the max/min key
3) getMax()/getMin(): return largest/smallest key

# Binary Heap

it's a data structure that helps to implement Priority Queue operations efficiently.

1) `Binary Heap` is implemented via `Array` and first entry is takes an empty
2) A `Binary Heap` is actually a complete `Binary Tree`; the values are stored in array by traversing (i.e. visiting
   each node) tree level by level from left to right

# Max Binary Heap: Each Node's value will be >= than the values of its children
# Min Binary Heap: Each Node's value will be <= than the values of its children