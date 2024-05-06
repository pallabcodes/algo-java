package recursion.backtracking;

import java.util.*;


// combination: Each value must Unique value and order does not matter
public class CombinationSumIII {
  public List<List<Integer>> combinationSum3(int k, int n) { // T = O(C = no. of valid combinations), S = O(K)
    List<List<Integer>> results = new ArrayList<>();
    backtrack(results, new ArrayList<>(), k, n, 1);
    return results;
  }

  private void backtrack(List<List<Integer>> results, List<Integer> currentCombination, int k, int remain, int start) {
    if (remain < 0 || currentCombination.size() > k) {
      // If we exceed the sum or size, no need to proceed.
      return;
    }

    if (remain == 0 && currentCombination.size() == k) {
      // Found a valid combination
      results.add(new ArrayList<>(currentCombination));
      return;
    }

    for (int i = start; i <= 9; i++) {
      // Add the current number to the combination
      currentCombination.add(i);
      // Recursively try the next numbers with the updated sum
      backtrack(results, currentCombination, k, remain - i, i + 1);
      // Backtrack, remove the last number added
      currentCombination.remove(currentCombination.size() - 1);
    }
  }

  public static void main(String[] args) {
    CombinationSumIII solver = new CombinationSumIII();
    int k = 3; // Number of digits in the combination
    int n = 9; // Target sum
    List<List<Integer>> combinations = solver.combinationSum3(k, n);
    for (List<Integer> combination : combinations) {
      System.out.println(combination);
    }

  }

}
