package trees;

// This is indeed related to intervals but it is different that "Merger Interval Leetcode pattern"
public class SegmentTree {
    public static void main(String[] args) {
        int[] arr = {3, 8, 6, 7, -2, -8, 4, 9};
        SegmentTree tree = new SegmentTree(arr);
        tree.display(); // Display the constructed tree
    }

    public static class Node {
        int data;
        int startInterval;
        int endInterval;
        Node left;
        Node right;

        public Node(int startInterval, int endInterval) {
            this.startInterval = startInterval;
            this.endInterval = endInterval;
        }
    }

    Node root;

    public SegmentTree(int[] arr) {
        this.root = constructTree(arr, 0, arr.length - 1);
    }

    private Node constructTree(int[] arr, int start, int end) {
        if (start == end) {
            Node leaf = new Node(start, end);
            leaf.data = arr[start];
            return leaf;
        }
        Node node = new Node(start, end);
        int mid = (start + end) / 2;
        node.left = this.constructTree(arr, start, mid);
        node.right = this.constructTree(arr, mid + 1, end);
        node.data = node.left.data + node.right.data;
        return node;
    }

    public void display() {
        display(this.root);
    }

    private void display(Node node) {
        if (node == null) {
            return;
        }

        String str = "";
        if (node.left != null) {
            str += "Interval=[" + node.left.startInterval + "-" + node.left.endInterval + "] and data: " + node.left.data + " => ";
        } else {
            str += "No left child => ";
        }

        str += "Interval=[" + node.startInterval + "-" + node.endInterval + "] and data: " + node.data;

        if (node.right != null) {
            str += " <= Interval=[" + node.right.startInterval + "-" + node.right.endInterval + "] and data: " + node.right.data;
        } else {
            str += " <= No right child";
        }

        System.out.println(str);
        display(node.left);
        display(node.right);
    }

    public int query(int qsi, int qei) {
        return query(root, qsi, qei);
    }

    private int query(Node node, int qsi, int qei) {
        if (node.startInterval >= qsi && node.endInterval <= qei) {
            return node.data;
        } else if (node.startInterval > qei || node.endInterval < qsi) {
            return 0;
        } else {
            return this.query(node.left, qsi, qei) + this.query(node.right, qsi, qei);
        }
    }

    public void update(int index, int value) {
        update(root, index, value);
    }

    private int update(Node node, int index, int value) {
        if (node.startInterval == node.endInterval) {
            if (node.startInterval == index) {
                node.data = value;
            }
            return node.data;
        }
        if (index <= node.left.endInterval) {
            update(node.left, index, value);
        } else {
            update(node.right, index, value);
        }
        node.data = node.left.data + node.right.data;
        return node.data;
    }
}
