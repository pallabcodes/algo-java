package recursion.backtracking;

import java.util.*;

// https://leetcode.com/problems/combination-sum-ii/description/
public class CombinationSum2 {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(candidates); // Sort to help skip duplicates
        backtrack(candidates, target, results, new ArrayList<>(), 0);
        return results;
    }

    private void backtrack(int[] candidates, int target, List<List<Integer>> results, List<Integer> currentCombination, int start) {
        if (target == 0) {
            results.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // Skip duplicates
            if (i > start && candidates[i] == candidates[i - 1]) continue;
            // Early termination
            if (candidates[i] > target) break;

            currentCombination.add(candidates[i]);
            backtrack(candidates, target - candidates[i], results, currentCombination, i + 1);
            currentCombination.remove(currentCombination.size() - 1); // backtrack
        }
    }

    public static void main(String[] args) {
        CombinationSum2 solution = new CombinationSum2();
        int[] candidates1 = {10, 1, 2, 7, 6, 1, 5};
        System.out.println(solution.combinationSum2(candidates1, 8));
        int[] candidates2 = {2, 5, 2, 1, 2};
        System.out.println(solution.combinationSum2(candidates2, 5));
    }
}
