## All types Binary Tree are as follows:

1. Binary Search Tree (BST)
   Properties: For every node, all elements in the left subtree are less than the node, and all elements in the right subtree are greater.
   Use Cases: Efficient searching, sorting, and maintaining a dynamically changing dataset.

2. AVL Tree
   Properties: A self-balancing binary search tree where the difference between heights of left and right subtrees cannot be more than one for any node.
   Use Cases: Implementations where frequent insertions and deletions are involved, and balance is needed to ensure O(log n) search time.

3. Red-Black Tree
   Properties: A self-balancing binary search tree with an extra bit for denoting the color of the node. It ensures that the tree remains approximately balanced through rotations and color changes.
   Use Cases: Implementing associative arrays, Java's TreeMap and TreeSet, C++'s std::map and std::set.

4. Splay Tree
   Properties: A self-balancing binary search tree that moves the recently accessed item to the root of the tree using a splay operation.
   Use Cases: Cache implementations, where recently accessed elements are expected to be accessed again soon.

5. B-tree / B+ tree
   Properties: A self-balancing tree structure that maintains sorted data and allows searches, sequential access, insertions, and deletions in logarithmic time. B+ trees are a variant of B-trees with all values stored at the leaf level.
   Use Cases: Database indexes and filesystems where large blocks of data are handled and disk reads are expensive.

6. Binary Heap
   Properties: A complete binary tree where each node is smaller (Min Heap) or larger (Max Heap) than its children. The tree is completely filled except possibly for the bottom level, which is filled from left to right.
   Use Cases: Implementing priority queues, heap sort algorithm, scheduling algorithms.

7. Trie (Prefix Tree)
   Properties: Though not strictly a binary tree, a trie is a tree-like data structure where each node represents a character of a string. All descendants of a node have a common prefix.
   Use Cases: Autocomplete features, spell checkers, IP routing.

8. Segment Tree
   Properties: A binary tree used for storing intervals or segments. Each node represents an interval.
   Use Cases: Range queries and updates over an array, such as finding the sum or minimum/maximum of elements within a given range.

9. Fenwick Tree (Binary Indexed Tree)
   Properties: A data structure that provides efficient methods for calculating prefix sums in a table of numbers.
   Use Cases: Range queries and updates in log(n) time, especially useful in scenarios where there's a mix of query and update operations.

10. Expression Tree
    Properties: A binary tree where each leaf represents an operand, and each internal node represents an operator.
    Use Cases: Parsing and evaluating expressions, compilers and calculators.


Each type of binary tree is optimized for specific kinds of operations and use cases, making them versatile tools in the arsenal of a software developer.