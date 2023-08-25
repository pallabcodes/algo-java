package math.bitwise;

import java.util.Arrays;

// // https://leetcode.com/problems/flipping-an-image
public class FlipImage {
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(
                new FlipImage().flipAndInvertImage(new int[][] { { 1, 1, 0 }, { 1, 0, 1 }, { 0, 0, 0 } })));

    }

    public int[][] flipAndInvertImage(int[][] image) {
        for (int[] row : image) {
            // reverse this array
            for (int i = 0; i < (image[0].length + 1) / 2; i++) {
                // swap
                int temp = row[i] ^ 1;
                row[i] = row[image[0].length - i - 1] ^ 1;
                row[image[0].length - i - 1] = temp;
            }
        }
        return image;
    }
}
