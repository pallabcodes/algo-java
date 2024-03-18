package greedy;

public class KthOddNo {
    // T = O(1), S = O(1)
    private static int kthOdd(int[] range, int K) {
        if (K <= 0) return 0;

        int L = range[0];
        int R = range[1];

        if ((R & 1) > 0) {
            int count = (int) Math.ceil((R - L + 1) / 2);
            if (K > count) {
                return 0;
            } else {
                return (R - 2 * K + 2);
            }
        } else {
            int count = (R - L + 1) / 2;
            if (K > count) {
                return 0;
            } else {
                return (R - 2 * K + 1);
            }
        }

    }

    public static void main(String[] args) {
        int[] p = {-3, 3};
        int k = 1;
        System.out.println(kthOdd(p, k));
    }
}
