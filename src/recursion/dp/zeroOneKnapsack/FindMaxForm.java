package recursion.dp.zeroOneKnapsack;

public class FindMaxForm {
    public int findMaxForm(String[] strs, int m, int n) {
        // dp array
        int[][] dp = new int[m+1][n+1];

        // Iterate over each string
        for (String str : strs) {
            // Count zeros and ones in the current string
            int zeros = 0, ones = 0;
            for (char c : str.toCharArray()) {
                if (c == '0') zeros++;
                else ones++;
            }

            // Dynamic programming transition
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i-zeros][j-ones] + 1);
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        FindMaxForm sol = new FindMaxForm();

        // Example 1
        String[] strs1 = {"10","0001","111001","1","0"};
        int m1 = 5, n1 = 3;
        System.out.println(sol.findMaxForm(strs1, m1, n1)); // Output: 4

        // Example 2
        String[] strs2 = {"10","0","1"};
        int m2 = 1, n2 = 1;
        System.out.println(sol.findMaxForm(strs2, m2, n2)); // Output: 2
    }
}
