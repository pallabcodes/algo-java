package trees;

import java.util.Scanner;

// Working
public class BinaryTree {

    private static class Node {
        int value;
        Node left, right;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public BinaryTree() {
    }

    public void populate(Scanner scanner) {
        System.out.println("Enter the Root Node: ");
        int value = scanner.nextInt();
        root = new Node(value);
        populate(scanner, root);
    }

    private void populate(Scanner scanner, Node node) {
        System.out.println("Do you want to enter left of " + node.value + "? Enter true for yes, false for no.");
        boolean left = scanner.nextBoolean();
        if (left) {
            System.out.println("Enter the value of the Left for the current Node: " + node.value);
            int value = scanner.nextInt();
            node.left = new Node(value);
            populate(scanner, node.left);
        }

        System.out.println("Do you want to enter right of " + node.value + "? Enter true for yes, false for no.");
        boolean right = scanner.nextBoolean();
        if (right) {
            System.out.println("Enter the value of the Right for the current Node: " + node.value);
            int value = scanner.nextInt();
            node.right = new Node(value);
            populate(scanner, node.right);
        }
    }

    public void display() {
        display(root, "");
    }

    private void display(Node node, String indent) {
        if (node == null) {
            return;
        }
        display(node.right, indent + "\t");
        System.out.println(indent + node.value);
        display(node.left, indent + "\t");
    }

    public void prettyDisplay() {
        prettyDisplay(root, 0);
    }

    public void prettyDisplay2() {
        int depth = depth(root);
        int width = (int) (Math.pow(2, depth) - 1);
        prettyDisplay(root, 1, width / 2, width / 4 + 1);
    }

    private void prettyDisplay(Node node, int depth, int indent, int offset) {
        if (node == null) {
            return;
        }
        printWhitespaces(indent);
        System.out.println(node.value);
        int newOffset = offset / 2;
        prettyDisplay(node.left, depth + 1, indent - offset, newOffset);
        prettyDisplay(node.right, depth + 1, indent + offset, newOffset);
    }

    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    private int depth(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(depth(node.left), depth(node.right));
    }

    private void prettyDisplay(Node node, int level) {
        if (node == null) {
            return;
        }

        prettyDisplay(node.right, level + 1);

        if (level != 0) {
            for (int i = 0; i < level - 1; i++) {
                System.out.print("|\t\t");
            }
            System.out.println("|------->" + node.value);
        } else {
            System.out.println(node.value);
        }

        prettyDisplay(node.left, level + 1);
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.value + " ");
        inOrder(node.right);
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value + " ");
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        Scanner scanner = new Scanner(System.in);
        binaryTree.populate(scanner);

        System.out.println("\nBinary Tree - Pretty Display:");
        binaryTree.prettyDisplay2(); // binaryTree.prettyDisplay();

        System.out.println("\nBinary Tree - PreOrder Traversal:");
        binaryTree.preOrder();
        System.out.println("\nBinary Tree - InOrder Traversal:");
        binaryTree.inOrder();
        System.out.println("\nBinary Tree - PostOrder Traversal:");
        binaryTree.postOrder();
    }
}
