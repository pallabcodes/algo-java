package recursion.dp.zeroOneKnapsack;

import java.util.Arrays;


public class WeightedJobScheduling {

    private static class Job {
        int start, finish, profit;

        Job(int start, int finish, int profit) {
            this.start = start;
            this.finish = finish;
            this.profit = profit;
        }
    }

    // Utility function to find the last non-conflicting job using binary search
    private static int binarySearchLastNonConflicting(Job[] jobs, int index) {
        int low = 0, high = index - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (jobs[mid].finish <= jobs[index].start) {
                if (jobs[mid + 1].finish <= jobs[index].start) {
                    low = mid + 1;
                } else {
                    return mid;
                }
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public static int findMaxProfit(Job[] jobs) {
        // Sort jobs according to finish time
        Arrays.sort(jobs, (a, b) -> a.finish - b.finish);

        int n = jobs.length;
        int[] dp = new int[n];
        dp[0] = jobs[0].profit;

        for (int i = 1; i < n; i++) {
            int inclProfit = jobs[i].profit;
            int l = binarySearchLastNonConflicting(jobs, i);
            if (l != -1) {
                inclProfit += dp[l];
            }
            dp[i] = Math.max(inclProfit, dp[i - 1]);
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        Job[] jobs = {
                new Job(1, 2, 50),
                new Job(3, 5, 20),
                new Job(6, 19, 100),
                new Job(2, 100, 200)
        };
        System.out.println("The maximum profit is " + findMaxProfit(jobs));
    }
}
