package recursion.dp.fibonacci;


// DP category: Counting Paths or Combinatorial DP (other than being Fibonacci Sequence)
public class UniquePaths {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // Initialize the first row and first column to 1 since there's only one way to reach those cells
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        // Fill the DP table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // The cell value is the sum of the cell above it and the cell to the left of it
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        // The bottom-right corner will have the total number of unique paths
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        UniquePaths solution = new UniquePaths();
        System.out.println(solution.uniquePaths(3, 7)); // Output: 28
        System.out.println(solution.uniquePaths(3, 2)); // Output: 3
    }
}
