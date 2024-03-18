package stack;

import java.util.*;

public class NextGreaterElem {
    // Brute Force (accepted answer)
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];

        // Here, for each element in nums1, below algorithm scans through nums2 to find the next greater element so O(n * m) and space O(n) since it stores into the result based on legnth of nums1

        // O(n)
        for (int i = 0; i < nums1.length; i++) {
            // O(m)
            for (int j = 0; j < nums2.length; j++) {
                if (nums1[i] == nums2[j]) {
                    result[i] = -1;

                    for (int k = j + 1; k < nums2.length; k++) {
                        if (nums2[k] > nums1[i]) {
                            result[i] = nums2[k];
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    public static int[] nextGreaterElement(int[] nums1, int n, int[] nums2, int m) {
        Stack<Integer> mStack = new Stack<>();
        HashMap<Integer, Integer> map = new HashMap<>();

        // n is length of nums1 and m is the length of nums2


        // O(m)
        for (int i = 0; i < m; i++) {


            while (mStack.size() > 0 && nums2[i] > mStack.peek()) {
                int popped = mStack.pop();
                // N.B: Whenever needs to find correspondences or matching elements between two arrays; use "hashmap"
                map.put(popped, nums2[i]);
            }

            mStack.push(nums2[i]);

        }

        System.out.println("here " + mStack);
         System.out.println(map);

        int[] result = new int[nums1.length];

        // O(n)
        for (int i = 0; i < n; i++) result[i] = map.getOrDefault(nums1[i], -1);

        return result;
    }

    public static void main(String[] args) {
        int[] arr1 = {4, 1, 2};
        int[] arr2 = {1, -2, 4, 2};

        //  O(n * m) and space O(n)
        System.out.println(Arrays.toString(nextGreaterElement(arr1, arr2)));

        // O(n + m) and space O(2m + n) => O(m + n)
        System.out.println(Arrays.toString(nextGreaterElement(arr1, arr1.length, arr2, arr2.length)));
    }
}
