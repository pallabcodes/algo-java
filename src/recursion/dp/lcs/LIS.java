package recursion.dp.lcs;

// DP category : Longest increasing sub-sequence
public class LIS {
    public int lengthOfLIS(int[] nums) { // T = O( n log n), S = O(n)
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int i = 0, j = size;
            while (i != j) {
                int m = (i + j) / 2;
                if (tails[m] < x)
                    i = m + 1;
                else
                    j = m;
            }
            tails[i] = x;
            if (i == size) ++size;
        }
        return size;
    }

    public static void main(String[] args) {
        LIS solution = new LIS();
        System.out.println(solution.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18})); // Output: 4
        System.out.println(solution.lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3})); // Output: 4
        System.out.println(solution.lengthOfLIS(new int[]{7, 7, 7, 7, 7, 7, 7})); // Output: 1
    }
}
