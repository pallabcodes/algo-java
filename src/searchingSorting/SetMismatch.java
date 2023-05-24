package searchingSorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetMismatch {
    public static void main(String[] args) {
         System.out.println(Arrays.toString(findErrorNums(new int[]{1, 2, 2, 4})));
    }

    private static int[] findErrorNums(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            int correct = nums[i] - 1;
            if (nums[i] != nums[correct]) {
                swap(nums, i, correct);
            } else {
                i++;
            }
        }

        // search for the first missing number
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != index + 1) {
                return new int[]{nums[index], index + 1};
            }

        }

        return new int[]{-1, -1};
    }

    private static void swap(int[] arr, int first, int second) {
        // [ 1 0 ]
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }
}
