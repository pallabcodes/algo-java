package searchingSorting;

import java.util.*;

public class RadixSort {
    public static void main(String[] args) {
        int[] arr = { 29, 83, 471, 36, 91, 8 };
        System.out.println("Og Array: " + Arrays.toString(arr));
        radixSort(arr);
        System.out.println("Sorted Array: " + Arrays.toString(arr));

    }

    public static void radixSort(int[] arr) {
        int max = Arrays.stream(arr).max().getAsInt();

        // do count sort for every digit place on an element

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, exp);
        }
    }

    private static void countSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        Arrays.fill(count, 0);

        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        System.out.println("Count array for " + exp + " : " + Arrays.toString(count));

        for (int i = 1; i < 10; i++) {
            count[i] = count[i] + count[i - 1];
        }

        System.out.println("Updated Count array " + Arrays.toString(count));

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        System.out.println("Output array " + Arrays.toString(output));

        System.arraycopy(output, 0, arr, 0, n);
    }

}
