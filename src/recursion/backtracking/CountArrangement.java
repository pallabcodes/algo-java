package recursion.backtracking;

public class CountArrangement {
    private int count = 0;

    public int countArrangement(int n) {
        // Reset count to 0 for each call to handle multiple calls correctly
        count = 0;
        boolean[] visited = new boolean[n + 1];
        backtrack(n, 1, visited);
        return count;
    }

    private void backtrack(int n, int pos, boolean[] visited) {
        if (pos > n) {
            count++;
            return;
        }

        for (int i = 1; i <= n; i++) {
            if (!visited[i] && (i % pos == 0 || pos % i == 0)) {
                visited[i] = true;
                backtrack(n, pos + 1, visited);
                visited[i] = false; // Backtrack
            }
        }
    }

    public static void main(String[] args) {
        CountArrangement solution = new CountArrangement();
        System.out.println(solution.countArrangement(2)); // Output: 2
        System.out.println(solution.countArrangement(1)); // Output: 1
    }
}
