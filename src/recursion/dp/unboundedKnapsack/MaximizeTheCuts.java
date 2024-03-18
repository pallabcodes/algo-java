package recursion.dp.unboundedKnapsack;

public class MaximizeTheCuts {
    public int maximizeTheCuts(int n, int x, int y, int z) {
        int[] dp = new int[n + 1];
        dp[0] = 0; // Base case: no cuts needed for length 0

        // Iterate through each possible length up to n
        for (int i = 1; i <= n; i++) {
            // Initialize current maximum cuts as -infinity
            int maxCuts = Integer.MIN_VALUE;

            // Check if cutting with x, y, or z is possible
            if (i >= x) maxCuts = Math.max(maxCuts, dp[i - x]);
            if (i >= y) maxCuts = Math.max(maxCuts, dp[i - y]);
            if (i >= z) maxCuts = Math.max(maxCuts, dp[i - z]);

            // If cutting with any of x, y, or z is possible, update dp[i]
            if (maxCuts != Integer.MIN_VALUE) {
                dp[i] = maxCuts + 1; // Add 1 to include the current cut
            } else {
                dp[i] = 0; // No possible cuts for length i
            }
        }

        // Return the maximum cuts for length n
        return dp[n];
    }

    public static void main(String[] args) {
        MaximizeTheCuts solution = new MaximizeTheCuts();
        int n1 = 4, x1 = 2, y1 = 1, z1 = 1;
        int n2 = 5, x2 = 5, y2 = 3, z2 = 2;
        System.out.println(solution.maximizeTheCuts(n1, x1, y1, z1)); // Output: 4
        System.out.println(solution.maximizeTheCuts(n2, x2, y2, z2)); // Output: 2
    }
}
