package recursion.backtracking;

public class GridWays {
  private static int gridWays(int i, int j, int n, int m) { // T (Exponential time) = O(2^n)
    if (i == n - 1 && j == m - 1) { // it means arrived at the last cell row-wise and col-wise
      return 1;
    } else if (i == n || j == n) { // it means row-wise or col-wise gotten out of the bound grid then
      return 0;

    }

    return gridWays(i + 1, j, n, m) + gridWays(i, j + 1, n, m);

  }

  private static int binomialCoefficient(int n, int k) {
    int res = 1;

    // Since C(n, k) = C(n, n-k)
    if (k > n - k) {
      k = n - k;
    }

    // Calculate value of [n*(n-1)*---*(n-k+1)] / [k*(k-1)*---*1]
    for (int i = 0; i < k; ++i) {
      res *= (n - i);
      res /= (i + 1);
    }

    return res;
  }

  private static int gridWaysLinear(int n, int m) { // O(min(n-1, m - 1)), O(1)
    // The total number of moves to the bottom right corner is (n-1) down and (m-1)
    // right.
    // The total ways to arrange these moves are the binomial coefficient of (n+m-2)
    // choose (n-1).
    return binomialCoefficient(n + m - 2, n - 1);
  }

  public static void main(String[] args) {
    int n = 3, m = 3; // row(n) = 3, m (col) = 3

    // total cells in a matrix / 2d array = (n + m) so here 6

    int ans = gridWays(0, 0, n, m); // starting row = 0, starting col = 0, end row = 3, end column = 3

    System.out.println("answer = " + ans);

    int ans2 = gridWaysLinear(n, m);
    System.out.println("answer = " + ans2);

  }

}
