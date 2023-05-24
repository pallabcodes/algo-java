package searchingSorting;

import java.util.ArrayList;
import java.util.List;

public class FindDuplicate {
    public static void main(String[] args) {
        System.out.println(new FindDuplicate().findDuplicate(new int[]{4, 3, 1, 2, 2}));
        System.out.println(new FindDuplicate().findDuplicate(new int[]{3, 1, 3, 4, 2}));

    }

    public int findDuplicate(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            if (nums[i] != i + 1) {
                int correct = nums[i] - 1;
                if (nums[i] != nums[correct]) {
                    swap(nums, i, correct);
                } else {
                    return nums[i];
                }
            } else {
                i++;
            }
        }


        return -1;

    }

    private static void swap(int[] arr, int first, int second) {
        // [ 1 0 ]
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }
}
