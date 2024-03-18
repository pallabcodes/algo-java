package queue;

/*
* Q: Given N ropes of different lengths, the task is to connect these ropes into one rope with minimum cost, such that cost to connect two ropes is equal to the sum of their lengths
* sample input 1: N = 4, arr = [4, 3, 2, 6]
* sample input 1: N = 2, arr = [1, 2, 3]
*
* */


import java.util.*;
public class ConnectRopes {

    // TC and SC = O(n)
    public static int minCost(int[] arr, int n) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i = 0; i < n; i++) {
            pq.add(arr[i]);
        }

        int res = 0;

        while (pq.size() > 1) {
            int first = pq.poll();
            @SuppressWarnings("DataFlowIssue") int second = pq.poll();

            res += first + second;
            pq.add(first + second);

        }

        return res;

    }
    public static void main(String[] args) {
        int[] len = {2, 3, 4, 6}; // length of each rope
        int size = len.length;

        System.out.println("Total cost for connecting rope is " + minCost(len, size));
        System.out.println("Total cost for connecting rope is " + minCost(new int[]{1, 2, 3}, 3));

    }
}
