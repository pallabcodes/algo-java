package recursion.dp.zeroOneKnapsack;

import java.util.Arrays;

public class Main {

    private static int knapsack(int[] val, int[] wt, int W, int n, int[][] dp) { // O(n * W)
        if (W == 0 || n == 0) return 0;

        if (dp[n][W] != -1) { // already calculated the ans
            return dp[n][W];
        }

        if (wt[n - 1] <= W) { // valid
            // include the current value
            int ans1 = val[n - 1] + knapsack(val, wt, W - wt[n - 1], n - 1, dp);
            // exclude the current value
            int ans2 = knapsack(val, wt, W, n - 1, dp);
            dp[n][W] = Math.max(ans1, ans2);
            return dp[n][W];
        } else { // invalid
            dp[n][W] = knapsack(val, wt, W, n - 1, dp);
            return dp[n][W];
        }
    }

    private static void print(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int knapsackTab(int[] val, int[] wt, int W) {
        int n = val.length;
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 0; i < dp.length; i++) { // assign 0 to every row's oth column
            dp[i][0] = 0;
        }

        for (int j = 0; j < dp[0].length; j++) { // assign 0 to every column of first row
            dp[0][j] = 0;
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < W + 1; j++) {
                int v = val[i - 1]; // `value` of ith item  (i.e. current item from iteration)
                int w = wt[i - 1]; // `weight` of ith item (i.e. current item from iteration)

                if (w <= j) { // valid
                    int includeProfit = v + dp[i - 1][j - w];
                    int excludeProfit = dp[i - 1][j];
                    dp[i][j] = Math.max(includeProfit, excludeProfit);
                } else { // invalid
                    int excludeProfit = dp[i - 1][j];
                    dp[i][j] = excludeProfit;
                }
            }
        }

        print(dp);

        return dp[n][W];


    }

    public static void main(String[] args) {
        int[] values = {15, 14, 10, 45, 30};
        int[] weights = {2, 5, 1, 3, 4};
        int W = 7; // capacity of the bag
        int[][] dp = new int[values.length + 1][W + 1];

        // i = row, j = col (also i = item and j = W (capacity)
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) dp[i][j] = -1;
        }
        // System.out.println(Arrays.deepToString(dp));

        // System.out.println(knapsack(values, weights, W, values.length, dp));
        System.out.println(knapsackTab(values, weights, W));
    }
}
