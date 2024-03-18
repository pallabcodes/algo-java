package trees;

// Working
public class BST {
    public class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Node root;

    public BST() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void display() {
        display(root, "");
    }

    private void display(Node node, String indent) {
        if (node == null) return;

        display(node.right, indent + "\t"); // Display right subtree
        System.out.println(indent + node.getValue());
        display(node.left, indent + "\t"); // Display left subtree
    }

    public void insert(int value) {
        root = insert(value, root);
    }

    private Node insert(int value, Node node) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(value, node.left);
        } else if (value > node.value) {
            node.right = insert(value, node.right);
        }
        // For BST, we don't need to adjust the height or balance the tree
        return node;
    }

    public void populate(int[] nums) {
        for (int num : nums) {
            this.insert(num);
        }
    }

    // This method seems intended to efficiently populate a balanced BST from a sorted array.
    // It's a nice idea but the name could be misleading since it's not populating but rather balancing.
    // I'll correct its logic slightly.
    public void populateSorted(int[] nums) {
        populateSorted(nums, 0, nums.length - 1); // Pass correct bounds
    }

    private void populateSorted(int[] nums, int start, int end) {
        if (start > end) {
            return;
        }

        int mid = start + (end - start) / 2;
        insert(nums[mid]);

        populateSorted(nums, start, mid - 1); // Correct bounds for left
        populateSorted(nums, mid + 1, end); // Correct bounds for right
    }

    // Additional methods for BST functionality

    // Find a value in the BST
    public boolean find(int value) {
        return find(root, value);
    }

    private boolean find(Node node, int value) {
        if (node == null) {
            return false;
        }
        if (value == node.value) {
            return true;
        } else if (value < node.value) {
            return find(node.left, value);
        } else {
            return find(node.right, value);
        }
    }

    // Main method for demonstrations
    public static void main(String[] args) {
        BST tree = new BST();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(2);
        tree.insert(4);
        tree.insert(6);
        tree.insert(8);

        tree.display();

        System.out.println("Is 6 in the tree? " + tree.find(6));
        System.out.println("Is 10 in the tree? " + tree.find(10));
    }
}
