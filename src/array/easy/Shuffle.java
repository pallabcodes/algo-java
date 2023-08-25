package array.easy;

import java.util.Arrays;

public class Shuffle {
    public static void main(String[] args) {
        int[] nums = {2, 5, 1, 3, 4, 7};
        int n = 3;
        // System.out.println(Arrays.toString(shuffle(nums, n, 0)));
        // System.out.println(Arrays.toString(shuffle(new int[]{1, 2, 3, 4, 4, 3, 2, 1}, 4, 0)));
         int[] arr = {1, 1, 2, 2};
        System.out.println(Arrays.toString(shuffle(arr, 2, 0, 1, new int[arr.length])));
        System.out.println(Arrays.toString(shuffle(new int[]{2,5,1,3,4,7}, 3, 0, 1, new int[6])));
        System.out.println(Arrays.toString(shuffle(new int[]{1,2,3,4,4,3,2,1}, 4, 0, 1, new int[8])));
    }


    /*
     * ans = [1, 2, 1, 2]
     *
     * [1  1  2  2]; n = 2; i = 2; [ 0 0 0 0 ]
     * [1  1  2  2]; n = 2; i = 1,  j = 3; => [ 0 0 # # ]
     * [1  1  2  2]; n = 2; i = 0,  j = i + n = 2 ; [ # # # # ]

     * [1  1  2  2]; n = 2 ↑
     * */

    // https://leetcode.com/problems/shuffle-the-array/description/ ( I have solved it )
    private static int[] shuffle(int[] arr, int n, int i, int j, int[] list) {
        if (i == n) return list;


        int[] result = shuffle(arr, n, i + 1, i + n, list);
        int x = i + i;
        // System.out.println(x);
        result[x] = arr[i];
        result[x + 1] = arr[i + n];
        return result;


    }
}
