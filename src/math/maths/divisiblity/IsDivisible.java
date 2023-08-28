package math.maths.divisiblity;

public class IsDivisible {
    public static void main(String[] args) {
        System.out.println(check("769452"));
        // System.out.println(check("123456758933312"));
        // System.out.println(check("3635883959606670431112222"));
        System.out.println(check(769452));
        check();

    }

    // Q: is the given integer n divisible by 3 ; Time O(n) and space O(1)
    // This doesn't work with bigger inputs e.g. line 6 and 7
    private static boolean check(String str) {
        // if done 7 + 6 + 9 + 4 + 5 + 2 = 33 / 3 = 11
        int n = str.length();
        int sum = 0;

        // here instead of adding numbers; take each of their ASCII code = 321 / 3 = 107
        for (int i = 0; i < n; i++) {
            sum += str.charAt(i);
        }
        return (sum % 3 == 0);
    }

    private static long check(long n) {
        if (n < 3) {
            return 0;
        }

        if (n == 0) {
            return 0;
        }
        // System.out.println("Result: " + result);
        return n % 10 + check(n / 10);
    }

    // Time : O(1) and space O(1)
    private static void check() {
        long n = 769452;

        if (n % 3 == 0) {
            System.out.println("Yeah");
        } else {
            System.out.println("No");
        }

    }

}