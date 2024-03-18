package recursion.backtracking;

public class Sudoku {
    private static boolean sudokuSolver(int[][] sudoku, int row, int col) {

        // base case
        if (row == 9 && col == 0) {
            return true;
        }

        // recursion

        // determine the "correct range" to provide within recursive calls
        int nextRow = row, nextCol = col + 1;

        if (col + 1 == 9) { // iterated through all available cols so now go to next row & reset col
            nextRow = row + 1;
            nextCol = 0;
        }

        // secondary base case
        if (sudoku[row][col] != 0) { // a value is already placed so just go next col & return its boolean value
            return sudokuSolver(sudoku, nextRow, nextCol);

        }

        /*
         * since sudoku board is always 9 * 9 thus hard coded 9 below
         * Q: but why looped from 1 - 9 ?
         * Answer: Because, a sudoku board only accept value from 1 - 9 which is why
         * then check `isSafe`
         *
         */

        for (int digit = 1; digit <= 9; digit++) {

            if (isSafe(sudoku, row, col, digit)) {

                sudoku[row][col] = digit; // place

                if (sudokuSolver(sudoku, nextRow, nextCol)) { // recursive call (solution exist)
                    return true;
                }

                sudoku[row][col] = 0; // backtracking step (i.e. un-place or undo exact previous action)

            }
        }

        return false;
    }

    private static boolean isSafe(int[][] sudoku, int row, int col, int digit) {

        // can I use given `digit` row-wise?
        for (int i = 0; i < sudoku.length; i++) {
            if (sudoku[row][i] == digit) {
                return false;
            }
        }

        // can I use given `digit` col-wise?
        for (int j = 0; j < sudoku.length; j++) {
            if (sudoku[j][col] == digit) {
                return false;
            }

        }

        // check the 3 * 3 sub-grid
        int sr = (row / 3) * 3; // starting row
        int sc = (col / 3) * 3; // starting col

        // 3 * 3 sub grid: check whether given digit already exist or not
        for (int i = sr; i < sr + 3; i++) {
            for (int j = sc; j < sc + 3; j++) {
                if (sudoku[i][j] == digit) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void printSudoku(int[][] sudoku) {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        // Sudoku is a 9*9 grid (2d array) where a placed can't be same as in its own
        // sub-grid, row-wise or col-wise
        int sudoku[][] = {
                {0, 0, 8, 0, 0, 0, 0, 0, 0},
                {4, 9, 0, 1, 5, 7, 0, 0, 2},
                {0, 0, 3, 0, 0, 4, 1, 9, 0},
                {1, 8, 5, 0, 6, 0, 0, 2, 0},
                {0, 0, 0, 0, 2, 0, 0, 6, 0},
                {9, 6, 0, 4, 0, 5, 3, 0, 0},
                {0, 3, 0, 0, 7, 2, 0, 0, 4},
                {0, 4, 9, 0, 3, 0, 0, 5, 7},
                {8, 2, 7, 0, 0, 9, 0, 1, 3}
        };

        if (sudokuSolver(sudoku, 0, 0)) {
            System.out.println("solution exists");
            printSudoku(sudoku);
        } else {
            System.out.println("solution does not exist");
        }

    }
}
