package trees;

// import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        // BinaryTree tree = new BinaryTree();
        // tree.populate(scanner);
        // tree.display();
        // tree.prettyDisplay();

        // BST tree = new BST();
        // int[] nums = {5, 2, 7, 1, 4, 6, 9, 8, 3, 10};

        // tree.populate(nums);
        // tree.display();

        // AVL tree = new AVL();
        // for (int i = 0; i < 1024; i++) {
        //     tree.insert(i);
        // }
        // System.out.println(tree.height()); // log 1000 (base 2) = 9 ( but here it gives 12, that's wrong)

        int[] arr = {3, 8, 6, 7, -2, -8, 4, 9};
        SegmentTree tree = new SegmentTree(arr);
        // tree.display();

        System.out.println(tree.query(1, 6));

    }
}