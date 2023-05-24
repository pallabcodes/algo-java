package recursion.easy;

public class Factorial {
    public static void main(String[] args) {
        // 2! = 2 * 1
        // System.out.println(factorial(5));
        // 2 = 2 + 1; 5 = 5 + 4 + 3 + 2 + 1
        System.out.println(sum(5));

    }

    private static int factorial(int n) {
        if (n <= 1) return n;
        return n * factorial(n - 1);
    }

    private static int sum(int n) {
        if (n == 1) return n;
        return n + sum(n - 1);
    }


}
