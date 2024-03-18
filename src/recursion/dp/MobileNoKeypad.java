package recursion.dp;

public class MobileNoKeypad {
    // Movement array representing the allowed moves including the number itself
    // (up, left, right, down, and itself)
    static int[][] move = {{0, 8}, {1, 2, 4}, {1, 2, 3, 5}, {2, 3, 6}, {1, 4, 5, 7}, {2, 4, 5, 6, 8}, {3, 5, 6, 9}, {4, 7, 8}, {5, 7, 8, 9, 0}, {6, 8, 9}};

    static int getCount(int N) {
        if (N == 1) return 10;
        int[][] dp = new int[N + 1][10];

        // Initialization for N = 1
        for (int j = 0; j <= 9; j++) {
            dp[1][j] = 1;
        }

        // Fill the dp table
        for (int i = 2; i <= N; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k : move[j]) {
                    dp[i][j] += dp[i - 1][k];
                }
            }
        }

        // Sum up all possibilities of length N
        int totalCount = 0;
        for (int j = 0; j <= 9; j++) {
            totalCount += dp[N][j];
        }

        return totalCount;
    }

    public static void main(String[] args) {
        System.out.println(getCount(1)); // Output: 10
        System.out.println(getCount(2)); // Output: 36
    }
}
