package trees;

// AVL is special type of Binary Search Tree (this is also a balance tree or b-tree that used by databases)
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
            System.out.println("Duplicate value " + value + " not inserted.");
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

    public boolean search(int value) {
        return search(root, value) != null;
    }

    private Node search(Node node, int value) {
        if (node == null || node.value == value) {
            return node;
        }

        if (value < node.value) {
            return search(node.left, value);
        }

        return search(node.right, value);
    }

    // Find the node with the minimum value (leftmost node)
    public int findMin() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMin(root).value;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Find the node with the maximum value (rightmost node)
    public int findMax() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMax(root).value;
    }

    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // Delete a node with a specific value
    public void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node node, int value) {
        if (node == null) {
            return node;
        }

        if (value < node.value) {
            node.left = delete(node.left, value);
        } else if (value > node.value) {
            node.right = delete(node.right, value);
        } else { // Node to be deleted found
            // Node with only one child or no child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Node with two children: Get the inorder successor (smallest in the right
            // subtree)
            Node temp = findMin(node.right);

            // Copy the inorder successor's value to this node
            node.value = temp.value;

            // Delete the inorder successor
            node.right = delete(node.right, temp.value);
        }

        updateHeight(node);

        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Right Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Left Case
        if (balance < -1 && getBalance(node.right) > 0) {
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
            display(node.left, childIndent, false);
            display(node.right, childIndent, true);
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


        System.out.println("Searching for 25: " + tree.search(25));
        System.out.println("Searching for 15: " + tree.search(15));

        // System.out.println("Minimum value: " + tree.findMin());
        // System.out.println("Maximum value: " + tree.findMax());

        // tree.delete(50);
        // tree.display();

        // System.out.println("The height of the AVL tree is: " + tree.height(tree.root));

    }
}
