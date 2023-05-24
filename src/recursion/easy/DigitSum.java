package recursion.easy;

public class DigitSum {
    public static void main(String[] args) {
        System.out.println(sum(1342));

    }
    private static int sum(int n) {
        /*
         * n == 0 so return 0
         * rem 1 % 10 = 1; 1 / 10 = 0; 1 + 0 = 1
         * rem 13 % 10 = 3; 13 / 10 = 1; 3 + 1 = 4
         * rem 134 % 10 = 4; 134 / 10 = 13; 4+4 = 8
         * rem 1342 % 10 = 2; 1342 / 10 = 134; 8 + 2 = 10
         *
         * */
        if(n == 0) return n; // n = 1; n % 10  == n then stop and return n

        return (n % 10) + sum(n / 10);

    }
}
