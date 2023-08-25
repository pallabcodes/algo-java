package math.bitwise;

public class FindUnique {
    public static void main(String[] args) {
        // xor =^, and = &, or = |, left-shift = << , right-shift = >>
        int[] arr = {2, 3, 3, 4, 2, 6, 4};
        System.out.println(ans(arr));
        System.out.println(findTheIthBit(31));
    }

    private static int ans(int[] arr) {
        int unique = 0;

        for (int n : arr) {
            // XOR each element and when the same element is found; then both matched elements are removed
            unique ^= n;
        }

        return unique;
    }

    // + , - , *,  /, %, bitwise, power, log
    private static int findTheIthBit(int n) {
        int ithBit = 5;
        return n & (1 << ithBit - 1); // return n | (1 << ithBit - 1);
    }
}
