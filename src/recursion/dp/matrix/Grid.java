package recursion.dp.matrix;

import java.util.*;

public class Grid {
    private static int mcm(int[] arr, int i, int j) { // O(2^n)
        if (i == j) {
            return 0; // single matrix case
        }
        ;

        int ans = Integer.MAX_VALUE;

        for (int k = i; k <= j - 1; k++) {
            int cost1 = mcm(arr, i, k); // Ai....Ak => arr[i - 1] x arr[k]
            int cost2 = mcm(arr, k + 1, j); // Ai+1....Aj => arr[k] x arr[j]
            int cost3 = arr[i - 1] * arr[k] * arr[j];
            int finalCost = cost1 + cost2 + cost3;
            ans = Math.min(ans, finalCost);
        }
        return ans;
    }

    private static int mcmMemo(int[] arr, int i, int j, int[][] dp) { // O(n²)
        if (i == j) return 0;

        if (dp[i][j] != -1) { // already calculated
            return dp[i][j];
        }

        int ans = Integer.MAX_VALUE;

        for (int k = i; k <= j - 1; k++) {
            int cost1 = mcmMemo(arr, i, k, dp); // Ai....Ak => arr[i - 1] x arr[k]; cost1 represents a * b inherently
            int cost2 = mcmMemo(arr, k + 1, j, dp); // Ai+1....Aj => arr[k] x arr[j]; cost2 represents c * d inherently
            int cost3 = arr[i - 1] * arr[k] * arr[j]; // a (arr[i - 1]) * b (arr[k] * d (arr[j])
            ans = Math.min(ans, cost1 + cost2 + cost3);
        }

        return dp[i][j] = ans;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 3};
        int n = arr.length;
        System.out.println("recursion = " + mcm(arr, 1, n - 1));


        int[][] dp = new int[n][n];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println("Memoized = " + mcmMemo(arr, 1, n - 1, dp));

    }


}
