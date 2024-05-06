package recursion.backtracking;

import java.util.*;

public class PalindromePermutationII {
  public List<String> generatePalindromes(String s) { // T = O((N/2 + 1)!) , S = O(N)
    int[] count = new int[128];
    int odd = 0;
    for (char c : s.toCharArray()) {
      count[c]++;
      if (count[c] % 2 == 0)
        odd--;
      else
        odd++;
    }
    List<String> ans = new ArrayList<>();
    if (odd > 1)
      return ans; // Not possible to form a palindrome
    String mid = "";
    List<Character> chars = new ArrayList<>();
    for (int i = 0; i < 128; i++) {
      if (count[i] % 2 == 1)
        mid += (char) i;
      for (int j = 0; j < count[i] / 2; j++)
        chars.add((char) i);
    }
    generateHalves(chars, mid, new boolean[chars.size()], new StringBuilder(), ans);
    return ans;
  }

  private void generateHalves(List<Character> chars, String mid, boolean[] used, StringBuilder sb, List<String> ans) {
    if (sb.length() == chars.size()) {
      ans.add(sb.toString() + mid + sb.reverse().toString());
      sb.reverse();
      return;
    }
    for (int i = 0; i < chars.size(); i++) {
      if (used[i] || i > 0 && chars.get(i) == chars.get(i - 1) && !used[i - 1])
        continue;
      used[i] = true;
      sb.append(chars.get(i));
      generateHalves(chars, mid, used, sb, ans);
      used[i] = false;
      sb.deleteCharAt(sb.length() - 1);
    }
  }

  public static void main(String[] args) {
    PalindromePermutationII solver = new PalindromePermutationII();
    String s = "aabb";
    System.out.println(solver.generatePalindromes(s)); // Output: ["abba", "baab"]
  }
}
