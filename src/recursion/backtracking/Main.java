package recursion.backtracking;


// Pre-requisites: i) Recursion basics ii) Divide and conquer

// Types of Backtracking: i) decision (answer exists or not) ii) optimization (shortest path from available path) iii) Enumerations (all paths)

public class Main {
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

    public static void main(String[] args) {
        int n = 5;
        int[] f = new int[n + 1]; // to allow this array have size of 6 and it looks by default [0, 0, 0, 0, 0, 0]
        System.out.println(fib(5, f));
    }

}
