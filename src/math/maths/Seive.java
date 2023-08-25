package math.maths;

public class Seive {
    public static void main(String[] args) {
        int n = 37;
        // if did new boolean[n] => it will have size 39 : so to allow 40 items n + 1
        boolean[] primes = new boolean[n + 1];
        sieve(n, primes);
    }

    // false in array means number is prime
    static void sieve(int n, boolean[] primes) {
        // prime no. starts from 2 which is why i = 2
        for (int i = 2; i * i <= n; i++) {
            if (!primes[i]) {
                for (int j = i * 2; j <= n; j += i) {
                    primes[j] = true;
                }
            }
        }

        for (int i = 2; i <= n; i++) {
            if (!primes[i]) {
                System.out.print(i + " ");
            }
        }
    }
}
