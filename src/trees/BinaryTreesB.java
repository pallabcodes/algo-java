package trees;

import java.util.*;

public class BinaryTreesB {
    static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    static class BinaryTree {
        static int idx = -1;

        private static Node buildTree(int[] nodes) { // T = O(n)
            idx++;
            if (nodes[idx] == -1) {
                return null;
            }

            Node newNode = new Node(nodes[idx]); // creating a new node
            newNode.left = buildTree(nodes); // here assuming this recursive call will create the left subtree by
            // itself, I just have to assign it to `left`
            newNode.right = buildTree(nodes); // same as the above for the right subtree

            return newNode;
        }

        // DFS traversal: PreOrder (Root, Left Subtree, Right Subtree)
        private static void preOrder(Node root) {
            if (root == null) {
                return;
            }

            System.out.print(root.data + " ");
            preOrder(root.left);
            preOrder(root.right);
        }

        // DFS traversal: InOrder (Left Subtree, Root, Right Subtree)

        private static void inOrder(Node root) {
            if (root == null) {
                return;
            }

            inOrder(root.left);
            System.out.print(root.data + " ");
            inOrder(root.right);
        }

        // DFS traversal: PostOrder (Left Subtree, Right Subtree, Root)
        private static void postOrder(Node root) {
            if (root == null) {
                return;
            }

            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.data + " ");
        }

        // BFS: Level Order Traversal (Iterative) via Queue +
        // ArrayList<ArrayList<Integer>>
        private static void levelOrder(Node root) {
            if (root == null) {
                return;
            }

            Queue<Node> q = new LinkedList<>();
            q.add(root); // root added to this queue
            q.add(null); // and null value thereafter

            while (!q.isEmpty()) {
                Node currNode = q.remove();
                if (currNode == null) {
                    System.out.println();
                    if (q.isEmpty()) {
                        break;
                    } else {
                        q.add(null);
                    }
                } else {
                    System.out.print(currNode.data + " ");
                    if (currNode.left != null) {
                        q.add(currNode.left);
                    }
                    if (currNode.right != null) {
                        q.add(currNode.right);
                    }
                }
            }

        }

        private static ArrayList<Integer> levelOrderV2(Node root) {
            ArrayList<Integer> result = new ArrayList<>();

            if (root == null) {
                return result;
            }

            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);


            while (!queue.isEmpty()) {
                Node current = queue.poll();

                result.add(current.data);

                if (current.left != null) {
                    queue.offer(current.left);
                }

                if (current.right != null) {
                    queue.offer(current.right);
                }
            }

