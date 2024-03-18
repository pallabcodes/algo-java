package recursion.backtracking;

import java.util.*;

public class WordSearchII {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> currentList, int[] nums, int start) {
        // Add the current subset to the result
        result.add(new ArrayList<>(currentList));

        for (int i = start; i < nums.length; i++) {
            // Include the current element and explore further
            currentList.add(nums[i]);
            backtrack(result, currentList, nums, i + 1);
            // Exclude the current element (backtrack) and explore further
            currentList.remove(currentList.size() - 1);
        }
    }

    public static void main(String[] args) {
        WordSearchII solution = new WordSearchII();
        System.out.println(solution.subsets(new int[]{1, 2, 3}));
        System.out.println(solution.subsets(new int[]{0}));
    }
}
