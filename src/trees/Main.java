package trees;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        int[] arr = {3, 8, 6, 7, -2, -8, 4, 9};
        SegmentTree segmentTree = new SegmentTree(arr);
        // segmentTree.display();

        System.out.println("NOW QUERY ON SEGMENT TREE");
        System.out.println(segmentTree.query(1, 6));


    }
}