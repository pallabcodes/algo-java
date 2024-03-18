package recursion.basics.arrays;

// This belongs to Fibonacci type of recursion
public class Tiling {

    // although similar, what is difference and similarity between this and climbing stairs?

    // both common: somehow assume or go to last value and work from there

    // N.B: if feels like the result any sub-problem would give the result main problem or its parent recursive call, then it's is fibonacci type of recursion
    private static int tilingProblem(int n) { // 2 * n (height * width (length) ) floor size

        // base case
        if (n == 0 || n == 1) return 1;

        // vertical choice ( n - 1) and horizontal choice ( n - 2)
        return tilingProblem(n - 1) + tilingProblem(n - 2);
    }

    public static void main(String[] args) {
        System.out.println(tilingProblem(4));
    }
}
