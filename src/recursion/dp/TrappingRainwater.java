package recursion.dp;

// DP category = Optimization DP
public class TrappingRainwater {
    public int trap(int[] height) {
        if (height == null || height.length == 0) return 0;
        int n = height.length;
        int waterTrapped = 0;

        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // Fill leftMax array
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }

        // Fill rightMax array
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i + 1]);
        }

        // Calculate trapped water
        for (int i = 0; i < n; i++) {
            waterTrapped += Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return waterTrapped;
    }

    public static void main(String[] args) {
        TrappingRainwater solution = new TrappingRainwater();
        System.out.println(solution.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1})); // Output: 6
        System.out.println(solution.trap(new int[]{4,2,0,3,2,5})); // Output: 9
    }
}
