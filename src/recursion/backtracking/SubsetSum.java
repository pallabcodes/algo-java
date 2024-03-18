package recursion.backtracking;

public class SubsetSum {
    public static boolean canPartition(int[] nums) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // If totalSum is odd, it cannot be partitioned into two equal subsets
        if (totalSum % 2 != 0) return false;

        return canPartitionHelper(nums, 0, 0, totalSum);
    }

    private static boolean canPartitionHelper(int[] nums, int index, int currentSum, int totalSum) {
        // Base case: if current subset sum equals half of total sum, partition exists
        if (currentSum * 2 == totalSum) return true;

        // Base case: if index reaches the end or currentSum exceeds half of totalSum, no partition is possible
        if (index >= nums.length || currentSum > totalSum / 2) return false;

        // Include current element in the subset
        if (canPartitionHelper(nums, index + 1, currentSum + nums[index], totalSum)) {
            return true;
        }

        // Exclude current element from the subset and check
        return canPartitionHelper(nums, index + 1, currentSum, totalSum);
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 5, 11, 5};
        System.out.println(canPartition(arr1) ? "YES" : "NO"); // Output: YES

        int[] arr2 = {1, 3, 5};
        System.out.println(canPartition(arr2) ? "YES" : "NO"); // Output: NO
    }
}
