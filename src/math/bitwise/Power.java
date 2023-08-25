package math.bitwise;

public class Power {
    public static void main(String[] args) {
        int base = 2;
        int power = 6; // 1 1 0 in binary number

        // any decimal no with power 0 will result to which is why ans = 1
        int ans = 1;

        while (power > 0) {
            if ((power & 1) == 1) {
                ans *= base;
            }

            base *= base;
            power = power >> 1;
        }

        System.out.println(ans);
    }
}
