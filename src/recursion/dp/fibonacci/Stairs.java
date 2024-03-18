package recursion.dp.fibonacci;

import java.util.Arrays;

public class Stairs {
    private static int countWays(int n) { // O(2^n)
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            return 0;
        }

        return countWays(n - 1) + countWays(n - 2);
    }

    private static int countWaysMemo(int n, int[] ways) { // O(n)
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            return 0;
        }

        if (ways[n] != -1) { // already calculated
            return ways[n];
        }

        ways[n] = countWaysMemo(n - 1, ways) + countWaysMemo(n - 2, ways);

        return ways[n];
    }

    private static int countWaysTab(int n) { // O(n)
        int[] dp = new int[n + 1];
        dp[0] = 1; // initialized with the result of base case

        // tabulation loop
        for (int i = 1; i <= n; i++) {
            if (i == 1) {
                dp[i] = dp[i - 1] + 0;
            } else {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        int n = 5;
        // System.out.println(countWays(n));

        int[] ways = new int[n + 1]; // [0, 0, 0, 0, 0, 0]
        Arrays.fill(ways, -1); // [-1, -1, -1, -1, -1, -1]

        System.out.println(countWaysMemo(n, ways)); // memoization
        System.out.println(countWaysTab(n)); // tabulation


    }
}
