package recursion.dp.lcs;


// DP category = LCS variation
public class MinInsertions {
    public int minInsertions(String s) {
        String reverse = new StringBuilder(s).reverse().toString();
        int lpsLength = longestCommonSubsequence(s, reverse);
        return s.length() - lpsLength;
    }

    private int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        MinInsertions solution = new MinInsertions();
        System.out.println(solution.minInsertions("zzazz")); // Output: 0
        System.out.println(solution.minInsertions("mbadm")); // Output: 2
        System.out.println(solution.minInsertions("leetcode")); // Output: 5
    }
}
