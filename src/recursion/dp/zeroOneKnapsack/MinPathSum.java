package recursion.dp.zeroOneKnapsack;


// DP category: Optimization on Grids (other than having similarities with 0-1 knapsack)
public class MinPathSum {
    public int minPathSum(int[][] grid) { // T = O(mn), S = O(mn)
        int m = grid.length, n = grid[0].length;
        // dp[i][j] represents the minimum path sum to reach (i, j) from (0, 0)
        int[][] dp = new int[m][n];

        // Initialize the top-left cell
        dp[0][0] = grid[0][0];

        // Initialize the first column
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        // Initialize the first row
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        // Fill the dp table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // The cell value is the min of the cell above and to the left plus the current cell's value
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        // Return the minimum path sum to the bottom-right corner
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        MinPathSum solution = new MinPathSum();
        int[][] grid1 = {{1,3,1},{1,5,1},{4,2,1}};
        System.out.println(solution.minPathSum(grid1)); // Output: 7

        int[][] grid2 = {{1,2,3},{4,5,6}};
        System.out.println(solution.minPathSum(grid2)); // Output: 12
    }
}
