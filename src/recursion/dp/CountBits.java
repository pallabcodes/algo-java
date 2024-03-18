package recursion.dp;

import java.util.*;


// DP category: Counting DP or Bit manipulation

public class CountBits {
    public int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }
    public static void main(String[] args) {
        CountBits solution = new CountBits();
        int n1 = 2;
        int n2 = 5;
        System.out.println(Arrays.toString(solution.countBits(n1))); // Output: [0, 1, 1]
        System.out.println(Arrays.toString(solution.countBits(n2))); // Output: [0, 1, 1, 2, 1, 2]
    }
}
