package recursion.arrays;

public class Fibo {
    public static void main(String[] args) {
        int ans = fib(4);
        System.out.println(ans);

    }

    private static int fib(int n) {
        // with fibonacci first value could be 1 and second value could be 0
        // so when n = 1; this (n - 1 = 0) and (n - 2 = -1) so stop when n < 2
        if (n < 2) return n;
        return fib(n - 1) + fib(n - 2);
    }
}
