package array;

import java.util.Arrays;

public class Concatenation {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 1};
        int n = arr.length;
        // System.out.println(Arrays.toString(concat(arr, n)));
        System.out.println(Arrays.toString(concatR(arr, n)));
        // System.out.println(Arrays.toString(dynamicArrayR(0, new int[]{})));

    }

    private static int[] concat(int[] arr, int n) {
        int[] ans = new int[n * 2];
        System.out.println(Arrays.toString(ans));

        int i = 0;

        for (int j = n; j < ans.length; j++) {
            ans[j] = arr[i];
            i++;

        }
        for (int k = 0; k < arr.length; k++) {
            ans[k] = arr[k];

        }
        return ans;
    }


    // https://leetcode.com/problems/concatenation-of-array/submissions/935866847/
    private static int[] concatR(int[] arr, int n) {
        return helperConcat(arr, n, arr.length * 2, arr);
    }

    private static int[] helperConcat(int[] arr, int n, int size, int[] ogAr) {
        if (n == ogAr.length * 2) {
            for (int i = 0; i < ogAr.length; i++) arr[i] = ogAr[i];
            // since the last value of arr and ogArr should be same thus
            arr[n - 1] = ogAr[(n / 2) - 1];
            return arr;
        }
        int[] result = helperConcat(new int[n + 1], n + 1, size, ogAr);
        int elem = n - 1;
        if (elem >= ogAr.length) {
            int select = (n - ogAr.length) - 1;
            result[elem] = result[select];
            return result;
        } else return result;


    }

    private static int[] dynamicArrayR(int n, int[] arr) {
        if (n == 5) {
            return arr;
        }
        return dynamicArrayR(n + 1, new int[n + 1]);


    }


}
