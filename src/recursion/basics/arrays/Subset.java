package recursion.basics.arrays;

import java.util.*;

public class Subset {
    private static void generateSubsets(int[] arr, List<Integer> p, int i) {
        if (i == arr.length) {
            System.out.println(p);
            return;
        }

        // create a new list from p and then add the current element within it
        List<Integer> pWithCurrent = new ArrayList<>(p);
        pWithCurrent.add(arr[i]);


        // Push
        generateSubsets(arr, pWithCurrent, i + 1);

        // Don't push (just pass the p and then move forward
        generateSubsets(arr, p, i + 1);

    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        generateSubsets(arr, new ArrayList<Integer>(), 0);

    }
}
