package recursion.dp.lcs;

// DP category: Longest common substring
public class LCS {
    int longestCommonSubstr(String S1, String S2, int n, int m){
        int[][] dp = new int[n + 1][m + 1];
        int result = 0; // Length of the longest common substring

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
                else if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    result = Math.max(result, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        LCS solution = new LCS();
        System.out.println(solution.longestCommonSubstr("ABCDGH", "ACDGHR", 6, 6)); // Output: 4
        System.out.println(solution.longestCommonSubstr("ABC", "ACB", 3, 3)); // Output: 1
    }
}
