package array;

// 1d array has a single row and n no. of columns, new int[] {1, 2, 3, 4} = here it 1 row and 4 columns
// [ 0: [10, 20], 1: [40, 50] ] : 2 rows 2 columns; for(int col = 0; col < row.length; col++) { arr[row][col] }
public class ColNoFixed {

    public static void main(String[] args) {
        int[][] arr = {{1, 2, 3, 4}, {5, 6}, {7, 8, 9}}; // here this 2d array has 3 rows with no fixed columns

        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[row].length; col++) {
                System.out.print(arr[row][col] + " ");
            }
            System.out.println();
        }
    }
}
