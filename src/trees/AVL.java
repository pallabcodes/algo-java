package trees;

// Working
public class AVL {

    public class Node {
        int value;
        Node left, right;
        int height; // Height of the node.

        public Node(int value) {
            this.value = value;
            this.height = 0; // Initially, the height is 0 when the node is a leaf.
        }
    }

    private Node root;

    public AVL() {
    }

    // A utility method to get the height of the node
    private int height(Node node) {
        if (node == null) return -1; // For null nodes, height is considered as -1
        return node.height;
    }

    // The method to insert a value in the AVL tree and balance the tree
    public void insert(int value) {
        root = insert(root, value);
    }

    // Recursive method to insert a new value
    private Node insert(Node node, int value) {
        if (node == null) return new Node(value);

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else {
            // Duplicate values are not allowed
            return node;
        }

        // Update the height of the ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor to check whether this node became unbalanced
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && value < node.left.value) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && value > node.right.value) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Return the (unchanged) node pointer
        return node;
    }

    // A utility method to right rotate the subtree rooted with y
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility method to left rotate the subtree rooted with x
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get the balance factor of a node
    private int getBalance(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    // Method to display the tree
    public void display() {
        display(root, "");
    }

    private void display(Node node, String indent) {
        if (node != null) {
            display(node.right, indent + "   ");
            System.out.println(indent + node.value + " (" + node.height + ")");
            display(node.left, indent + "   ");
        }
    }

    public static void main(String[] args) {
        AVL tree = new AVL();

        // Example usage
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

        tree.display();

        // Print the height of the AVL tree
        System.out.println("The height of the AVL tree is: " + tree.height(tree.root));

    }
}
