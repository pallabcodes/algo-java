package array;

import java.util.Arrays;

public class Reverse {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(reverse(arr, arr.length)));
    }

    static int[] reverse(int[] arr, int n) {
        System.out.println(arr[4]);
        int start = 0, end = n - 1;
        while(start < end) {
            int start_ = arr[start];
            arr[start] = arr[end];
            arr[end] = arr[start_];
            start++;
            end--;
        }
        return arr;
    }
}
