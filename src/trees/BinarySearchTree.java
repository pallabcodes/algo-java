package trees;

import java.util.*;

/*
 * What is BST ?
 * 1) Left subtree will always be smaller than its root
 * 2) Right subtree will always be greater than its root
 * 3) no duplicates and any Left and right subtree also follow above 2 rules
 *
 * */
public class BinarySearchTree {
    static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
        }

        public int getValue() {
            return data;
        }
    }

    // modifies the tree in-place (& root must to be outside as done in main)
    private static void insert_(Node root, int val) {
        if (root == null) {
            // This case should not happen with the initial call if the root is correctly
            // managed.
            return;
        }

        // Find the correct position for the new value
        if (val < root.data) {
            if (root.left == null) {
                root.left = new Node(val);
            } else {
                insert_(root.left, val);
            }
        } else {
            if (root.right == null) {
                root.right = new Node(val);
            } else {
                insert_(root.right, val);
            }
        }
    }

    // in-place + flexibility
    private static Node insert(Node root, int val) { // T = O(H = Height of the tree) , S = O(N)
        if (root == null) {
            /*
             * root = new Node(val);
             * return root;
             *
             */

            // Directly return a new Node if root is null
            return new Node(val);
        }

        // val won't be duplicate so then val will be either smaller or greater than
        // root
        if (val < root.data) {
            // Recursively insert in left subtree and update root's left child
            root.left = insert(root.left, val);

        } else {
            // Recursively insert in right subtree and update root's right child
            root.right = insert(root.right, val);
        }

        // Returning `root` is primarily for maintaining the correct structure of the
        // tree through subsequent recursive calls and ensuring the integrity of the
        // tree from the root down to the newly inserted nodes.
        return root;
    }

    private static boolean search(Node root, int key) { // T = O(H), S = O(N)
        if (root == null) {
            return false;
        }

        if (root.data == key)
            return true;

        if (key < root.data) {
            return search(root.left, key);
        } else {
            return search(root.right, key);
        }
    }

    private static Node findInOrderSuccessor(Node root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private static Node delete(Node root, int val) {
        if (root == null) {
            return null; // Base case: if the tree is empty
        }
        // Recursively find the node to delete.
        if (val < root.data) { // If the val to delete is smaller than root's data, go left
            root.left = delete(root.left, val);
        } else if (val > root.data) { // If the val to delete is greater than root's data, go right
            root.right = delete(root.right, val);
        } else { // Found the node to delete
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            // Node with two children: Get the in-order successor (smallest leftmost node in
            // the right
            // subtree but if right subtree has single node i.e. itself then it'll be
            // considered smallest)
            Node temp = findInOrderSuccessor(root.right);

            // Copy the in-order successor's content to this node
            root.data = temp.data;

            // Delete the in-order successor
            root.right = delete(root.right, temp.data); // Correctly reassign the result to root.right
        }

        return root; // Return the modified tree
    }

    private static void printInRange(Node root, int k1, int k2) {
        if (root == null)
            return;

        if (root.data >= k1 && root.data <= k2) {
            printInRange(root.left, k1, k2);
            System.out.print(root.data + " ");
            printInRange(root.right, k1, k2);
        } else if (root.data < k1) {
            printInRange(root.right, k1, k2);
        } else {
            printInRange(root.right, k1, k2);
        }
    }

    private static void printPath(ArrayList<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i) + "->");
        }

        System.out.println("Null");
    }

    private static void printRoot2Leaf(Node root, ArrayList<Integer> path) {

        if (root == null) {
            return;
        }

        path.add(root.data);

        if (root.left == null && root.right == null) {
            printPath(path);
        }

        printRoot2Leaf(root.left, path);
        printRoot2Leaf(root.right, path);
        path.remove(path.size() - 1);

    }

    private static boolean isValidBST(Node root, Node min, Node max) {
        if (root == null) {
            return true;
        }

        if (min != null && root.data <= min.data) {
            return false;
        } else if (max != null && root.data >= max.data) {
            return false;
        }

        return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
    }

    private static void inOrder(Node root) {
        if (root == null)
            return;
        inOrder(root.left);
        System.out.print(root.data + " ");
        inOrder(root.right);
    }

    private static void preOrder(Node root) {
        if (root == null)
            return;
        System.out.print(root.data + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    private static Node createMirror(Node root) {
        if (root == null) {
            return null;
        }

        Node leftMirror = createMirror(root.left);
        Node rightMirror = createMirror(root.right);

        root.left = rightMirror;
        root.right = leftMirror;

        return root;
    }

    /*
     * Unbalance Tree: where height of left subtree or right subtree is greater than
     * other
     * Balanced Tree: where height of left subtree or right subtree is <= 1
     * skewed tree: it has either left or right subtree e.g. SinglyLinkedList
     *
     * N.B: This is assumed to be an inOrder traversal since inOrder gives sorted
     * result from BST
     *
     * Note: so, given a sorted array ( which could be asc, desc or increasing ->
     * decreasing,
     * deceasing -> increasing); BST could be formed
     *
     */
    private static Node createBalancedBSTFromSortedArr(int[] arr, int s, int e) {
        if (s > e) {
            return null; // no need to create a Node so just return null
        }
        int mid = (s + e) / 2; // e - ((s + e) / 2) -> this seems to have an issue so used (s + e) / 2

        Node root = new Node(arr[mid]); // create a new Node that will become root from arr[mid]
        root.left = createBalancedBSTFromSortedArr(arr, s, mid - 1);
        root.right = createBalancedBSTFromSortedArr(arr, mid + 1, e);

        return root;
    }

    private static void getInorder(Node root, ArrayList<Integer> inorder) {
        if (root == null)
            return;

        getInorder(root.left, inorder);
        inorder.add(root.data);
        getInorder(root.right, inorder);
    }

    private static Node createBST(ArrayList<Integer> inorder, int s, int e) {
        if (s > e) {
            return null;
        }

        int mid = (s + e) / 2;
        Node root = new Node(inorder.get(mid));
        root.left = createBST(inorder, s, mid - 1);
        root.right = createBST(inorder, mid + 1, e);

        return root;
    }

    // inOrder seq will always give a sorted result from BST
    private static Node convertToBalancedBST(Node root) {
        // inOrder seq
        ArrayList<Integer> inorder = new ArrayList<>();
        getInorder(root, inorder); // so now, `inorder` will contain seq

        // sorted arr -> balanced BST
        root = createBST(inorder, 0, inorder.size() - 1);

        return root;
    }

    static class Info {
        boolean isBST;
        int size;
        int min;
        int max;

        public Info(boolean isBST, int size, int min, int max) {
            this.isBST = isBST;
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }

    private static int maxSize = 0; // To keep track of the maximum size of BST found

    // when BST's root is null or it just has root node then it's a valid BST
    private static Info findLargestBST(Node root) { // O(N = no. of nodes in the tree), O(H = height of the tree)
        if (root == null) {
            return new Info(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }
        // Recursively check the left and right subtrees
        Info left = findLargestBST(root.left);
        Info right = findLargestBST(root.right);

        // Current node forms a BST if it's greater than max in left subtree and smaller
        // than min in right subtree
        if (left.isBST && right.isBST && root.data > left.max && root.data < right.min) { // valid BST
            int currentSize = left.size + right.size + 1;
            maxSize = Math.max(maxSize, currentSize);
            return new Info(true, currentSize, Math.min(root.data, left.min), Math.max(root.data, right.max));
        }

        // If not a BST, return false but calculate the subtree bounds for parent nodes
        return new Info(false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static Node mergeBSTs(Node root1, Node root2) { // O(N = no. of nodes from root1 + M = no. of nodes from root2) , S = O(H1 = height from the root1 + H2 = height from the root2)
        // STEP 1:
        ArrayList<Integer> arr1 = new ArrayList<>();
        getInorder(root1, arr1);

        // STEP 2:

        ArrayList<Integer> arr2 = new ArrayList<>();
        getInorder(root2, arr2);

        // STEP 3: merge

        int i = 0, j = 0;
        ArrayList<Integer> finalArr = new ArrayList<>();

        // after this loop, either arr1 or arr2 will be fully done or filled
        while (i < arr1.size() && j < arr2.size()) {
            if (arr1.get(i) <= arr2.get(j)) {
                finalArr.add(arr1.get(i));
                i++;
            } else {
                finalArr.add(arr2.get(j));
                j++;
            }
        }

        // in case of arr1 is not fully filled (meaning arr2 must have filled), so then just take remaining from arr1 and fill it into finalArr

        while (i < arr1.size()) {
            finalArr.add(arr1.get(i));
            i++;
        }

        // in case of arr2 is not fully filled (meaning arr1 must have filled), so then just take remaining from arr2 and fill it into finalArr

        while (j < arr2.size()) {
            finalArr.add(arr2.get(j));
            j++;
        }

        // STEP 4 : Using Sorted `finalArr` create Balanced BST
        return createBST(finalArr, 0, finalArr.size() - 1);
    }

    // Since, inorder follows LIFO so stack is a natural choice
    private static Node mergeBSTsWithTwoStacks(Node root1, Node root2) { // O(N = no. of nodes from root1 + M = no. of nodes from root2) , S = O(H1 = height from the root1 + H2 = height from the root2)
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();

        // This list will hold the merged in-order traversal of both trees
        ArrayList<Integer> mergedList = new ArrayList<Integer>();

        Node current1 = root1;
        Node current2 = root2;

        while (current1 != null || current2 != null || !stack1.isEmpty() || !stack2.isEmpty()) {
            // Push all the left children of current1 to the stack1
            while (current1 != null) {
                stack1.push(current1);
                current1 = current1.left;
            }
            // Push all the left children of current2 to the stack2
            while (current2 != null) {
                stack2.push(current2);
                current2 = current2.left;
            }

            // Compare the top elements of the stacks and choose the smaller one
            if (stack2.isEmpty() || (!stack1.isEmpty() && stack1.peek().data <= stack2.peek().data)) {
                current1 = stack1.pop();
                mergedList.add(current1.data); // Add to merged list
                current1 = current1.right; // Move to the right subtree
            } else {
                current2 = stack2.pop();
                mergedList.add(current2.data); // Add to merged list
                current2 = current2.right; // Move to the right subtree
            }
        }

        // Use the merged list to construct a balanced BST
        return createBST(mergedList, 0, mergedList.size() - 1);
    }

    private static List<Integer> inOrderTraversalUsingDeque(Node root) {
        List<Integer> result = new ArrayList<>();
        Deque<Node> deque = new ArrayDeque<>();
        Node current = root;
        while (current != null || !deque.isEmpty()) {
            while (current != null) {
                deque.push(current); // Simulating stack behavior using deque
                current = current.left;
            }
            current = deque.pop();
            result.add(current.data);
            current = current.right;
        }
        return result;
    }
    private static Node createBalancedBST(List<Integer> nums, int start, int end) {
        if (start > end) return null;
        int mid = start + (end - start) / 2;
        Node node = new Node(nums.get(mid));
        node.left = createBalancedBST(nums, start, mid - 1);
        node.right = createBalancedBST(nums, mid + 1, end);
        return node;
    }
    private static Node mergeBSTsWithDeque(Node root1, Node root2) {
        List<Integer> list1 = inOrderTraversalUsingDeque(root1);
        List<Integer> list2 = inOrderTraversalUsingDeque(root2);
        List<Integer> mergedList = new ArrayList<>();

        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) < list2.get(j)) {
                mergedList.add(list1.get(i++));
            } else {
                mergedList.add(list2.get(j++));
            }
        }
        while (i < list1.size()) mergedList.add(list1.get(i++));
        while (j < list2.size()) mergedList.add(list2.get(j++));

        return createBalancedBST(mergedList, 0, mergedList.size() - 1);
    }

    private static List<Integer> treeToSortedList(Node root) {
        ArrayList<Integer> resultList = new ArrayList<>();
        getInorder(root, resultList);
        return resultList;
    }

    private static List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2) {
        List<Integer> mergedList = new ArrayList<>();
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) < list2.get(j)) {
                mergedList.add(list1.get(i++));
            } else {
                mergedList.add(list2.get(j++));
            }
        }
        // Add remaining elements
        while (i < list1.size()) mergedList.add(list1.get(i++));
        while (j < list2.size()) mergedList.add(list2.get(j++));
        return mergedList;
    }
    private static Node mergeBSTsAlternative(Node root1, Node root2) {
        List<Integer> list1 = treeToSortedList(root1);
        List<Integer> list2 = treeToSortedList(root2);
        List<Integer> mergedList = mergeSortedLists(list1, list2);
        return createBalancedBST(mergedList, 0, mergedList.size() - 1);
    }



    public static void main(String[] args) {
        int[] arr = {8, 5, 3, 1, 4, 6, 10, 11, 14};

        // Initialize the root with the first element to ensure it's not null
        Node root_ = new Node(arr[0]);
        // Start from the second element as the first one is used for root
        // initialization
        for (int i = 1; i < arr.length; i++)
            insert(root_, arr[i]);
        inOrder(root_);
        System.out.println();

        Node root = null;
        for (int it : arr)
            root = insert(root, it);
        inOrder(root);

        System.out.println();
        if (search(root, 10)) {
            System.out.println("found");
        } else
            System.out.println("not found");

        root = delete(root, 1);
        System.out.println("deleting from the BST");
        inOrder(root);
        System.out.println();

        printInRange(root, 5, 12);
        System.out.println();

        printRoot2Leaf(root, new ArrayList<Integer>());

        if (isValidBST(root, null, null)) {
            System.out.println("valid");
        } else {
            System.out.println("invalid");
        }

        Node rootForMirrorBST = new Node(8);
        rootForMirrorBST.left = new Node(5);
        rootForMirrorBST.right = new Node(10);
        rootForMirrorBST.left.left = new Node(3);
        rootForMirrorBST.left.right = new Node(6);
        rootForMirrorBST.right.right = new Node(11);

        // 8 10 11 5 6 3

        rootForMirrorBST = createMirror(rootForMirrorBST); // re-assign root's value from the result of
        preOrder(rootForMirrorBST);

        System.out.println("Balanced tree with min height");

        int[] list = {3, 5, 6, 8, 10, 11, 12};
        Node balancedBST = createBalancedBSTFromSortedArr(list, 0, list.length - 1);
        preOrder(balancedBST);
        System.out.println();

        rootForMirrorBST = convertToBalancedBST(rootForMirrorBST);
        preOrder(rootForMirrorBST);
        System.out.println();

        Node largestRoot = new Node(60);
        largestRoot.left = new Node(45);
        largestRoot.right = new Node(70);
        largestRoot.right.left = new Node(65);
        largestRoot.right.right = new Node(80);

        Info info = findLargestBST(largestRoot);
        System.out.println("largest BST in size = " + maxSize);


        Node root1 = new Node(2);
        root1.left = new Node(1);
        root1.right = new Node(4);

        Node root2 = new Node(9);
        root2.left = new Node(3);
        root2.right = new Node(12);

        Node mergedRoot = mergeBSTsAlternative(root1, root2);
        preOrder(mergedRoot);
    }
}
