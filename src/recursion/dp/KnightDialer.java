package recursion.dp;


// DP category: counting and path-finding
public class KnightDialer {
    public int knightDialer(int n) {
        int MOD = 1000000007;
        int[][] moves = {
                {4, 6}, {6, 8}, {7, 9}, {4, 8}, {3, 9, 0},
                {}, {1, 7, 0}, {2, 6}, {1, 3}, {2, 4}
        };

        int[][][] dp = new int[10][n][2];
        for (int i = 0; i < 10; i++) {
            dp[i][0][0] = 1;
        }

        for (int step = 1; step < n; step++) {
            for (int num = 0; num < 10; num++) {
                for (int move : moves[num]) {
                    dp[num][step][0] = (dp[num][step][0] + dp[move][step - 1][0]) % MOD;
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum = (sum + dp[i][n - 1][0]) % MOD;
        }

        return sum;
    }

    public static void main(String[] args) {
        KnightDialer kd = new KnightDialer();
        int n1 = 1, n2 = 2, n3 = 3131;
        System.out.println(kd.knightDialer(n1)); // Output: 10
        System.out.println(kd.knightDialer(n2)); // Output: 20
        System.out.println(kd.knightDialer(n3)); // Output: 136006598
    }
}
