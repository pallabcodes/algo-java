package recursion.dp.fibonacci;

// DP category: Counting (other than having similarities with Fibonacci Sequence)
public class DecodeWays {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1; // Base case: empty string has one way to be decoded
        dp[1] = s.charAt(0) != '0' ? 1 : 0; // If the first char is not '0', there's 1 way to decode it

        for (int i = 2; i <= n; i++) {
            int oneDigit = Integer.parseInt(s.substring(i - 1, i));
            int twoDigits = Integer.parseInt(s.substring(i - 2, i));

            if (oneDigit >= 1) {
                dp[i] += dp[i - 1]; // If oneDigit is valid, it contributes dp[i-1] ways
            }

            if (twoDigits >= 10 && twoDigits <= 26) {
                dp[i] += dp[i - 2]; // If twoDigits is valid, it contributes dp[i-2] ways
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        DecodeWays solution = new DecodeWays();
        System.out.println(solution.numDecodings("12")); // Output: 2
        System.out.println(solution.numDecodings("226")); // Output: 3
        System.out.println(solution.numDecodings("06")); // Output: 0
    }

}
