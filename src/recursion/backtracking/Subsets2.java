package recursion.backtracking;


import java.util.*;

public class Subsets2 {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums); // Sort to handle duplicates
        List<List<Integer>> results = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), results);
        return results;
    }

    private void backtrack(int[] nums, int start, List<Integer> currentSubset, List<List<Integer>> results) {
        results.add(new ArrayList<>(currentSubset));

        for (int i = start; i < nums.length; i++) {
            // Skip duplicates
            if (i > start && nums[i] == nums[i - 1]) continue;

            currentSubset.add(nums[i]);
            backtrack(nums, i + 1, currentSubset, results);
            currentSubset.remove(currentSubset.size() - 1); // Backtrack
        }
    }

    public static void main(String[] args) {
        Subsets2 solution = new Subsets2();
        System.out.println(solution.subsetsWithDup(new int[]{1, 2, 2}));
        System.out.println(solution.subsetsWithDup(new int[]{0}));
    }
}
