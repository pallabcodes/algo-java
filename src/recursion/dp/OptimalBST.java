package recursion.dp;

// DP category: Optimization DP (or also known as Optimal BST)

public class OptimalBST {

    // Main function to find the minimum cost of all searches
    public static int optimalSearchTree(int keys[], int freq[], int n) {
        // Create a cost matrix to store the cost of optimal BSTs
        int[][] cost = new int[n][n];

        // For a single key, cost is equal to its frequency
        for (int i = 0; i < n; i++) {
            cost[i][i] = freq[i];
        }

        // L is the chain length.
        for (int L = 2; L <= n; L++) {
            // i is the row number in cost[][]
            for (int i = 0; i <= n - L; i++) { // Corrected bounds for i
                // Get column number j from row number i and chain length L
                int j = i + L - 1;
                cost[i][j] = Integer.MAX_VALUE;

                // Try making all keys in interval keys[i..j] as root
                for (int r = i; r <= j; r++) {
                    // c = cost when keys[r] becomes root of this subtree
                    int costLeftSubtree = (r > i) ? cost[i][r - 1] : 0;
                    int costRightSubtree = (r < j) ? cost[r + 1][j] : 0;
                    int costCurrent = costLeftSubtree + costRightSubtree;

                    // Calculate sum of frequencies dynamically
                    int sumFreq = 0;
                    for (int f = i; f <= j; f++) {
                        sumFreq += freq[f];
                    }

                    int c = costCurrent + sumFreq;
                    if (c < cost[i][j]) {
                        cost[i][j] = c;
                    }
                }
            }
        }

        // Return the minimum cost for keys[0..n-1]
        return cost[0][n-1];
    }

    public static void main(String[] args) {
        int keys[] = {10, 12, 20};
        int freq[] = {34, 8, 50};
        int n = keys.length;
        System.out.println("Cost of Optimal BST is " + optimalSearchTree(keys, freq, n));
    }
}
