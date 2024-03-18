package greedy;

import java.util.*;

public class MinimumSum {
    public static void main(String[] args) {
        int[] A = {4, 1, 8, 7};
        int[] B = {2, 3, 5, 6};

        Arrays.sort(A);
        Arrays.sort(B);

        int minDiff = 0;

        for (int i = 0; i < A.length; i++) {
            minDiff += Math.abs(A[i] - B[i]); // with .abs() if result is -1 then it will become 1
        }

        System.out.println("min absolute pair is = " + minDiff);
    }
}
