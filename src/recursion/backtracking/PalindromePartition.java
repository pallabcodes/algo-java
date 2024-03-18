package recursion.backtracking;

import java.util.*;

public class PalindromePartition {
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), s, 0);
        return result;
    }

    private void backtrack(List<List<String>> result, List<String> currentList, String s, int start) {
        if (start >= s.length()) {
            result.add(new ArrayList<>(currentList));
        }

        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                // Add current substring in the currentList
                currentList.add(s.substring(start, end + 1));
                backtrack(result, currentList, s, end + 1);
                // Remove the current substring from currentList
                currentList.remove(currentList.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s, int low, int high) {
        while (low < high) {
            if (s.charAt(low++) != s.charAt(high--)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        PalindromePartition solution = new PalindromePartition();
        System.out.println(solution.partition("aab"));
        System.out.println(solution.partition("a"));
    }
}
