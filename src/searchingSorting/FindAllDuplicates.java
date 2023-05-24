package searchingSorting;

import java.util.ArrayList;
import java.util.List;

public class FindAllDuplicates {
    public static void main(String[] args) {
        System.out.println(new FindAllDuplicates().findAllDuplicates(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));
    }

    public List<Integer> findAllDuplicates(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            int correct = nums[i] - 1;
            if (nums[i] != nums[correct]) {
                swap(nums, i, correct);
            } else {
                i++;
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != index + 1) {
                ans.add(nums[index]);
            }

        }

        return ans;

    }


    private static void swap(int[] arr, int first, int second) {
        // [ 1 0 ]
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }
}
