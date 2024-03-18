package recursion.basics.easy;

public class Reverse {


    static int sum = 0;

    private static int rev(int n) {
        // if(n % 10 == n) return n; // or
        if (n == 0) return 0;

        int rem = n % 10;
        sum = sum * 10 + rem;
        rev(n / 10);
        return sum;
    }

    private static void rev1(int n) {
        // if(n % 10 == n) return n; // or
        if (n == 0) return;

        int rem = n % 10;
        sum = sum * 10 + rem;
        rev1(n / 10);
    }

    private static int rev2(int n) {
        // sometimes might need some additional variables in the argument;
        // then make a helper function
        int digits = (int) (Math.log10(n)) + 1; // Math.log10(2468) = 3 + 1; Math.log10(246) = 2 + 1; Math.log10(24) = 1 + 1; Math.log10(2) = 0 + 1
        return helper(n, digits);
    }

    private static int helper(int n, int digits) {
        if (n % 10 == n) return n;
        int rem = n % 10;
        // just needed to figure when rem = 8, digits = 3; how to reach
        //  2 -> + 40 + 2 ->  600 + 42 -> 8 * 1000 + 6
        return rem * (int)(Math.pow(10, digits - 1)) + helper(n / 10, digits - 1);

    }

    public static void main(String[] args) {
        //rev1(1234);
        //System.out.println(sum);
        System.out.println(rev2(2468));
    }
}
