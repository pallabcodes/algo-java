package greedy;

import java.util.*;

public class Coins {
    public static void main(String[] args) {
        int[] coins = {1, 2, 5, 10, 20, 50, 100, 500, 1000, 2000};
        int amount = 590;

        int countOfCoins = 0;
        ArrayList<Integer> ans = new ArrayList<>();
        int coinEnd = coins.length - 1, coinStart = 0;

        // 1. Sort array in DESC (to get the min exchanges needed)
        for (int i = 0; i < coins.length / 2; i++) {
            int temp = coins[i];
            coins[i] = coins[coins.length - 1 - i];
            coins[coins.length - 1 - i] = temp;
        }

        // 2. linear search on coins
        for (int i = 0; i < coins.length; i++) {
            // and whichever element is <=
            if(coins[i] <= amount) {
                // while loop could be based any value (doesn't necessarily need to be index-based always)
                while (coins[i] <= amount) {
                    countOfCoins++;
                    ans.add(coins[i]);
                    amount -= coins[i];
                }
            }
        }

        System.out.println("total (min) coins used = " + countOfCoins);

        for (int i = 0; i < ans.size(); i++) {
            System.out.println("coin/note = " + ans.get(i));
        }
    }
}
