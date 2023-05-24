package math.bitwise;

public class FindUnique {
    public static void main(String[] args) {
        // xor =^, and = &, or = |, left-shift = << , right-shift = >>
        int[] arr = {2, 3, 3, 4, 2, 6, 4};
        System.out.println(ans(arr));
    }
    private static int ans(int[] arr) {
        int unique = 0;

        for(int n : arr) {
            unique ^= n;
        }

        return unique;
    }
}
