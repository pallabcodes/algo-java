package recursion.backtracking;

public class PartitionIntoKEqualSumSubsets {
    public static boolean canPartitionKSubsets(int[] arr, int K) {
        int totalSum = 0;
        for (int num : arr) {
            totalSum += num;
        }

        if (totalSum % K != 0) return false;
        int targetSum = totalSum / K;
        boolean[] used = new boolean[arr.length];

        return canPartition(arr, K, used, targetSum, 0, 0, 0);
    }

    private static boolean canPartition(int[] arr, int K, boolean[] used,
                                        int targetSum, int currentSum, int start, int currentNumElements) {
        if (K == 0) return true; // K subsets formed
        if (currentSum > targetSum) return false; // Exceeds target sum

        if (currentSum == targetSum) {
            // Current subset is complete, proceed to next subset
            return canPartition(arr, K-1, used, targetSum, 0, 0, 0);
        }

        for (int i = start; i < arr.length; i++) {
            if (!used[i]) {
                used[i] = true;
                if (canPartition(arr, K, used, targetSum, currentSum + arr[i], i + 1, currentNumElements+1)) {
                    return true;
                }
                used[i] = false; // Backtrack
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[] arr1 = {2, 1, 4, 5, 6};
        System.out.println(canPartitionKSubsets(arr1, 3) ? "Yes" : "No");

        int[] arr2 = {2, 1, 5, 5, 6};
        System.out.println(canPartitionKSubsets(arr2, 3) ? "Yes" : "No");
    }
}
