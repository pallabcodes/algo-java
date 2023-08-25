package array.easy;

import java.util.Arrays;

public class Rotation {
    public static void main(String[] args) {
        int[] list = {1, 3, 5, 7, 9};
        // int[] arr2 = {1, 2, 3, 4, 5};
        int n = list.length;
        // rotate(arr, n, 2);
        // rotate(arr2, arr2.length, 4);
        rotateArray(list, n, 2);

        System.out.println(Arrays.toString(list));

    }

    private static void rotateArray(int[] arr, int n, int k) {
        if (k == 0) return;

        // rotate "arr" to the right by one position
        int temp = arr[n - 1];
        for (int i = n - 1; i > 0; i--) {
            arr[i] = arr[i - 1];
        }
        arr[0] = temp;

        rotateArray(arr, n, k - 1);

    }

    private static void rotate(int[] arr, int n, int k) {
        k = k % n;
        int i, j;

        // i = n - k = 3, j = 4; 3 < 4; i++, j--

        // Reverse last k numbers
        for (i = n - k, j = n - 1; i < j; i++, j--) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        // Reverse the first n - k terms
        for (i = 0, j = n - k - 1; i < j; i++, j--) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        // Reverse the entire array
        for (i = 0, j = n - 1; i < j; i++, j--) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        for (int t = 0; t < n; t++) {
            System.out.print(arr[t] + " ");

        }
    }
}
