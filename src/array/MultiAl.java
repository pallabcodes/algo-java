package array;

import java.util.ArrayList;
import java.util.Scanner;

public class MultiAl {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // int[][] arr = new int[2][4];
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();

        // adding an empty array with no specified size onto list
        for (int i = 0; i < 3; i++) list.add(new ArrayList<>());


        // if single element needed to be added then this loop would do
        // for (int i = 0; i < 3; i++) list.get(i).add(in.nextInt());

        // however if needed to add multiple elements then below
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) list.get(i).add(in.nextInt());

        System.out.println(list);
    }

}
