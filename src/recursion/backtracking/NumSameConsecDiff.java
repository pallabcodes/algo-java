package recursion.backtracking;

import java.util.*;

// DFS + backtracking
public class NumSameConsecDiff {
  public int[] numsSameConsecDiff(int n, int k) { // T = O(N^2), S = O(N)
    List<Integer> results = new ArrayList<>();
    if (n == 1)
      results.add(0); // Edge case: single digit numbers should include 0.
    for (int i = 1; i < 10; i++) { // Starting digit can't be 0 for numbers longer than 1.
      dfs(n - 1, i, k, results);
    }
    return results.stream().mapToInt(i -> i).toArray();
  }

  private void dfs(int n, int currentNum, int k, List<Integer> results) {
    if (n == 0) {
      results.add(currentNum);
      return;
    }
    int lastDigit = currentNum % 10;
    // Try next digit +k
    if (lastDigit + k < 10) {
      dfs(n - 1, currentNum * 10 + lastDigit + k, k, results);
    }
    // Try next digit -k, if k != 0 (to avoid duplicates)
    if (k != 0 && lastDigit - k >= 0) {
      dfs(n - 1, currentNum * 10 + lastDigit - k, k, results);
    }
  }

  public static void main(String[] args) {
    NumSameConsecDiff sol = new NumSameConsecDiff();
    int n = 3, k = 7;
    int[] results = sol.numsSameConsecDiff(n, k);
    for (int num : results) {
      System.out.println(num);
    }
  }

}
