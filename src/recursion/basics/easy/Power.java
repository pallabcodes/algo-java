package recursion.basics.easy;

public class Power {

    // x^n => 2^10 = 1024
    private static int power(int x, int n) { // O(n)
        if (n == 0) return 1; // 2^0 = 1
        return x * power(x, n - 1);
    }

    // optimized
    private static int optimizedPower(int a, int n) { // O(logn)
        if (n == 0) return 1;

        int halfPower = optimizedPower(a, n / 2);
        int halfPowerSq = halfPower * halfPower;


        // when  n is odd
        if (n % 2 != 0) {
            halfPowerSq = a * halfPowerSq;
        }

        return halfPowerSq;
    }

    public static void main(String[] args) {
        System.out.println(power(2, 10));
        System.out.println(optimizedPower(2, 4));
    }

}
