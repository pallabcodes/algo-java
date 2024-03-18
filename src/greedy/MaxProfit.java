package greedy;

public class MaxProfit {

    // T = O(n) S = O(1)
    private static int maxProfit(int[] prices, int n) {
        int buy = prices[0], max_profit = 0;
        for (int i = 1; i < n; i++) {
            if (buy > prices[i]) {
                buy = prices[i];
            } else if (prices[i] - buy > max_profit) {
                max_profit = prices[i] - buy;
            }
        }
        return max_profit;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}, 6));
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}, 5));
    }
}
