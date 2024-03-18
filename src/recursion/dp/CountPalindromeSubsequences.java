package recursion.dp;

// DP category = Counting DP
public class CountPalindromeSubsequences {
    private static final int MOD = 1000000007;

    public int countPalindromicSubsequences(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = 1; // Every single character is a palindrome
        }

        for (int len = 2; len <= n; len++) {
            for (int start = 0; start <= n - len; start++) {
                int end = start + len - 1;
                if (s.charAt(start) == s.charAt(end)) {
                    int low = start + 1;
                    int high = end - 1;

                    while (low <= high && s.charAt(low) != s.charAt(start)) {
                        low++;
                    }
                    while (low <= high && s.charAt(high) != s.charAt(start)) {
                        high--;
                    }
                    if (low > high) {
                        dp[start][end] = dp[start + 1][end - 1] * 2 + 2;
                    } else if (low == high) {
                        dp[start][end] = dp[start + 1][end - 1] * 2 + 1;
                    } else {
                        dp[start][end] = dp[start + 1][end - 1] * 2 - dp[low + 1][high - 1];
                    }
                } else {
                    dp[start][end] = dp[start][end - 1] + dp[start + 1][end] - dp[start + 1][end - 1];
                }
                // Add MOD before taking modulo to ensure non-negative result
                dp[start][end] = (dp[start][end] % MOD + MOD) % MOD;
            }
        }

        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        CountPalindromeSubsequences solution = new CountPalindromeSubsequences();
        System.out.println(solution.countPalindromicSubsequences("bccb")); // Output: 6
        System.out.println(solution.countPalindromicSubsequences("abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba")); // Output: 104860361
    }
}
