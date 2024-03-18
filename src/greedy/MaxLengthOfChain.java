package greedy;

import java.util.*;

public class MaxLengthOfChain {
    public static void main(String[] args) { // O(nlogn)
        int[][] pairs = {
                {5, 24},
                {39, 60},
                {5, 28},
                {27, 40},
                {50, 90},
        };

        // O(nlog n)
        Arrays.sort(pairs, Comparator.comparingDouble(o -> o[1]));

        // for (int[] row : pairs) System.out.println(Arrays.toString(row)); // print after sorting `pairs`

        int chainLen = 1; // by default it has to be 1 (since even it has 1 pair the max length is 1)
        int chainEnd = pairs[0][1]; // chain end

        // O(n)
        for (int row = 1; row < pairs.length; row++) {
            if (pairs[row][0] > chainEnd) {
                // add to the `chainLen`
                chainLen++;
                // update chainEnd
                chainEnd = pairs[row][1];
            }
        }

        System.out.println("The maximum length of chain is = " + chainLen);


    }
}