            return result;
        }

        // get the height of a binary tree
        private static int height(Node root) {
            if (root == null) {
                return 0;
            }

            int lh = height(root.left);
            int rh = height(root.right);

            return Math.max(lh, rh) + 1;
        }

        private static int count(Node root) {
            if (root == null) {
                return 0;
            }

            // int leftCount = count(root.left);
            // int rightCount = count(root.right);

            // return leftCount + rightCount + 1;

            return 1 + count(root.left) + count(root.right);
        }

        // sum of all nodes
        private static int sum(Node root) {
            if (root == null) {
                return 0;
            }

            return sum(root.left) + sum(root.right) + root.data;
        }

        // diameter of a tree i.e. no. of nodes in the longest path between 2 leafs i)
        // is diameter goes through root? ii) does it exist within a subtree?

        private static int diameter(Node root) { // O(N^2)
            if (root == null) {
                return 0;
            }

            int leftDiam = diameter(root.left);
            int leftHt = height(root.left);
            int rightDiam = diameter(root.right);
            int rightHt = height(root.right);

            int selfDiam = leftHt + rightHt + 1;

            return Math.max(selfDiam, Math.max(leftDiam, rightDiam));
        }

        static class Info {
            int diam;
            int ht;

            public Info(int diam, int ht) {
                this.diam = diam;
                this.ht = ht;
            }
        }

        private static Info diameterV2(Node root) { // O(N)
            if (root == null) {
                return new Info(0, 0);
            }

            Info leftInfo = diameterV2(root.left);
            Info rightInfo = diameterV2(root.right);

            int diam = Math.max(Math.max(leftInfo.diam, rightInfo.diam), leftInfo.ht + rightInfo.ht + 1);
            int ht = Math.max(leftInfo.ht, rightInfo.ht) + 1;

            return new Info(diam, ht);
        }

        private static boolean isIdentical(Node node, Node subRoot) {
            if (node == null && subRoot == null) {
                return true;
            } else if (node == null || subRoot == null || node.data != subRoot.data) {
                return false;
            }

            return isIdentical(node.left, subRoot.left) && isIdentical(node.right, subRoot.right);
        }

        private static boolean isSubtree(Node root, Node subRoot) {

            if (root == null) {
                return false;
            }

            if (root.data == subRoot.data) {
                if (isIdentical(root, subRoot)) {
                    return true;
                }
            }

            return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
        }

        static class InfoForTopView {
            Node node;
            int hd;

            public InfoForTopView(Node node, int hd) {
                this.node = node;
                this.hd = hd;
            }
        }

        private static void topView(Node root) {
            // level order traversal used here (iteratively)
            Queue<InfoForTopView> q = new LinkedList<>();
            HashMap<Integer, Node> map = new HashMap<>();

            int min = 0, max = 0;
            q.add(new InfoForTopView(root, 0));
            q.add(null);

            while (!q.isEmpty()) {
                InfoForTopView curr = q.remove();
                if (curr == null) {
                    if (q.isEmpty()) {
                        break;
                    } else {
                        q.add(null);
                    }
                } else {
                    if (!map.containsKey(curr.hd)) { // first time, hd occurs
                        map.put(curr.hd, curr.node);
                    }

                    if (curr.node.left != null) {
                        q.add(new InfoForTopView(curr.node.left, curr.hd - 1));
                        min = Math.min(min, curr.hd - 1);
                    }

                    if (curr.node.right != null) {
                        q.add(new InfoForTopView(curr.node.right, curr.hd + 1));
                        max = Math.max(max, curr.hd + 1);
                    }
                }

            } // loop ends

            for (int i = min; i <= max; i++) {
                System.out.print(map.get(i).data + " ");
            }

            System.out.println();
        }

        private static void KLevel(Node root, int level, int K) {
            if (root == null) {
                return;
            }

            if (level == K) {
                System.out.print(root.data + " ");
                return;
            }

            KLevel(root.left, level + 1, K);
            KLevel(root.right, level + 1, K);
        }

        private static boolean getPath(Node root, int n, ArrayList<Node> path) {

            if (root == null) {
                return false;
            }

            path.add(root);

            if (root.data == n)
                return true;

            boolean foundLeft = getPath(root.left, n, path);
            boolean foundRight = getPath(root.right, n, path);

            if (foundLeft || foundRight)
                return true;

            path.remove(path.size() - 1);

            return false;
        }

        private static Node lca(Node root, int n1, int n2) { // T, S = O(N)
            ArrayList<Node> path1 = new ArrayList<>();
            ArrayList<Node> path2 = new ArrayList<>();

            getPath(root, n1, path1);
            getPath(root, n2, path2);

            // last common ancestor
            int i = 0;
            for (; i < path1.size() && i < path2.size(); i++) {
                if (!path1.get(i).equals(path2.get(i))) {
                    break;
                }
            }

            // last equal node -> i-1th
            Node lca = path1.get(i - 1);

            return lca;

        }

        private static Node lca2(Node root, int n1, int n2) { // O(N)

            if (root == null || root.data == n1 || root.data == n2)
                return root;

            Node leftLca = lca2(root.left, n1, n2);
            Node rightLca = lca2(root.right, n1, n2);

            // then check within leftLca
            if (rightLca == null) {
                return leftLca;
            }

            // then check within rightLca
            if (leftLca == null) {
                return rightLca;
            }

            return root;

        }

        private static int lcaDist(Node root, int n) {
            if (root == null) {
                return -1;
            }

            if (root.data == n)
                return 0;

            int leftDist = lcaDist(root.left, n);
            int rightDist = lcaDist(root.right, n);

            if (leftDist == -1 && rightDist == -1) {
                return -1;

            } else if (leftDist == -1) {
                return rightDist + 1;

            } else {
                return leftDist + 1;
            }

        }

        private static int minDistance(Node root, int n1, int n2) {
            Node lca = lca2(root, n1, n2);
            int dis1 = lcaDist(lca, n1);
            int dist2 = lcaDist(lca, n2);
            return dis1 + dist2;
        }

        // Declare a class variable to store the Kth ancestor's value. Initialize it
        // with a sentinel value indicating not found.
        static int kthAncestorValue = -1;

        private static int KAncestorHelper(Node root, int n, int k) {
            if (root == null)
                return -1;

            if (root.data == n)
                return 0;

            int leftDist = KAncestorHelper(root.left, n, k);
            int rightDist = KAncestorHelper(root.right, n, k);

            // If n is not found in the subtree rooted at this node, return -1.
            if (leftDist == -1 && rightDist == -1) {
                return -1;
            }

            int max = Math.max(leftDist, rightDist);

            // When the distance equals k-1, it means this node is the Kth ancestor.
            // Update the global variable to store this node's value.
            if (max + 1 == k) {
                kthAncestorValue = root.data;
            }

            // Return the distance to the caller.
            return max + 1;
        }

        private static void KAncestor(Node root, int n, int k) {
            kthAncestorValue = -1; // Reset the value before each call.
            KAncestorHelper(root, n, k);
            // Check if the Kth ancestor was found and print it. If not, print an
            // appropriate message.
            if (kthAncestorValue != -1) {
                System.out.println("Kth Ancestor: " + kthAncestorValue);
            } else {
                System.out.println("Kth ancestor not found or does not exist.");
            }
        }

        private static int sumOfAllNodes(Node root) {
            if (root == null)
                return 0;
            return root.data + sumOfAllNodes(root.left) + sumOfAllNodes(root.right);
        }

        private static int transform(Node root) {
            if (root != null) {
                int l = transform(root.left);
                int r = transform(root.right);

                int temp = root.data;

                root.data = l + r;

                return temp + l + r;

            } else {
                return 0;
            }
        }

        private static void invertOrMirrorBT(Node root) {

            if (root == null) return;

            Stack<Node> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {

                Node current = stack.pop();

                // Swap the left and right children
                Node temp = current.left;
                current.left = current.right;
                current.right = temp;

                // Push non-null children to the stack
                if (current.left != null) {
                    stack.push(current.left);
                }

                if (current.right != null) {
                    stack.push(current.right);
                }
            }

            System.out.println("inverted = " + stack.toString());
        }

        // Method to collect values of a binary tree in pre-order traversal iteratively
        private static ArrayList<Integer> collectPreOrderIteratively(Node root) {
            ArrayList<Integer> result = new ArrayList<>();

            if (root == null) {
                return result;
            }

            Stack<Node> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                Node node = stack.pop();
                result.add(node.data);

                // Push right child first so that left is processed first
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
            return result;
        }

        public static void main(String[] args) {
            int[] nodes = {1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};
            BinaryTree tree = new BinaryTree();
            Node root = tree.buildTree(nodes);
            // System.out.println(root.data); // printing the root node's value

            // tree.preOrder(root);
            // tree.inOrder(root);
            // tree.postOrder(root);
            // tree.levelOrder(root);

            System.out.println(tree.height(root));
            System.out.println("total count of all nodes = " + tree.count(root));
            System.out.println(tree.sum(root));
            System.out.println("diameter v1 " + diameter(root));
            System.out.println("diameter v2 " + diameterV2(root).diam + " and height is = " + diameterV2(root).ht);

            // ------------------------------------------------------------------------------------

            Node node = new Node(1);
            node.left = new Node(2);
            node.right = new Node(3);
            node.left.left = new Node(4);
            node.left.right = new Node(5);
            node.right.left = new Node(6);
            node.right.right = new Node(7);

            /*
             * 1
             * | \
             * 2 3
             * | \ | \
             * 4 5 6 7
             */

            /*
             * 2
             * | \
             * 4 5
             *
             */

            Node subRoot = new Node(2);
            subRoot.left = new Node(4);
            subRoot.right = new Node(5);

            System.out.println("isSubtree = " + isSubtree(node, subRoot));

            topView(root);

            int k = 3, level = 1; // since , root is level 1 so initial level is 1
            KLevel(node, level, k);

            int n1 = 4, n2 = 7; // n1 and n2 are nodes available within Node 1 which is why it gives 1
            System.out.println(lca(node, n1, n2).data);
            System.out.println(lca2(node, n1, n2).data);
            System.out.println(minDistance(node, n1, n2));

            KAncestor(root, 5, 1);

            Node sumNode = new Node(10);
            sumNode.left = new Node(-2);
            sumNode.right = new Node(6);
            sumNode.left.left = new Node(8);
            sumNode.left.right = new Node(-4);
            sumNode.right.left = new Node(7);
            sumNode.right.right = new Node(5);

            transform(sumNode);
            inOrder(sumNode); // https://www.geeksforgeeks.org/convert-a-given-tree-to-sum-tree/

            Node invertedRoot = new Node(12);
            invertedRoot.left = new Node(14);
            invertedRoot.right = new Node(16);
            invertedRoot.left.left = new Node(15);
            invertedRoot.left.right = new Node(17);
            invertedRoot.right.right = new Node(20);

            // invert output should be as this -> 12 16 20 14 17 15

            // Invert or mirror the binary tree iteratively
            invertOrMirrorBT(invertedRoot); // also by calling this function invertedRoot has been modified

            // Collect the values in pre-order traversal after mirroring, iteratively by passing modified invertedRoot
            ArrayList<Integer> result = collectPreOrderIteratively(invertedRoot);
            System.out.println(result);

        }
    }
}