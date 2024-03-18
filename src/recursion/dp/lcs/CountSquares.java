package recursion.dp.lcs;

// DP category: Optimization on Grids (other than having similarities with LCS)
public class CountSquares {
    public int countSquares(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        int count = 0; // Total number of square sub-matrices

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) { // Edge case for first row or first column
                        dp[i][j] = 1;
                    } else { // Calculate dp[i][j] based on neighbors
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    count += dp[i][j]; // Add dp[i][j] to the total count
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        CountSquares solution = new CountSquares();
        int[][] matrix1 = {{0,1,1,1},{1,1,1,1},{0,1,1,1}};
        System.out.println(solution.countSquares(matrix1)); // Output: 15

        int[][] matrix2 = {{1,0,1},{1,1,0},{1,1,0}};
        System.out.println(solution.countSquares(matrix2)); // Output: 7
    }
}
