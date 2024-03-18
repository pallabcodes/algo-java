package recursion.backtracking;

// https://www.geeksforgeeks.org/problems/m-coloring-problem-1587115620/1
public class GraphColoring {
    // Function to check if the current color assignment is safe for vertex v
    private boolean isSafe(int v, int[][] graph, int[] color, int c) {
        for (int i = 0; i < graph.length; i++) {
            if (graph[v][i] == 1 && c == color[i]) {
                return false;
            }
        }
        return true;
    }

    // Recursive utility function to solve m coloring problem
    private boolean graphColoringUtil(int[][] graph, int m, int[] color, int v) {
        // If all vertices are assigned a color then return true
        if (v == graph.length) {
            return true;
        }

        // Try different colors for vertex v
        for (int c = 1; c <= m; c++) {
            // Check if assignment of color c to v is fine
            if (isSafe(v, graph, color, c)) {
                color[v] = c;

                // Recur to assign colors to rest of the vertices
                if (graphColoringUtil(graph, m, color, v + 1)) {
                    return true;
                }

                // If assigning color c doesn't lead to a solution then remove it
                color[v] = 0;
            }
        }

        // If no color can be assigned to this vertex then return false
        return false;
    }

    public boolean graphColoring(int[][] graph, int m) {
        // Initialize all color values as 0. This initialization is needed
        // correct functioning of isSafe()
        int[] color = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            color[i] = 0;
        }

        // Call graphColoringUtil() for vertex 0
        if (!graphColoringUtil(graph, m, color, 0)) {
            return false; // If no coloring exists
        }

        // Print the solution
        // printSolution(color);
        return true;
    }

    // Function to print solution
    void printSolution(int color[]) {
        System.out.println("Solution Exists: Following are the assigned colors");
        for (int i = 0; i < color.length; i++) {
            System.out.print(" " + color[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        GraphColoring solution = new GraphColoring();
        int[][] graph = {
                {0, 1, 1, 1},
                {1, 0, 1, 0},
                {1, 1, 0, 1},
                {1, 0, 1, 0},
        };
        int m = 3; // Number of colors
        System.out.println(solution.graphColoring(graph, m) ? 1 : 0); // Output: 1

        int[][] graph2 = {
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0},
        };
        int m2 = 2;
        System.out.println(solution.graphColoring(graph2, m2) ? 1 : 0); // Output: 0
    }
}
