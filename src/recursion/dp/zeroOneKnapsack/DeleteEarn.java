package recursion.dp.zeroOneKnapsack;

public class DeleteEarn {
    public int deleteAndEarn(int[] nums) {
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        int[] sums = new int[maxNum + 1];
        for (int num : nums) {
            sums[num] += num;
        }

        // Dynamic programming, similar to house robber problem
        int take = 0, skip = 0;
        for (int i = 0; i < sums.length; i++) {
            int takei = skip + sums[i];
            int skipi = Math.max(skip, take);
            take = takei;
            skip = skipi;
        }
        return Math.max(take, skip);
    }

    public static void main(String[] args) {
        DeleteEarn solution = new DeleteEarn();
        System.out.println(solution.deleteAndEarn(new int[]{3,4,2})); // Output: 6
        System.out.println(solution.deleteAndEarn(new int[]{2,2,3,3,3,4})); // Output: 9
    }
}
