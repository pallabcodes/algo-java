package recursion.backtracking;

public class UniquePathsIII {
    private static int uniquePathsIII(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int nonObstacleCells = 0, startX = 0, startY = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) nonObstacleCells++;
                else if (grid[i][j] == 1) {
                    startX = i;
                    startY = j;
                }
            }
        }
        return dfs(grid, startX, startY, nonObstacleCells);
    }

    private static int dfs(int[][] grid, int x, int y, int remaining) {
        int m = grid.length, n = grid[0].length;
        if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] == -1) return 0;
        if (grid[x][y] == 2) return remaining == -1 ? 1 : 0;

        grid[x][y] = -1; // Mark the cell as visited
        int paths = 0;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for (int d = 0; d < 4; d++) {
            paths += dfs(grid, x + dx[d], y + dy[d], remaining - 1);
        }

        grid[x][y] = 0; // Backtrack and mark the cell as unvisited
        return paths;
    }

    public static void main(String[] args) {
        int[][] grid1 = {{1,0,0,0},{0,0,0,0},{0,0,2,-1}};
        System.out.println(uniquePathsIII(grid1)); // Output: 2

        int[][] grid2 = {{1,0,0,0},{0,0,0,0},{0,0,0,2}};
        System.out.println(uniquePathsIII(grid2)); // Output: 4

        int[][] grid3 = {{0,1},{2,0}};
        System.out.println(uniquePathsIII(grid3)); // Output: 0
    }
}
