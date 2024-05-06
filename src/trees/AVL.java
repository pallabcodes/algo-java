package trees;

// AVL is special type of Binary Search Tree
public class AVL {

    private static class Node {
        int value, height;
        Node left, right;

        Node(int value) {
            this.value = value;
            this.height = 1; // A new node is initially added at leaf, height 1
        }
    }

    private Node root;

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Get the balance factor
    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private Node rightRotate(Node unbalancedNode) {
        Node newRoot = unbalancedNode.left;
        Node subtree = newRoot.right;

        newRoot.right = unbalancedNode;
        unbalancedNode.left = subtree;

        updateHeight(unbalancedNode);
        updateHeight(newRoot);

        return newRoot;
    }

    private Node leftRotate(Node unbalancedNode) {
        Node newRoot = unbalancedNode.right;
        Node subtree = newRoot.left;

        newRoot.left = unbalancedNode;
        unbalancedNode.right = subtree;

        updateHeight(unbalancedNode);
        updateHeight(newRoot);

        return newRoot;
    }

    private void updateHeight(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else { // Duplicate values are not allowed
            return node;
        }

        updateHeight(node);

        int balance = getBalance(node);

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

        return node;
    }

    private void display(Node node, String indent, boolean isRight) {
        if (node != null) {
            System.out.println(indent + (isRight ? "└── " : "├── ") + node.value + " (H: " + node.height + ")");
            // For the child nodes, increase the indent
            String childIndent = indent + (isRight ? "    " : "│   ");
            display(node.right, childIndent, true);
            display(node.left, childIndent, false);
        }
    }

    public void display() {
        display(root, "", true);
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
