package recursion.dp;

import java.util.*;

// DP category = Optimization on Grids
public class MaxSubMatrix {
    // Function to find the largest area rectangular sub-matrix with equal number of 1's and 0's
    static int maxSubMatrix(int[][] mat) {
        int row = mat.length;
        if (row == 0) return 0;
        int col = mat[0].length;

        // Replace 0's with -1's
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                if (mat[i][j] == 0) mat[i][j] = -1;

        int maxArea = 0;

        // Temp array to store results of 1-D array
        int[] temp = new int[row];

        // Consider every pair of columns
        for (int left = 0; left < col; left++) {
            // Initialize temp as 0
            for (int i = 0; i < row; i++) temp[i] = 0;

            for (int right = left; right < col; right++) {
                // Calculate sum between current left and right for every row 'i'
                for (int i = 0; i < row; i++) temp[i] += mat[i][right];

                // Find the largest subarray with 0 sum in temp[]
                maxArea = Math.max(maxArea, largestSubArrWithZeroSum(temp) * (right - left + 1));
            }
        }

        return maxArea;
    }

    // Utility function to find the largest subarray with 0 sum
    static int largestSubArrWithZeroSum(int arr[]) {
        // Map to store the previous sums
        HashMap<Integer, Integer> sumMap = new HashMap<>();
        int sum = 0; // Initialize sum of elements
        int max_length = 0; // Initialize result

        // Traverse through the given array
        for (int i = 0; i < arr.length; i++) {
            // Add current element to sum
            sum += arr[i];

            if (arr[i] == 0 && max_length == 0) max_length = 1;
            if (sum == 0) max_length = i + 1;

            // Look for this sum in Hash table
            Integer prev_i = sumMap.get(sum);

            // If this sum is seen before, then update max_len
            if (prev_i != null) max_length = Math.max(max_length, i - prev_i);
            else sumMap.put(sum, i); // Else put this sum in hash table
        }

        return max_length;
    }

    public static void main(String[] args) {
        int mat[][] = {
                {0, 0, 1, 1},
                {0, 1, 1, 0},
                {1, 1, 1, 0},
                {1, 0, 0, 1}
        };
        System.out.println("Largest area rectangular sub-matrix with equal number of 1's and 0's: " + maxSubMatrix(mat) + " sq. units");

        int mat2[][] = {
                {0, 0, 1, 1},
                {0, 1, 1, 1}
        };
        System.out.println("Largest area rectangular sub-matrix with equal number of 1's and 0's: " + maxSubMatrix(mat2) + " sq. units");
    }

}
