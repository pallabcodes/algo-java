package greedy;

import java.util.*;

public class FractionalKnapsack {
    public static void main(String[] args) {
        int[] val = {60, 100, 120};
        int[] weight = {10, 20, 30};
        int W = 50;

        double[][] ratio = new double[val.length][2];
        // 0th col => index; 1st column => ratio

        // fill up ratio matrix with index and value: weight ratio
        for (int i = 0; i < val.length; i++) {
            ratio[i][0] = i;
            ratio[i][1] = val[i] / (double) weight[i];
        }

        // print ratio
        for (double[] row: ratio) {
            // for (double col: row) System.out.println(col);
            System.out.println("ROW " + Arrays.toString(row));
        }

        // now sort ratio matrix in an ascending order based on specific column (or columns), here o[1] ↓
        Arrays.sort(ratio, Comparator.comparingDouble(o -> o[1]));

        // print the sorted ratio
        for (double[] row: ratio) {
            System.out.println("ROW Sorted in ASC " + Arrays.toString(row));
        }

        int capacity = W;
        int result = 0;

        // now fill up the knapsack (i.e. result) based on the capacity `full` or `partially` ↓
        for (int i = ratio.length - 1; i >= 0; i--) {
            int idx = (int) ratio[i][0];
            if (capacity >= weight[idx]) { // include full item
                result += val[idx];
                capacity -= weight[idx];
            } else { // include fraction of the item
                result += (ratio[i][1] * capacity);
                capacity = 0;
                break;
            }
        }
        System.out.println("Result = " + result);
    }
}
