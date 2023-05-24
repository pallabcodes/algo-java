package array;

import java.util.Arrays;

public class SearchingIn2dArray {
    public static void main(String[] args) {
        int[][] arr = {
                {23, 4, 1},
                {18, 12, 3, 9},
                {78, 99, 34, 56},
                {18, 12}
        };
        int target = 56;

        //System.out.println(Integer.MIN_VALUE); // -2147483647
        //System.out.println(Integer.MAX_VALUE); // 2147483647

        int[] ans = search(arr, target);
        System.out.println(Arrays.toString(ans));
        System.out.println(max(arr));

    }

    private static int[] search(int[][] arr, int target) {
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[row].length; col++) {
                if (arr[row][col] == target) return new int[]{row, col};
            }
        }
        return new int[]{-1, -1};
    }

    private static int max(int[][] arr) {
        int max = Integer.MIN_VALUE;

        for (int[] ints : arr) {
            for (int element : ints) if (element > max) max = element;
        }

        return max;
    }
}
