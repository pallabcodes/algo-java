package array;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// # when working with array , look at these characteristics:

// is the array fully sorted / unsorted?
// has rotation (left / clockwise, right/anti-clockwise)
// increasing / decreasing part of the array
// 2d array / matrix

public class ArrayListExample {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // int[] str = new int[]{"hello", "java"}; // this is how to define an array

        // this is how to do for ArrayList
        ArrayList<Integer> list = new ArrayList<>(5);
        // System.out.println(list.size());

        // list.add(0);
        // list.add(4);
        // list.add(3);
        // list.add(4);


        // ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        // lists.add(new ArrayList<Integer>(list));


//        System.out.println(list.contains(1));
//        System.out.println(list);
//
//        list.set(0, 10); // index, value
//
//        System.out.println(list);
//
//        list.remove(2); // index 2
//
//        System.out.println(list);

        // for (int i = 0; i < 5; i++) list.add(in.nextInt());
        // for (int i = 0; i < 5; i++) System.out.println(list.get(i));

        System.out.println(list);

        List<List<Integer>> outer = new ArrayList<List<Integer>>();
        List<Integer> inner1 = new ArrayList<Integer>();
        List<Integer> inner2 = new ArrayList<Integer>();

        inner1.add(100);
        inner1.add(200);

        inner2.add(100);
        inner2.add(200);

        outer.add(inner1);
        outer.add(inner2);

        outer.get(0).add(300);

        System.out.println(outer); //Output: [[100, 200, 300], [100, 200]]


    }
}
