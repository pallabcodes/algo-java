package recursion.dp;

import java.util.Arrays;

public class Kadane {
    private static int minJumps(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        dp[n - 1] = 0;

        for (int i = n - 2; i >= 0; i--) {
            int steps = nums[i];
            int ans = Integer.MAX_VALUE;

            for (int j = i + 1; j <= i + steps && j < n; j++) {
                if (dp[j] != -1) {
                    ans = Math.min(ans, dp[j] + 1);
                }
            }

            if (ans != Integer.MAX_VALUE) {
                dp[i] = ans;
            }
        }

        // dp[i] -> i to n - 1
        return dp[0];
    }

    // Approach: Two pointer
    private static int maxProduct(int[] nums) { // O(n), O(1)
        int n = nums.length;
        int leftProduct = 1, rightProduct = 1;
        int ans = nums[0];

        for (int i = 0; i < n; i++) {
            // if any of the leftProduct of rightProduct has value 0 then update it to 1
            leftProduct = leftProduct == 0 ? 1 : leftProduct;
            rightProduct = rightProduct == 0 ? 1 : rightProduct;

            // prefix product
            leftProduct *= nums[i];

            // suffix product
            rightProduct *= nums[n - 1 - i];

            ans = Math.max(ans, Math.max(leftProduct, rightProduct));
        }

        return ans;
    }

    private static int maxProductDP(int[] nums) { // O(n), O(n)
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        // Arrays to store the maximum and minimum product up to the current position
        int[] maxDp = new int[n];
        int[] minDp = new int[n];

        maxDp[0] = minDp[0] = nums[0];
        int result = nums[0];

        for (int i = 1; i < n; i++) {
            // Calculate the maximum/minimum product at the current position by considering:
            // 1. The current number itself
            // 2. The product of the current number and the maximum product until the previous position
            // 3. The product of the current number and the minimum product until the previous position
            if (nums[i] >= 0) {
                maxDp[i] = Math.max(nums[i], maxDp[i - 1] * nums[i]);
                minDp[i] = Math.min(nums[i], minDp[i - 1] * nums[i]);
            } else {
                maxDp[i] = Math.max(nums[i], minDp[i - 1] * nums[i]);
                minDp[i] = Math.min(nums[i], maxDp[i - 1] * nums[i]);
            }

            // Update the result with the maximum product found so far
            result = Math.max(result, maxDp[i]);
        }

        return result;
    }

    // DP but uses iteration here (but not exactly tabulation since it doesn't use 1D/2D array/table here)
    private static int maxProductOptimizedDP(int[] nums) { // O(n), O(1)
        if (nums == null || nums.length == 0) return 0;

        // Variables to store the current maximum and minimum product sub-array ending at the current position
        int currMaxProduct = nums[0];
        int currMinProduct = nums[0];

        // Variable to store the maximum product found so far
        int maxProduct = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                // Swap the currMaxProduct and currMinProduct when nums[i] is negative,
                // because a negative number flips max and min
                int temp = currMaxProduct;
                currMaxProduct = currMinProduct;
                currMinProduct = temp;
            }

            // Update the currMaxProduct and currMinProduct
            currMaxProduct = Math.max(nums[i], currMaxProduct * nums[i]);
            currMinProduct = Math.min(nums[i], currMinProduct * nums[i]);

            // Update the overall maxProduct
            maxProduct = Math.max(maxProduct, currMaxProduct);
        }

        return maxProduct;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(minJumps(nums));

        // System.out.println(maxProduct(new int[]{2, 3, -2, 4}));
        // System.out.println(maxProduct(new int[]{-2, 0, -1}));

        // System.out.println("dp solution for max product = " + maxProductDP(new int[]{2, 3, -2, 4}));
        // System.out.println("dp solution for max product = " + maxProductDP(new int[]{-2, 0, -1}));
    }
}
