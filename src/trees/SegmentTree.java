package trees;

/**
 * 1. Find sum, min, max, average etc from a given range
 * 2. Update value by the given range
 * 
 * So, it provides more features than prefix sum (in terms of sum) + splice (in terms of update in js)
 * Thus, it is best to use on range
 * 
 *  */ 

public class SegmentTree {
    public static void main(String[] args) {
        int[] arr = { 3, 8, 6, 7, -2, -8, 4, 9 };
        SegmentTree tree = new SegmentTree(arr);
        tree.display(); // Display the constructed tree

        // Example queries and updates
        System.out.println("Sum of range [1, 5]: " + tree.query(1, 5)); // O(log n)
        tree.update(4, 10); // Update index 4 to value 10; O(log n)
        System.out.println("After update, sum of range [1, 5]: " + tree.query(1, 5)); // O(log n)
    }

    public static class Node {
        int data; // Sum of the segment
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

    // Constructor to build the segment tree
    public SegmentTree(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        this.root = constructTree(arr, 0, arr.length - 1); // O(n)
    }

    // Helper method to construct the segment tree -> Insert O(n)
    private Node constructTree(int[] arr, int start, int end) {
        if (start == end) {
            Node leaf = new Node(start, end);
            leaf.data = arr[start];
            return leaf;
        }
        Node node = new Node(start, end);
        int mid = (start + end) / 2;
        node.left = constructTree(arr, start, mid); // O(n)
        node.right = constructTree(arr, mid + 1, end); // O(n)
        node.data = node.left.data + node.right.data; // O(1)
        return node;
    }

    // Display the segment tree
    public void display() {
        display(this.root);
    }

    private void display(Node node) {
        if (node == null) {
            return;
        }

        String str = "";
        if (node.left != null) {
            str += "Interval=[" + node.left.startInterval + "-" + node.left.endInterval + "] and data: "
                    + node.left.data + " => ";
        } else {
            str += "No left child => ";
        }

        str += "Interval=[" + node.startInterval + "-" + node.endInterval + "] and data: " + node.data;

        if (node.right != null) {
            str += " <= Interval=[" + node.right.startInterval + "-" + node.right.endInterval + "] and data: "
                    + node.right.data;
        } else {
            str += " <= No right child";
        }

        System.out.println(str);
        display(node.left); // O(n)
        display(node.right); // O(n)
    }

    // Query the sum in the range [qsi, qei] -> Access and Search O( log n )
    public int query(int qsi, int qei) {
        if (qsi > qei || qsi < 0 || qei >= root.endInterval + 1) {
            throw new IllegalArgumentException("Invalid query range");
        }
        return query(root, qsi, qei); // O(log n)
    }

    // Helper method to perform the query operation
    private int query(Node node, int qsi, int qei) {
        if (node.startInterval > qei || node.endInterval < qsi) {
            return 0; // Identity for sum query
        }
        if (node.startInterval >= qsi && node.endInterval <= qei) {
            return node.data;
        }
        return query(node.left, qsi, qei) + query(node.right, qsi, qei); // O(log n)
    }

    // Update the value at a specific index -> Delete O (log n)
    public void update(int index, int value) {
        if (index < 0 || index >= root.endInterval + 1) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        update(root, index, value); // O(log n)
    }

    // Helper method to perform the update operation
    private void update(Node node, int index, int value) {
        if (node.startInterval == node.endInterval) {
            node.data = value;
            return;
        }
        int mid = (node.startInterval + node.endInterval) / 2;
        if (index <= mid) {
            update(node.left, index, value); // O(log n)
        } else {
            update(node.right, index, value); // O(log n)
        }
        node.data = node.left.data + node.right.data; // O(1)
    }
}
