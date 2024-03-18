package recursion.dp;

// DP category: Optimization DP
public class Balloon {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // New array with added 1s at both ends
        int[] newNums = new int[n + 2];
        newNums[0] = 1;
        newNums[n + 1] = 1;
        System.arraycopy(nums, 0, newNums, 1, n);

        // dp[i][j] represents the max coins that can be obtained
        // by bursting all the balloons between index i and j
        int[][] dp = new int[n + 2][n + 2];

        // Build up from smaller subarrays to the full problem
        for (int len = 1; len <= n; len++) {
            for (int start = 1; start <= n - len + 1; start++) {
                int end = start + len - 1;
                // k is the last balloon to burst in the subarray (start, end)
                for (int k = start; k <= end; k++) {
                    int coins = newNums[start - 1] * newNums[k] * newNums[end + 1];
                    coins += dp[start][k - 1] + dp[k + 1][end];
                    dp[start][end] = Math.max(dp[start][end], coins);
                }
            }
        }

        return dp[1][n];
    }

    public static void main(String[] args) {
        Balloon solution = new Balloon();
        System.out.println(solution.maxCoins(new int[]{3, 1, 5, 8})); // Output: 167
        System.out.println(solution.maxCoins(new int[]{1, 5})); // Output: 10
    }
}
