package math.bitwise;

public class SetBits {
    public static void main(String[] args) {
        int n = 245678;
        System.out.println(Integer.toBinaryString(n));

        System.out.println(setBits(n));

    }

    // total no. of time 1 appears of the given n
    private static int setBits(int n) {
        int count = 0;

        while (n > 0) {
            count++;
            n = n & (n - 1);
        }

        return count;
    }
}
