package recursion.dp.lcs;

public class FindLength {
    public int findLength(int[] nums1, int[] nums2) {
        int maxLen = 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];

        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
                // No need to explicitly set dp[i][j] to 0 when nums1[i-1] != nums2[j-1]
                // since the default value in the DP table is 0.
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        FindLength solution = new FindLength();
        int[] nums1 = {1,2,3,2,1};
        int[] nums2 = {3,2,1,4,7};
        System.out.println(solution.findLength(nums1, nums2)); // Output: 3

        int[] nums1_2 = {0,0,0,0,0};
        int[] nums2_2 = {0,0,0,0,0};
        System.out.println(solution.findLength(nums1_2, nums2_2)); // Output: 5
    }
}
