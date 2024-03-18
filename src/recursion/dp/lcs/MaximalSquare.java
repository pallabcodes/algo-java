package recursion.dp.lcs;

public class MaximalSquare {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        int maxSide = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) { // First row or first column
                        dp[i][j] = 1;
                    } else {
                        // Update dp[i][j] based on its neighbors
                        dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]); // Update the maximum side length
                }
            }
        }

        return maxSide * maxSide; // Return the area of the largest square
    }

    public static void main(String[] args) {
        MaximalSquare solution = new MaximalSquare();
        char[][] matrix1 = {
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}
        };
        System.out.println(solution.maximalSquare(matrix1)); // Output: 4

        char[][] matrix2 = {{'0','1'},{'1','0'}};
        System.out.println(solution.maximalSquare(matrix2)); // Output: 1

        char[][] matrix3 = {{'0'}};
        System.out.println(solution.maximalSquare(matrix3)); // Output: 0
    }
}
