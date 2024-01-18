package searchingSorting;

import java.util.*;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = { 5, 4, 3, 2, 1 };
        System.out.println(Arrays.toString(arr));
        sort(arr, 0, arr.length - 1);
        // System.out.println(Arrays.toString(arr));

        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

    }

    // taking a specific range of array: 2 ways to do it (concept)
    // 1. sub-array (extra space)
    // 2. pointers/indices/variables (start, end) :- in-place & constant space
    public static void sort(int[] nums, int low, int hi) {

        // initially low is first index i.e. 0 and hi is last index of nums i.e.
        // nums.length - 1

        if (low >= hi) {
            return;
        }

        int s = low;
        int e = hi;
        int m = s + (e - s) / 2;
        int pivot = nums[m];

        while (s <= e) {
            // also a reason why if its already sorted then it won't swap again
            while (nums[s] < pivot) {
                s++;
            }
            while (nums[e] > pivot) {
                e--;
            }

            // now found 2 elements (s, e) i.e. violating so "SWAP"

            // Outer while loop's condition'll only run when everything (in its body) has
            // been executed
            if (s <= e) {
                int temp = nums[s];
                nums[s] = nums[e];
                nums[e] = temp;
                s++;
                e--;
            }
        }

        // now, pivot is at the correct index so now do the "Recursive call" on the two
        // halves (LHS, RHS)

        sort(nums, low, e);
        sort(nums, s, hi);

    }
}
