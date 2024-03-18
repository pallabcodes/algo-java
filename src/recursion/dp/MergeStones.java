package recursion.dp;

// DP category = Optimization DP
public class MergeStones {
    public int mergeStones(int[] stones, int k) {
        int n = stones.length;
        if ((n - 1) % (k - 1) != 0) return -1;

        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + stones[i];
        }

        int[][] dp = new int[n][n];
        for (int m = k; m <= n; ++m) { // size of the subproblem
            for (int i = 0; i + m <= n; ++i) {
                int j = i + m - 1; // end index of the subproblem
                dp[i][j] = Integer.MAX_VALUE;
                for (int mid = i; mid < j; mid += k - 1) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][mid] + dp[mid + 1][j]);
                }
                if ((j - i) % (k - 1) == 0) {
                    dp[i][j] += prefixSum[j + 1] - prefixSum[i];
                }
            }
        }

        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        MergeStones solution = new MergeStones();
        System.out.println(solution.mergeStones(new int[]{3,2,4,1}, 2)); // Output: 20
        System.out.println(solution.mergeStones(new int[]{3,2,4,1}, 3)); // Output: -1
        System.out.println(solution.mergeStones(new int[]{3,5,1,2,6}, 3)); // Output: 25
    }
}
