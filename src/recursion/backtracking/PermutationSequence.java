package recursion.backtracking;

import java.util.*;

public class PermutationSequence {
  public String getPermutation(int n, int k) { // T = O(N^2), S = O(N)
    int[] factorial = new int[n];
    List<Integer> numbers = new ArrayList<>();
    StringBuilder sb = new StringBuilder();

    // Precompute factorials and initialize the numbers list
    factorial[0] = 1;
    for (int i = 1; i < n; i++)
      factorial[i] = i * factorial[i - 1];
    for (int i = 1; i <= n; i++)
      numbers.add(i);

    // Adjust k to be zero-indexed
    k--;

    // Construct the kth permutation
    for (int i = n - 1; i >= 0; i--) {
      int index = k / factorial[i];
      sb.append(numbers.get(index));
      numbers.remove(index);
      k -= index * factorial[i];
    }

    return sb.toString();
  }

  public static void main(String[] args) {
    PermutationSequence solver = new PermutationSequence();
    int n = 3, k = 3;
    System.out.println(solver.getPermutation(n, k)); // Output: "213"
  }

}
