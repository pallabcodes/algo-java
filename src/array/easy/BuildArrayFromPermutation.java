package array.easy;

import java.util.Arrays;

// Q: https://leetcode.com/problems/build-array-from-permutation/ (level: easy)
public class BuildArrayFromPermutation {
    public static void main(String[] args) {
        int[] arr = {0, 2, 1, 5, 3, 4}; // {5, 0, 1, 2, 3, 4}
        // System.out.println(Arrays.toString(buildArrayBruteForce(arr)));
        System.out.println("normal " + Arrays.toString(buildArray(arr)));
         System.out.println("recursive " + Arrays.toString(buildArrayRecursive(arr)));

    }

    // time O(n) and space O(n)
    private static int[] buildArrayBruteForce(int[] nums) {
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            ans[i] = nums[nums[i]];

        }
        return ans;
    }

    // time O(n) and space O(n)

    /*
     * pattern:
     * -------
     * 1. first, check to see if the array values could be converted by doing
     * 1a. IO = [ 0, 2, 1, 5, 3, 4 ] => [ 0-5, 6-11, 12-17, 24-29, 30-35, 18-23 ]
     * 1b: re-covert let's assume array is now = [1, 10, 18, 35, 45, 29]; n = 6 & so [1/6 = 0, 10/6 = 1, 15/6 = 2, 29/6 = 4, 34/6 = 5, 20/6 = 3 ]
     *
     * 2. so , looks like the conversion will work and this will give 0(1) space complexity
     * */
    private static int[] buildArray(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            nums[i] = n * (nums[nums[i]] % n) + nums[i];
            /*
            6 * 0 + 0 = 0
            6 * 1 + 2 = 8
            6 * 2 + 1 = 13
            6 * 4 + 5 = 29
            6 * 5 + 3 = 33
            6 * 3 + 4 = 22



            */

        }

        // nums = [0 , 8, 13, 29, 33, 22]
        for (int i = 0; i < n; i++) {
            nums[i] = nums[i] / n;

        }
        return nums;
    }

    // recursive solution:

    // space : O(n) and time : O(n)
    private static int[] buildArrayRecursive(int[] nums) {
        aPermutation(nums, 0); // here using helper fn i.e. aPermutation to modify input array
        return nums;
    }

    private static void aPermutation(int[] nums, int start) {
        if (start < nums.length) {
            int temp = nums[start];
            int result = nums[temp];
            aPermutation(nums, start + 1);
            nums[start] = result;

        }
    }


}
