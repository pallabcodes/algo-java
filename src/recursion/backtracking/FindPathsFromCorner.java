package recursion.backtracking;

import java.util.*;


// https://www.geeksforgeeks.org/find-paths-from-corner-cell-to-middle-cell-in-maze/
public class FindPathsFromCorner {
    private static final int[] rowOffsets = {-1, 1, 0, 0};
    private static final int[] colOffsets = {0, 0, -1, 1};
    private static final String[] directions = {"N", "S", "W", "E"};

    public static List<String> findPaths(int[][] maze) {
        List<String> result = new ArrayList<>();
        int n = maze.length;
        int middle = n / 2;

        // Try starting from each of the four corners
        explore(maze, 0, 0, middle, new ArrayList<>(), result);
        explore(maze, 0, n - 1, middle, new ArrayList<>(), result);
        explore(maze, n - 1, 0, middle, new ArrayList<>(), result);
        explore(maze, n - 1, n - 1, middle, new ArrayList<>(), result);

        return result;
    }

    private static void explore(int[][] maze, int i, int j, int middle, List<String> path, List<String> result) {
        int n = maze.length;
        if (i < 0 || i >= n || j < 0 || j >= n || maze[i][j] == -1) {
            return; // Out of bounds or already visited
        }

        path.add("(" + i + ", " + j + ")");

        if (i == middle && j == middle) {
            result.addAll(new ArrayList<>(path)); // Found a path to the middle
            path.remove(path.size() - 1); // Backtrack
            return;
        }

        int steps = maze[i][j];
        maze[i][j] = -1; // Mark as visited

        // Try all four directions
        for (int dir = 0; dir < 4; dir++) {
            int nextI = i + steps * rowOffsets[dir];
            int nextJ = j + steps * colOffsets[dir];
            explore(maze, nextI, nextJ, middle, path, result);
        }

        maze[i][j] = steps; // Unmark as visited (backtrack)
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        int[][] maze = {
                {3, 5, 4, 4, 7, 3, 4, 6, 3},
                {6, 7, 5, 6, 6, 2, 6, 6, 2},
                {3, 3, 4, 3, 2, 5, 4, 7, 2},
                {6, 5, 5, 1, 2, 3, 6, 5, 6},
                {3, 3, 4, 3, 0, 1, 4, 3, 4},
                {3, 5, 4, 3, 2, 2, 3, 3, 5},
                {3, 5, 4, 3, 2, 6, 4, 4, 3},
                {3, 5, 1, 3, 7, 5, 3, 6, 4},
                {6, 2, 4, 3, 4, 5, 4, 5, 1}
        };

        List<String> result = findPaths(maze);
        for (String step : result) {
            System.out.print(step + " -> ");
        }
        System.out.println("MID");
    }
}
