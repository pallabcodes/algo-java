package math.maths;

public class Prime {
    public static void main(String[] args) {
        int n = 20;
        // a prime no. starts from 2 which is why i = 2
        for (int i = 2; i <= n; i++) {
            System.out.println(i + " " + isPrime(i));
        }

    }

    static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }

        int c = 2;
        while (c * c <= n) {
            if (n % c == 0) {
                return false;
            }
            c++;
        }
        return true;
    }
}
