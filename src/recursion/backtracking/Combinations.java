package recursion.backtracking;

import java.util.*;


// https://leetcode.com/problems/combinations/description/
public class Combinations {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> combinations = new ArrayList<>();
        backtrack(combinations, new ArrayList<>(), 1, n, k);
        return combinations;
    }

    private void backtrack(List<List<Integer>> combinations, List<Integer> currentCombination, int start, int n, int k) {
        if (k == 0) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = start; i <= n; i++) {
            currentCombination.add(i);
            backtrack(combinations, currentCombination, i + 1, n, k - 1);
            currentCombination.remove(currentCombination.size() - 1); // backtrack
        }
    }

    public static void main(String[] args) {
        Combinations solution = new Combinations();
        System.out.println(solution.combine(4, 2));
        System.out.println(solution.combine(1, 1));
    }
}
