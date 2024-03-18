package recursion.dp;

// DP category: Optimization DP
public class EggDrop {
    public int superEggDrop(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        int m = 0;
        while (dp[k][m] < n) {
            m++;
            for (int i = 1; i <= k; i++) {
                dp[i][m] = dp[i - 1][m - 1] + dp[i][m - 1] + 1;
            }
        }
        return m;
    }

    public static void main(String[] args) {
        EggDrop solution = new EggDrop();
        System.out.println(solution.superEggDrop(1, 2)); // Output: 2
        System.out.println(solution.superEggDrop(2, 6)); // Output: 3
        System.out.println(solution.superEggDrop(3, 14)); // Output: 4
    }
}
