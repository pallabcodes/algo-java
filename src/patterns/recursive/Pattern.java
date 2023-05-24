package patterns.recursive;

/*
 *      c   0 1 2 3 4
 *      r   0
 *      r   1
 *      r   2
 *      r   3
 *      r   4
 *
 *      c  4 3 2 1 0
 *      r  4
 *      r  3
 *      r  2
 *      r  1
 *      r  0
 *
 * */

/*
 *      c   1 2 3 4
 *      r   1
 *      r   2
 *      r   3
 *      r   4
 *
 *      c  4 3 2 1
 *      r  4
 *      r  3
 *      r  2
 *      r  1
 *
 * */

/*
 * how to approach pattern?
 *
 * 1. no. of line = no. of the rows i.e. how many times outer loop will run
 * 2. now, for every row no : i) how many cols are there? ii) type of element within col
 * 3. what do you need to print?
 * NOTE: Try to find the relation between r & c (these could determine how r & c's index might be & how many to print ) e.g n = 4; c = n-r
 * */

public class Pattern {
    public static void main(String[] args) {
        // pattern1(5);
        // pattern2(5);
        // pattern3(5);
        // pattern4(5);
        // pattern5(5);
        // pattern28(5);
        // pattern17(5);
        pattern31(4);

        // pattern30(5);
        // patternN(5);

    }

    static void pattern1(int n) {
        for (int r = 1; r <= n; r++) {

            for (int c = 1; c <= n; c++) {
                System.out.print("* ");
            }

            // todo: do anything that needs to be done below "before it goes to next iteration"
            System.out.println();
        }

    }

    static void pattern2(int n) {
        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= r; c++) System.out.print("* ");
            // todo: do anything that needs to be done below "before it goes to next iteration"
            System.out.println();
        }

    }

    static void pattern3(int n) {
        for (int r = n; r >= 1; r--) {
            for (int c = 1; c <= r; c++) {
                System.out.print("* ");
            }
            System.out.println();

        }

    }

    static void pattern4(int n) {
        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= r; c++) {
                System.out.print(c + " ");
            }
            // todo: do anything that needs to be done below "before it goes to next iteration"
            System.out.println();
        }
    }

    static void pattern5(int n) {
        for (int r = 0; r < 2 * n; r++) {
            // to get one less element from row like when at then 5th row, get 4th i.e. 2 * n - r
            int totalColsInRow = r > n ? 2 * n - r : r; // this is needed to figure out how many cols should be and when?
            for (int c = 0; c < totalColsInRow; c++) {
                System.out.print("*" + " ");
            }
            // todo: do anything that needs to be done below "before it goes to next iteration"
            System.out.println();
        }
    }


    static void pattern28(int n) {
        for (int r = 0; r < 2 * n; r++) {
            // to get one less element from row like when at then 5th row, get 4th i.e. 2 * n - r
            int totalColsInRow = r > n ? 2 * n - r : r;

            // how many spaces should be?
            int noOfSpaces = n - totalColsInRow;

            // what are the available space for each row?
            for (int s = 0; s < noOfSpaces; s++) {
                System.out.print(" ");

            }

            // System.out.println(r); // with spacing added the elements has placed rightly

            // now how many of the elements would be that's made by this inner loop
            for (int c = 0; c < totalColsInRow; c++) {
                System.out.print("*" + " ");
            }

            // todo: do anything that needs to be done below "before it goes to next iteration"
            System.out.println();
        }
    }


    static void pattern17(int n) {
        for (int row = 1; row <= 2 * n; row++) {

            int c = row > n ? 2 * n - row : row;


            for (int space = 0; space < n - c; space++) {
                System.out.print("  ");
            }

            for (int col = c; col >= 1; col--) {
                System.out.print(col + " ");
            }

            for (int col = 2; col <= c; col++) {
                System.out.print(col + " ");
            }

            System.out.println();

        }
    }

    static void pattern30(int n) {
        for (int row = 1; row <= n; row++) {
            for (int space = 0; space < n - row; space++) {
                System.out.print("  ");
            }

            for (int col = row; col >= 1; col--) {
                System.out.print(col + " ");
            }

            for (int col = 2; col <= row; col++) {
                System.out.print(col + " ");
            }
            System.out.println();

        }
    }

    static void pattern31(int n) {
        int originalN = n;
        n = 2 * n;
        for (int row = 0; row <= n; row++) {
            for (int col = 0; col <= n; col++) {
                int atEveryIndex = originalN - Math.min(Math.min(row, col), Math.min(n - row, n - col));
                System.out.print(atEveryIndex + " ");
            }
            System.out.println();
        }
    }

    static void patternN(int n) {
        for (int r = n; r >= 1; r--) {
            for (int c = n; c >= r; c--) {
                // 5: 5
                // 4: 5 4
                // 3: 5 4 3
                // 2: 5 4 3 2
                // 1: 5 4 3 2 1
                System.out.print(c + " ");

            }
            System.out.println();
        }
    }


}
