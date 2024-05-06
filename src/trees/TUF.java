package trees;

import java.util.*;

// DFS : PreOrder Traversal T = O(n) , S = O(n)

public class TUF {

    static class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            left = null;
            right = null;
        }

        Node() {
        }
    }

    static class Pair {
        Node node;
        int num;

        Pair(Node _node, int _num) {
            num = _num;
            node = _node;
        }
    }

    // DFS: preOrder traversal (recursive solution)
    static void preOrderTrav(Node curr, ArrayList<Integer> preOrder) { // T = O(n), S = O(n)
        if (curr == null)
            return;

        preOrder.add(curr.data);
        preOrderTrav(curr.left, preOrder);
        preOrderTrav(curr.right, preOrder);
    }

    // DFS: preOrder (iterative solution)
    static ArrayList<Integer> preOrderTrav(Node curr) { // T = O(n), S = O(n)
        ArrayList<Integer> preOrder = new ArrayList<Integer>();
        if (curr == null)
            return preOrder;

        Stack<Node> s = new Stack<>();
        s.push(curr);

        while (!s.isEmpty()) {
            Node topNode = s.peek();
            preOrder.add(topNode.data);
            s.pop();
            if (topNode.right != null)
                s.push(topNode.right);
            if (topNode.left != null)
                s.push(topNode.left);
        }
        return preOrder;
    }

    // DFS: inOrder (recursive solution)
    static void inOrderTrav(Node curr, ArrayList<Integer> inOrder) { // T = O(n), S = O(n)
        if (curr == null)
            return;

        inOrderTrav(curr.left, inOrder);
        inOrder.add(curr.data);
        inOrderTrav(curr.right, inOrder);
    }

    // DFS: inOrder (iterative solution)
    static ArrayList<Integer> inOrderTrav(Node curr) { // T = O(n), S = O(n)
        ArrayList<Integer> inOrder = new ArrayList<>();
        Stack<Node> s = new Stack<>();
        while (true) {
            if (curr != null) {
                s.push(curr);
                curr = curr.left;
            } else {
                if (s.isEmpty())
                    break;
                curr = s.peek();
                inOrder.add(curr.data);
                s.pop();
                curr = curr.right;
            }
        }
        return inOrder;
    }

    // DFS: PostOrder (Left Subtree, Right Subtree, Root) -> recursive solution
    static void postOrderTrav(Node curr, ArrayList<Integer> postOrder) { // T = O(n), S = O(n)
        if (curr == null)
            return;

        postOrderTrav(curr.left, postOrder);
        postOrderTrav(curr.right, postOrder);
        postOrder.add(curr.data);
    }

    // DFS: PostOrder (Left Subtree, Right Subtree, Root) -> iterative solution
    // using 1 stack
    static ArrayList<Integer> postOrderTrav(Node cur) {
        ArrayList<Integer> postOrder = new ArrayList<>();
        if (cur == null)
            return postOrder;

        Stack<Node> st = new Stack<>();
        while (cur != null || !st.isEmpty()) {

            if (cur != null) {
                st.push(cur);
                cur = cur.left;
            } else {
                Node temp = st.peek().right;
                if (temp == null) {
                    temp = st.peek();
                    st.pop();
                    postOrder.add(temp.data);
                    while (!st.isEmpty() && temp == st.peek().right) {
                        temp = st.peek();
                        st.pop();
                        postOrder.add(temp.data);
                    }
                } else
                    cur = temp;
            }
        }
        return postOrder;
    }

    // DFS: InOrder, PreOrder and PostOrder Traversals together (iterative) using 1
    // stack
    static void allTraversal(Node root, List<Integer> pre, List<Integer> in, List<Integer> post) {
        Stack<Pair> st = new Stack<Pair>();
        st.push(new Pair(root, 1));

        if (root == null)
            return;

        while (!st.isEmpty()) {
            Pair it = st.pop();

            // this is part of pre increment 1 to 2

            // push the left side of the tree
            if (it.num == 1) {
                pre.add(it.node.data);
                it.num++;
                st.push(it);

                if (it.node.left != null) {
                    st.push(new Pair(it.node.left, 1));
                }
            }

            // this is a part of in increment 2 to 3

            // push right
            else if (it.num == 2) {
                in.add(it.node.data);
                it.num++;
                st.push(it);

                if (it.node.right != null) {
                    st.push(new Pair(it.node.right, 1));
                }
            }
            // don't push it back again
            else {
                post.add(it.node.data);
            }
        }

    }

    // BFS: Level Order Traversal (iterative)
    static List<Integer> levelOrder(Node root) {
        Queue<Node> queue = new LinkedList<>();
        List<Integer> wrapList = new ArrayList<>();

        if (root == null)
            return wrapList;

        // concept: Add the root node, check its left and right and pop/poll the current
        // node and store it in a 2d array

        queue.offer(root); // adds the root node to (e.g. value = 1) `queue`

        // for (Node node : queue) System.out.println(node.data + " ");

        while (!queue.isEmpty()) {
            if (queue.peek().left != null) {
                queue.offer(queue.peek().left);
            }

            if (queue.peek().right != null) {
                queue.offer(queue.peek().right);
            }

            wrapList.add(queue.poll().data);
        }
        return wrapList;
    }

    // recursive
    static int maxDepth(Node root) { // T = O(N), S = O(H = Recursion stack space where "H" is height of the binary
        // tree)
        if (root == null)
            return 0;

        int lh = maxDepth(root.left);
        int rh = maxDepth(root.right);

        return 1 + Math.max(lh, rh);
    }

    // Here used BFS traversal (i.e. level order traversal) and iterative approach
    static int levelOrderMaxDepth(Node root) {
        if (root == null) {
            return 0;
        }

        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(root);

        int level = 0;

        while (queue.size() > 0) {
            int size = queue.size();

            while (size-- > 0) {
                Node remNode = queue.removeFirst();
                if (remNode.left != null) {
                    queue.addLast(remNode.left);
                }
                if (remNode.right != null) {
                    queue.addLast(remNode.right);
                }
            }

            level++;
        }

        return level;
    }

    static boolean isBalanced(Node root) { // T = O(N), S = O(N)
        return dfsHeight(root) != -1;
    }

    static int dfsHeight(Node root) {
        if (root == null)
            return 0;

        int leftHeight = dfsHeight(root.left);
        if (leftHeight == -1)
            return -1; // secondary or explicit base case
        int rightHeight = dfsHeight(root.right);
        if (rightHeight == -1)
            return -1; // secondary or explicit base case

        if (Math.abs(leftHeight - rightHeight) > 1)
            return -1;
        return Math.max(leftHeight, rightHeight) + 1;

    }

    static int diameterOfBinaryTree(Node root) { // T = O(N), S = 0(1) + O(H = height of the tree)
        int[] diameter = new int[1];
        height(root, diameter);
        return diameter[0];
    }

    private static int height(Node root, int[] diameter) {
        if (root == null) {
            return 0;
        }

        int lh = height(root.left, diameter);
        int rh = height(root.right, diameter);
        diameter[0] = Math.max(diameter[0], lh + rh);
        return 1 + Math.max(lh, rh);
    }

    static boolean isIdentical(Node node1, Node node2) { // T = O(N), S = O(N)
        if (node1 == null && node2 == null)
            return true;
        else if (node1 == null || node2 == null)
            return false;

        return ((node1.data == node2.data) && isIdentical(node1.left, node2.left) && isIdentical(node1.right, node2.right));
    }

    // Approach: Boolean Flag + Queue + ArrayList (recommended approach)
    static ArrayList<ArrayList<Integer>> zigzagLevelOrder(Node root) { // T = O(N), S = O(N)
        ArrayList<ArrayList<Integer>> wrapList = new ArrayList<>();
        if (root == null) return wrapList;

        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            ArrayList<Integer> subList = new ArrayList<Integer>(levelSize);
            for (int i = 0; i < levelSize; i++) {
                int index = i;
                if (queue.peek().left != null)
                    queue.offer(queue.peek().left); // add it to the queue
                if (queue.peek().right != null)
                    queue.offer(queue.peek().right); // add it to the queue

                if (leftToRight == true) {
                    subList.add(queue.poll().data); // remove from queue and add it to `subList`
                } else {
                    subList.add(0, queue.poll().data); // // remove from queue by index & add it to `subList`
                }
            }
            leftToRight = !leftToRight;
            wrapList.add(subList);
        }
        return wrapList;
    }

    // Approach: ArrayList + Queue + LinkedList
    static ArrayList<ArrayList<Integer>> zigzagLevelOrderV1(Node root) { // T = O(N), S = O(N)
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if (root == null) return result;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        boolean zigzag = false; // false for left to right, true for right to left

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            LinkedList<Integer> levelNodes = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                Node currentNode = queue.poll();
                // If zigzag is false, insert at the end. If true, insert at the beginning.
                if (zigzag) {
                    levelNodes.addFirst(currentNode.data);
                } else {
                    levelNodes.addLast(currentNode.data);
                }

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
            // Prepare for the next level
            zigzag = !zigzag;

            // Add the current level's nodes to the `result`
            result.add(new ArrayList<>(levelNodes));
        }

        return result;
    }

    // Approach = 2 stacks
    static ArrayList<ArrayList<Integer>> zigzagLevelOrderV2(Node root) { // T = O(N), S = O(N)
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if (root == null) return result;

        Stack<Node> currentLevel = new Stack<>();
        Stack<Node> nextLevel = new Stack<>();
        currentLevel.push(root);

        boolean leftToRight = true; // since first level will go to leftToRight always thus true
        ArrayList<Integer> levelNodes = new ArrayList<>();

        while (!currentLevel.isEmpty()) {
            Node currentNode = currentLevel.pop();
            levelNodes.add(currentNode.data);

            // If leftToRight is true, push left child first then right child to nextLevel stack
            // else push right child first then left child to ensure next level is processed in the opposite order
            if (leftToRight) {
                if (currentNode.left != null) nextLevel.push(currentNode.left);
                if (currentNode.right != null) nextLevel.push(currentNode.right);
            } else {
                if (currentNode.right != null) nextLevel.push(currentNode.right);
                if (currentNode.left != null) nextLevel.push(currentNode.left);
            }

            // If the current level is completely processed
            if (currentLevel.isEmpty()) {
                result.add(levelNodes);
                levelNodes = new ArrayList<>();
                // Swap the stacks
                Stack<Node> temp = currentLevel;
                currentLevel = nextLevel;
                nextLevel = temp;
                // Reverse the traversal direction
                leftToRight = !leftToRight;
            }
        }

        return result;
    }

    static ArrayList<ArrayList<Integer>> zigzagLevelOrderWithDeque(Node root) { // T = O(N), S = O(N)
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Deque<Node> deque = new LinkedList<>();
        deque.offer(root);
        boolean leftToRight = true;

        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            ArrayList<Integer> levelNodes = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                // Depending on the traversal direction, remove from the front or the back
                Node currentNode = leftToRight ? deque.pollFirst() : deque.pollLast();

                levelNodes.add(currentNode.data);

                // If traversing left to right, add children to the back in their natural order
                if (leftToRight) {
                    if (currentNode.left != null) deque.offerLast(currentNode.left);
                    if (currentNode.right != null) deque.offerLast(currentNode.right);
                }
                // If traversing right to left, add children to the front in reverse order
                else {
                    if (currentNode.right != null) deque.offerFirst(currentNode.right);
                    if (currentNode.left != null) deque.offerFirst(currentNode.left);
                }
            }
            // Prepare for the next level by flipping the direction
            leftToRight = !leftToRight;
            result.add(levelNodes);
        }
        return result;
    }

    static Boolean isLeaf(Node node) {
        return (node.left == null) && (node.right == null);
    }

    static void addLeftBoundary(Node root, ArrayList<Integer> res) {
        Node curr = root.left;

        // while loop will try to add "left nodes as much as possible" & add right node only if curr.left is null

        while (curr != null) {
            if (!isLeaf(curr)) res.add(curr.data);
            if (curr.left != null) {
                curr = curr.left;
            } else curr = curr.right;
        }

        // N.B: after the while loop from above, node could become a leaf Node or it could have left or right Node null
        // so, within `addLeaves method` , those handed
    }

    static void addRightBoundary(Node root, ArrayList<Integer> res) {
        Node curr = root.right;

        ArrayList<Integer> tmp = new ArrayList<>();

        // while loop will try to add "right nodes as much as possible" & add left node only if curr.right is null
        while (curr != null) {
            if (!isLeaf(curr)) tmp.add(curr.data);
            if (curr.right != null) {
                curr = curr.right;
            } else curr = curr.left;
        }
        int i;

        // reverse nodes from rightBoundary i.e. added to `tmp`
        for (i = tmp.size() - 1; i >= 0; --i) {
            res.add(tmp.get(i));
        }
    }

    static void addLeaves(Node root, ArrayList<Integer> res) {
        if (isLeaf(root)) {
            res.add(root.data);
            return;
        }

        if (root.left != null) addLeaves(root.left, res);
        if (root.right != null) addLeaves(root.right, res);
    }

    static ArrayList<Integer> printBoundary(Node node) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        if (!isLeaf(node)) ans.add(node.data);

        addLeftBoundary(node, ans);
        addLeaves(node, ans);
        addRightBoundary(node, ans);
        return ans;
    }


    // Method for boundary traversal
    static List<Integer> boundaryTraversal(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        if (!isLeaf(root)) result.add(root.data); // Add root if it's not a leaf node

        traverseLeft(root.left, result, true); // Traverse left boundary
        traverseRight(root.right, result, true); // Traverse right boundary in reverse

        return result;
    }

    // Helper method for left boundary
    static void traverseLeft(Node node, List<Integer> result, boolean isBoundary) {
        if (node == null) return;

        if (isBoundary || isLeaf(node)) result.add(node.data);

        traverseLeft(node.left, result, isBoundary);
        traverseLeft(node.right, result, isBoundary && node.left == null);
    }

    // Helper method for right boundary
    static void traverseRight(Node node, List<Integer> result, boolean isBoundary) {
        if (node == null) return;

        traverseRight(node.left, result, isBoundary && node.right == null);
        traverseRight(node.right, result, isBoundary);

        if (isBoundary || isLeaf(node)) result.add(node.data);
    }

    private static class Tuple {
        Node node;
        int row;
        int col;

        public Tuple(Node _node, int _row, int _col) {
            this.node = _node;
            this.row = _row;
            this.col = _col;
        }
    }

    public static List<List<Integer>> findVertical(Node root) { // T = O(N*logN*logN*logN), S = O(N)
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();
        Queue<Tuple> q = new LinkedList<Tuple>();
        q.offer(new Tuple(root, 0, 0));
        while (!q.isEmpty()) {
            Tuple tuple = q.poll();
            Node node = tuple.node;
            int x = tuple.row;
            int y = tuple.col;


            if (!map.containsKey(x)) {
                map.put(x, new TreeMap<>());
            }
            if (!map.get(x).containsKey(y)) {
                map.get(x).put(y, new PriorityQueue<>());
            }
            map.get(x).get(y).offer(node.data);

            if (node.left != null) {
                q.offer(new Tuple(node.left, x - 1, y + 1));
            }
            if (node.right != null) {
                q.offer(new Tuple(node.right, x + 1, y + 1));
            }
        }
        List<List<Integer>> list = new ArrayList<>();
        for (TreeMap<Integer, PriorityQueue<Integer>> ys : map.values()) {
            list.add(new ArrayList<>());
            for (PriorityQueue<Integer> nodes : ys.values()) {
                while (!nodes.isEmpty()) {
                    list.get(list.size() - 1).add(nodes.poll());
                }
            }
        }
        return list;
    }

    static ArrayList<Integer> topView(Node root) { // T = O(N), S = O(N)
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null) return ans;
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<Pair> q = new LinkedList<Pair>();
        q.add(new Pair(root, 0));
        while (!q.isEmpty()) {
            Pair it = q.remove();
            int hd = it.num;
            Node temp = it.node;
            map.computeIfAbsent(hd, k -> temp.data);
            if (temp.left != null) {

                q.add(new Pair(temp.left, hd - 1));
            }
            if (temp.right != null) {

                q.add(new Pair(temp.right, hd + 1));
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            ans.add(entry.getValue());
        }
        return ans;

    }

    static ArrayList<Integer> bottomView(Node root) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null) return ans;
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<Node> q = new LinkedList<Node>();
        root.data = 0;
        q.add(root);
        while (!q.isEmpty()) {
            Node temp = q.remove();
            int hd = temp.data;
            map.put(hd, temp.data);
            if (temp.left != null) {
                temp.left.data = hd - 1;
                q.add(temp.left);
            }
            if (temp.right != null) {
                temp.right.data = hd + 1;
                q.add(temp.right);
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            ans.add(entry.getValue());
        }

        return ans;
    }

    static boolean isSymmetricUtil(Node root1, Node root2) {
        if (root1 == null || root2 == null) return root1 == root2;

        return (root1.data == root2.data) && isSymmetricUtil(root1.left, root2.right) && isSymmetricUtil(root1.right, root2.left);
    }

    static boolean isSymmetric(Node root) {
        if (root == null) return true;
        return isSymmetricUtil(root.left, root.right);
    }


    private static List<Integer> lightSideView(Node root) {
        List<Integer> result = new ArrayList<Integer>();
        leftView(root, result, 0);
        return result;
    }

    private static void leftView(Node curr, List<Integer> result, int currDepth) { // O(N), O(H)
        if (curr == null) {
            return;
        }
        if (currDepth == result.size()) {
            result.add(curr.data);
        }

        leftView(curr.left, result, currDepth + 1);
        leftView(curr.right, result, currDepth + 1);
    }

    private static List<Integer> rightSideView(Node root) {
        List<Integer> result = new ArrayList<Integer>();
        rightView(root, result, 0);
        return result;
    }

    private static void rightView(Node curr, List<Integer> result, int currDepth) { // O(N), O(H)
        if (curr == null) {
            return;
        }

        if (currDepth == result.size()) {
            result.add(curr.data);
        }

        rightView(curr.right, result, currDepth + 1);
        rightView(curr.left, result, currDepth + 1);
    }

    static boolean getPath(Node root, ArrayList<Integer> arr, int x) {
        // if root is NULL then there is no path
        if (root == null) {
            return false;
        }

        // push the node's value in 'arr'
        arr.add(root.data);

        // check if it is the required node return true
        if (root.data == x) {
            return true;
        }

        // otherwise, check whether the required node lies in the left subtree or right subtree of the current node
        if (getPath(root.left, arr, x) || getPath(root.right, arr, x)) {
            return true;
        }

        // required node does not lie either in the
        // left or right subtree of the current node
        // Thus, remove current node's value from
        // 'arr' and then return false
        arr.remove(arr.size() - 1);
        return false;
    }

    static Node lowestCommonAncestorRec(Node root, Node p, Node q) {
        //base case
        if (root == null || root == p || root == q) {
            return root;
        }
        Node left = lowestCommonAncestorRec(root.left, p, q);
        Node right = lowestCommonAncestorRec(root.right, p, q);

        //result
        if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else { //both left and right are not null, we found our result
            return root;
        }
    }

    static Node lowestCommonAncestor(Node root, Node p, Node q) {
        Map<Node, Node> parentMap = new HashMap<>();
        Deque<Node> stack = new ArrayDeque<>();

        parentMap.put(root, null); // root's parent is null
        stack.push(root);

        // iterate until we find both the nodes p and q
        while (!parentMap.containsKey(p) || !parentMap.containsKey(q)) {
            Node node = stack.pop();

            if (node.left != null) {
                parentMap.put(node.left, node);
                stack.push(node.left);
            }

            if (node.right != null) {
                parentMap.put(node.right, node);
                stack.push(node.right);
            }
        }

        Set<Node> ancestors = new HashSet<>();

        // collect ancestors of p from parentMap
        while (p != null) {
            ancestors.add(p);
            p = parentMap.get(p);
        }

        // The first ancestor of q which appears in p's ancestor set is the LCA
        while (!ancestors.contains(q)) q = parentMap.get(q);

        return q;
    }

    // concept: morris traversal (Linked-list in-place)
    static void flatten(Node root) { // O(N), O(1)
        Node cur = root;
        while (cur != null) {
            if (cur.left != null) {
                // Find the inorder predecessor of cur
                Node predecessor = cur.left;
                // Find the rightmost node in the left subtree
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }
                // Connect the rightmost node's right pointer to the current node's right subtree
                predecessor.right = cur.right;
                // Move the left subtree to the right, and set left to null
                cur.right = cur.left;
                cur.left = null;
            }
            // Move to the next node in the "flattened" tree
            cur = cur.right;
        }
    }


    public static void main(String args[]) {

        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.left.right.left = new Node(8);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        root.right.right.left = new Node(9);
        root.right.right.right = new Node(10);

        ArrayList<Integer> preOrder = new ArrayList<>();
        // preOrderTrav(root, preOrder); // recursive
        preOrder = preOrderTrav(root); // iterative

        System.out.print("The preOrder Traversal is : ");
        for (int i = 0; i < preOrder.size(); i++) {
            System.out.print(preOrder.get(i) + " ");
        }

        ArrayList<Integer> inOrder = new ArrayList<>();
        // inOrderTrav(root, inOrder); // recursive solution
        inOrder = inOrderTrav(root); // iterative solution

        System.out.println();
        System.out.print("The inOrder Traversal is : ");
        for (int i = 0; i < inOrder.size(); i++) {
            System.out.print(inOrder.get(i) + " ");
        }

        ArrayList<Integer> postOrder = new ArrayList<>();
        // postOrderTrav(root, postOrder); // recursive solution
        postOrder = postOrderTrav(root);

        System.out.println();
        System.out.print("The postOrder Traversal is : ");
        for (int i = 0; i < postOrder.size(); i++) {
            System.out.print(postOrder.get(i) + " ");
        }

        System.out.println();
        List<Integer> result = levelOrder(root);
        System.out.println("Level order traversal of binary tree is: " + result);

        // Below code is for to allTraversal

        List<Integer> pre = new ArrayList<>();
        List<Integer> in = new ArrayList<>();
        List<Integer> post = new ArrayList<>();
        allTraversal(root, pre, in, post);

        System.out.print("The preorder Traversal is : ");
        for (int nodeVal : pre) {
            System.out.print(nodeVal + " ");
        }
        System.out.println();
        System.out.print("The inorder Traversal is : ");
        for (int nodeVal : in) {
            System.out.print(nodeVal + " ");
        }
        System.out.println();
        System.out.print("The postorder Traversal is : ");
        for (int nodeVal : post) {
            System.out.print(nodeVal + " ");
        }
        System.out.println();

        System.out.println(maxDepth(root)); // maxDepth (recursive solution)
        System.out.println(levelOrderMaxDepth(root)); // maxDepth (iterative solution)

        System.out.println(isBalanced(root));

        // --------------------------------------------------------------------------------------

        Node root1 = new Node(1);
        root1.left = new Node(2);
        root1.right = new Node(3);
        root1.right.left = new Node(4);
        root1.right.right = new Node(5);

        Node root2 = new Node(1);
        root2.left = new Node(2);
        root2.right = new Node(3);
        root2.right.left = new Node(4);

        if (isIdentical(root1, root2)) {
            System.out.println("Two Trees are identical");
        } else {
            System.out.println("Two trees are not identical");
        }

        ArrayList<ArrayList<Integer>> ans;
        ans = zigzagLevelOrder(root);
        // ans = zigzagLevelOrderV1(root);
        // ans = zigzagLevelOrderV2(root);
        // ans = zigzagLevelOrderWithDeque(root);
        System.out.println("Zig Zag Traversal of Binary Tree ");
        for (int i = 0; i < ans.size(); i++) {
            for (int j = 0; j < ans.get(i).size(); j++) {
                System.out.print(ans.get(i).get(j) + " ");
            }
            System.out.println();
        }

        ArrayList<Integer> boundaryTraversal;
        boundaryTraversal = printBoundary(root);

        System.out.println("The Boundary Traversal is : ");
        for (int i = 0; i < boundaryTraversal.size(); i++) {
            System.out.print(boundaryTraversal.get(i) + " ");
        }

        System.out.println();

        List<Integer> boundary = boundaryTraversal(root);
        System.out.println("Boundary traversal of the binary tree is:");
        for (int data : boundary) {
            System.out.print(data + " ");
        }

        List<List<Integer>> list = new ArrayList<>();
        list = findVertical(root);

        System.out.println("The Vertical Traversal is : ");
        for (List<Integer> it : list) {
            for (int nodeVal : it) {
                System.out.print(nodeVal + " ");
            }
            System.out.println();
        }


        System.out.println("TopView = " + topView(root));
        System.out.println("BottomView =  " + bottomView(root));


        Node symRoot = new Node(1);
        symRoot.left = new Node(2);
        symRoot.left.left = new Node(3);
        symRoot.left.right = new Node(4);
        symRoot.right = new Node(2);
        symRoot.right.left = new Node(4);
        symRoot.right.right = new Node(3);

        boolean res;
        res = isSymmetric(symRoot);

        if (res) {
            System.out.println("The tree is symmetrical");
        } else System.out.println("The tree is not symmetrical");

        Node pathRoot = new Node(1);
        pathRoot.left = new Node(2);
        pathRoot.left.left = new Node(4);
        pathRoot.left.right = new Node(5);
        pathRoot.left.right.left = new Node(6);
        pathRoot.left.right.right = new Node(7);
        pathRoot.right = new Node(3);

        ArrayList<Integer> arr = new ArrayList<>();
        boolean path = getPath(pathRoot, arr, 7);

        System.out.println("The path is " + path);
        for (int it : arr) System.out.print(it + " ");
        System.out.println();

        Node  flattenRoot = new Node(1);
        flattenRoot.left = new Node(2);
        flattenRoot.left . left = new Node(3);
        flattenRoot.left . right = new Node(4);
        flattenRoot.right = new Node(5);
        flattenRoot.right . right = new Node(6);
        flattenRoot.right . right . left = new Node(7);

        flatten(flattenRoot);
        while (flattenRoot.right != null) {
            System.out.print(flattenRoot.data + " -> ");
            flattenRoot = flattenRoot.right;
        }

        System.out.print(flattenRoot.data);

    }
}