package recursion.basics.easy;

public class Fibonacci { // T = O(2^n), S = O(n)
    public static void main(String[] args) {
        System.out.println(fibonacci(6));
    }

    private static int fibonacci(int n) {
        if (n < 2) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
