package recursion.backtracking;

import java.util.*;

// https://leetcode.com/problems/permutations-ii/description/
public class PermutationUnique {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(nums); // Sort to handle duplicates
        backtrack(nums, new ArrayList<>(), results, new boolean[nums.length]);
        return results;
    }

    private void backtrack(int[] nums, List<Integer> current, List<List<Integer>> results, boolean[] used) {
        if (current.size() == nums.length) {
            results.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // Skip duplicates or already used elements
            if (used[i] || (i > 0 && nums[i] == nums[i-1] && !used[i-1])) {
                continue;
            }
            current.add(nums[i]);
            used[i] = true;
            backtrack(nums, current, results, used);
            used[i] = false;
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        PermutationUnique solution = new PermutationUnique();
        int[] nums1 = {1, 1, 2};
        System.out.println(solution.permuteUnique(nums1));
        int[] nums2 = {1, 2, 3};
        System.out.println(solution.permuteUnique(nums2));
    }

}
