package array;

import java.util.Arrays;

public class MaxSumSubArray {
    public static void main(String[] args) {
        int[] array = { 5, -4, -2, 6, -1 };
        // System.out.println(getMaxSubArray(array));
        // System.out.println(maxSumSubArr(new int[]{2, 9, 31, -4, 21, 7}, 3));
        // System.out.println(maxSubArrSameElem(new int[]{4, 2, 2, 3, 3, 3}));
        // System.out.println(maxSubArrSameElem(new int[]{4, 4, 4, 4, 4, 4}));
        // System.out.println(maxSubArrSameElem(new int[]{4, 4}));
        // System.out.println(maxSubArrSameElem(new int[]{4, 5}));
        // System.out.println(isPalindrome(new int[]{1, 1}));
        System.out.println(Arrays.toString(targetSum(new int[] { -1, 2, 3, 4, 8, 9 }, 7)));
        // System.out.println(Arrays.toString(targetsSum(new int[]{-1, 2, 3, 4, 8, 9},
        // 7)));
    }

    // Q: maximum sum from the subarray (kadane's algorithm)
    private static int getMaxSubArray(int[] arr) {
        int maxSum = 0;
        int currSum = 0;
        for (int i : arr) {
            currSum += arr[i];
            if (currSum > maxSum)
                maxSum = currSum;
            if (currSum < 0)
                currSum = 0;
        }
        return maxSum;
    }

    // Q: find the maximum from mam subarray (hint: sliding window fixed size)
    private static int maxSumSubArr(int[] a, int k) {
        int maxSum = Integer.MIN_VALUE;
        int wSum = 0;

        for (int i = 0; i < k; i++) {
            wSum += a[i];
            maxSum = Math.max(wSum, maxSum);
        }

        // sliding logic : remove the first windows from previous window & add the
        // current item to match the current total
        for (int i = k; i < a.length; i++) {
            wSum = wSum - (a[i - k] + a[i]); // here it ensures the total is correct from the correct window
            maxSum = Math.max(wSum, maxSum);

        }
        // this has O(k * n) time and O(1) space

        return maxSum;
    }

    // Q: find the length of maximum subarray where all elements are same (hint:
    // sliding window)
    private static int maxSubArrSameElem(int[] a) {
        int n = 0;
        int l = 0; // comparison

        // main concept: arr1 = [4 4 4 ] [ 4 5 5 ]
        // when at the first element then current value and l is same, at the next
        // iteration
        // if the value is same : then no need to do anything as l already has that
        // value so just increase length
        // but if value isn't the same: if the current subarray has no same element then
        // store the current value. increase length

        for (int i = 0; i < a.length; i++) {
            if (a[i] != a[l])
                l = a[i];
            n = Math.max(n, ((i - l) + 1)); //
        }

        return n;
    }

    // Q: check if the given array is palindrome

    private static boolean isPalindrome(int[] a) {
        int s = 0, e = a.length - 1;
        while (s <= e) {
            if (a[s] != a[e]) {
                return false;
            }
            e--;
            s++;
        }
        return true;
    }

    // Q: Given a sorted, find indices of two element whose sum is same as the
    // target value (assume there's only solution)
    private static int[] targetSum(int[] a, int t) {
        int start = 0, end = a.length - 1;

        while (start <= end) {
            if (a[start] + a[end] > t) {
                end = end - 1;
            } else if (a[start] + a[end] < t) {
                start = start + 1;
            } else {
                return new int[] { start, end };
            }
        }

        return new int[] { -1, -1 };

    }

    private static int[][] targetsSum(int[] a, int t) {
        int start = 0, end = a.length - 1;

        while (start <= end) {
            if (a[start] + a[end] > t) {
                end = end - 1;
            } else if (a[start] + a[end] < t) {
                start = start + 1;
            } else {
                int[] list = new int[] { start, end };
                // now here make ArrayList or List to store the result from the "list" then add
                // the others to that 2d array
                // to find all other occurrence of two indices that is same as target value then
                // use two pointer again
                start = start + 1;
                end = end - 1;
                while (start <= end) {
                    if (a[start] + a[end] < t) {
                        start += 1;
                    } else if (a[start] + a[end] > t) {
                        end -= 1;
                    } else {
                        int[] list_ = new int[] { start, end };
                        System.out.println("all " + Arrays.toString(list));
                        System.out.println("all " + Arrays.toString(list_));

                        return new int[][] {
                                list,
                                list_
                        };
                    }
                }
            }
        }

        return new int[][] {
                { -1, -1 },
                { -1, -1 }
        };

    }

}
