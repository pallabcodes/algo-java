package recursion.basics.easy;

public class Palindrome {
    public static void main(String[] args) {
        System.out.println(palin(1234121));
        System.out.println(palin(1221));
    }

    private static int rev(int n) {
        // sometimes might need some additional variables in the argument;
        // then make a helper function
        int digits = (int)(Math.log10(n)) + 1;
        return helper(n, digits);
    }

    private static int helper(int n, int digits) {
        if (n % 10 == n) {
            return n;
        }
        int rem = n % 10;
        return rem * (int)(Math.pow(10, digits - 1)) + helper(n / 10, digits - 1);

    }

    static boolean palin(int n) {
        return n == rev(n);
    }
}
