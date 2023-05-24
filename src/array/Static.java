package array;

import java.util.Arrays;

public class Static {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        int n = arr.length;
        removeMiddle(arr, 0, n);
        // printArr(arr, n);
    }


    public static void removeMiddle(int[] arr, int i, int length) {
        // Shift starting from i + 1 to end.
        for (int index = i + 1; index < length; index++) {
            arr[index - 1] = arr[index];
        }
        // No need to 'remove' arr[i], since we already shifted
        System.out.println(Arrays.toString(arr));
    }

    public static void printArr(int[] arr, int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
