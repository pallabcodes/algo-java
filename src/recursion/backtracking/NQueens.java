package recursion.backtracking;

public class NQueens {

  static int count = 0;

  private static void nQueens(char[][] board, int row) { // T = O(n!), S = O(n^2)

    // base case
    if (row == board.length) {
      printBoard(board); // all ways to place queen
      count++;
      return;

    }

    // column loop
    for (int j = 0; j < board.length; j++) {
      if (isSafe(board, row, j)) {
        board[row][j] = 'Q'; // place
        nQueens(board, row + 1); // recursive call
        board[row][j] = 'x'; // undo/un-place: backtracking (removing previously occupied place)

      }
    }
  }

  private static boolean nQueensBool(char[][] board, int row) {
    // base case
    if (row == board.length) {
      count++;
      return true;

    }

    // column loop
    for (int j = 0; j < board.length; j++) {
      if (isSafe(board, row, j)) {
        board[row][j] = 'Q';
        if (nQueensBool(board, row + 1)) {
          return true;

        }
        board[row][j] = 'x'; // backtracking step (removing or emptying previously occupied place by 'Q')
      }
    }

    // in case no valid answer found then
    return false;
  }

  private static boolean isSafe(char[][] board, int row, int col) {

    // vertical up
    for (int i = row; i >= 0; i--) {
      if (board[i][col] == 'Q') {
        return false;
      }
    }

    // diagonal left-up

    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
      if (board[i][j] == 'Q') {
        return false;
      }
    }

    // diagonal right-up
    for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
      if (board[i][j] == 'Q') {
        return false;
      }
    }

    return true;

  }

  private static void printBoard(char[][] board) {
    System.out.println("-------- chess board");
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static void solveNQueens(int n) { // T = O(n!), S = O(n)
    boolean[] cols = new boolean[n]; // Columns where queens are placed
    boolean[] diag1 = new boolean[2 * n]; // Diagonal 1
    boolean[] diag2 = new boolean[2 * n]; // Diagonal 2
    int[] board = new int[n]; // The row index represents the row, and the value represents the column
    solve(0, cols, diag1, diag2, board, n);
  }

  private static void solve(int row, boolean[] cols, boolean[] diag1, boolean[] diag2, int[] board, int n) {
    if (row == n) {
      printBoard(board, n);
      return;
    }
    for (int col = 0; col < n; col++) {
      if (!cols[col] && !diag1[row - col + n] && !diag2[row + col]) {
        board[row] = col;
        cols[col] = diag1[row - col + n] = diag2[row + col] = true;
        solve(row + 1, cols, diag1, diag2, board, n);
        cols[col] = diag1[row - col + n] = diag2[row + col] = false; // Backtrack
      }
    }
  }

  private static void printBoard(int[] board, int n) {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print((board[i] == j ? "Q" : "x") + " ");
      }
      System.out.println();
    }
    System.out.println("-------- chess board");
  }

  public static void main(String[] args) {
    int n = 4; // also check with n = 5 and 2
    char board[][] = new char[n][n];

    // initialize board
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        board[i][j] = 'x';
      }
    }

    if (nQueensBool(board, 0)) { // decision: to check whether it has a valid ans if yes print first ans ↓
      System.out.println("solution is possible ");
      printBoard(board);
    } else {
      System.out.println("solution is not found");
    }

    nQueens(board, 0);
    System.out.println("Total ways to solve n queens = " + count); // enumerations (all paths, total counts)

    System.out.println("Alternative: below");
    solveNQueens(n);
  }
}
