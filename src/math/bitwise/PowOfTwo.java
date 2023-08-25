package math.bitwise;

public class PowOfTwo {
    public static void main(String[] args) {
        // int n = 31; // 31 is not power of 2, so it will be false
        int n = 16; // 31 is not power of 2, so it will be true
        boolean ans = (n & (n - 1)) == 0;
        System.out.println(ans);
    }
}
