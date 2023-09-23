package recursion.basics;

public class Fibonacci {
    public static void main(String[] args) {
        System.out.println(fibo(5));
        System.out.println(fibo(8));
    }

    private static int fibo(int n) {
        if (n == 1 || n == 0) return n; // if( n < 2) return n;
        return fibo(n - 1) + fibo(n - 2);
    }
}
