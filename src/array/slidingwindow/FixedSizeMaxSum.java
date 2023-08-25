package array.slidingwindow;

import java.util.Arrays;

public class FixedSizeMaxSum {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        // System.out.println(Arrays.toString(arr));

        int k = 3;
        System.out.println(maxSum(arr, k));;

    }


    private static int maxSum(int[] arr, int k) {
        int size = arr.length;
        int i = 0, j = 0;
        int sum = 0;
        int max = 0;

        while (j < size) {
            int wSize = j - i + 1;

            // add the current value to sum and keep incrementing j++ till its windowSize
            sum += arr[j];

            if (wSize < k) {
                j++;
            } else if (wSize == k) {

                max = Math.max(max, sum);

                // now, remove the value of arr[i] then move i as a slide is done and remove
                sum = sum - arr[i];
                i++;
                j++;

            }

            // j++;
        }

        // System.out.println("MAX " + max);
        return  max;
    }
}