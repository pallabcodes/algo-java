package recursion.dp.fibonacci;

public class Main {

    // Dynamic Programming : Memoization (top down)
    private static int fib(int n, int[] f) { // O(n), O(n)

        if (n == 0 || n == 1) {
            return n;
        }

        if (f[n] != 0) { // fib(n) is already calculated
            return f[n];
        }

        f[n] = fib(n - 1, f) + fib(n - 2, f);

        return f[n];
    }

    // Dynamic Programming : Tabulation (bottom up)
    private static int fibTabulation(int n) { // O(n), O(1)
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n]; // ans
    }


    public static void main(String[] args) {
        int n = 5;
        int[] f = new int[n + 1]; // to allow this array have size of 6 and it looks by default [0, 0, 0, 0, 0, 0]

        System.out.println(fib(5, f)); // Memoization

        System.out.println(fibTabulation(5)); // Tabulation
    }
}
