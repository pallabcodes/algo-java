package trees;

import java.util.Scanner;

public class BinaryTree {
    public BinaryTree() {
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    // insert elements

    public void populate(Scanner scanner) {
        System.out.println("Enter the Root Node: ");
        int value = scanner.nextInt();
        root = new Node(value);
        populate(scanner, root);
    }

    private void populate(Scanner scanner, Node node) {
        System.out.println("Do you want to enter left of " + node.value);
        boolean left = scanner.nextBoolean();
        if (left) {
            System.out.println("Enter the value of the Left for the current Node: " + node.value);
            int value = scanner.nextInt();
            node.left = new Node(value);
            // N.B: the first left child (here 6) was called from here (line : 39)
            // so as 6 popped off; it will come to below (line : 39)
            populate(scanner, node.left);
        }


        System.out.println("Do you want enter right of " + node.value);
        boolean right = scanner.nextBoolean();
        if (right) {
            System.out.println("Enter the value of the right for the current Node: " + node.value);
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
        System.out.println(indent + node.value);
        display(node.left, indent + "\t");
        display(node.right, indent + "\t");
    }

    public void prettyDisplay() {
        prettyDisplay(root, 0);
    }

    private void prettyDisplay(Node node, int level) {
        if (node == null) {
            return;
        }

        prettyDisplay(node.right, level + 1);

        if (level != 0) {

            // when level != 0 that means I am not at root node (& some level down here = 1)

            // this is just for indentation
            for (int i = 0; i < level - 1; i++) {
                System.out.print("|\t\t");
            }
            System.out.println("|------->" + node.value);
        } else {
            // level root == 0 i.e. I am the root
            System.out.println(node.value);
        }

        prettyDisplay(node.left, level + 1);
    }

    public void preOrder() {
        preOrder(root);
    }

    // tc = O(n) since every no. printed once
    private void preOrder(Node node) {
        if (node == null) return;
        System.out.println(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.println(node.value + " ");
        inOrder(node.right);
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.value + " ");
    }
}
