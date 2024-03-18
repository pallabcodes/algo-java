package recursion.dp.zeroOneKnapsack;

// DP category = Optimization DP (other than having similarities with 0-1 knapsack)
public class MaxProfit {
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        if (k >= n / 2) {
            // If k is large enough, we can treat this as an unlimited transaction problem
            int maxProfit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }

        // dp[i][j] represents the max profit up until prices[j] using at most i transactions
        int[][] dp = new int[k + 1][n];
        for (int i = 1; i <= k; i++) {
            int maxDiff = -prices[0]; // Represents the max difference of -prices[j] + dp[i-1][j] (buying price)
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + maxDiff);
                maxDiff = Math.max(maxDiff, dp[i - 1][j] - prices[j]);
            }
        }

        return dp[k][n - 1];
    }

    public static void main(String[] args) {
        MaxProfit solution = new MaxProfit();
        System.out.println(solution.maxProfit(2, new int[]{2,4,1})); // Output: 2
        System.out.println(solution.maxProfit(2, new int[]{3,2,6,5,0,3})); // Output: 7
    }
}
