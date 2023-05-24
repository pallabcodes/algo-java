package trees.bt;

import java.util.Scanner;

public class BinaryTree {
    static Scanner sc = null;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        createTree();
    }

    // left i = 2 * i, right = 2 * i + 1


    static Node createTree() {
        Node root = null;
        System.out.println("Enter data: ");
        int data = sc.nextInt();

        if(data == -1) return null; // base case

        root = new Node(data);

        System.out.println("Enter left for: " + data);
        root.left = createTree();

        System.out.println("Enter right for:  " + data);

        root.right = createTree();

        return root;
    }


}

class Node {
    Node left, right;
    int data;

    public Node(int data) {
        this.data = data;
    }
}
