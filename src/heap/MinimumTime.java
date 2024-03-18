package heap;

import java.util.*;

// find the minimum time required to visit all positions in a given range [1, N]
public class MinimumTime {
    public static void minTime(int[] arr, int N, int K) { // TC and SC O(n)
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[N + 1];
        int time = 0;

        for (int i = 0; i < K; i++) {
            queue.add(arr[i]);
            visited[arr[i]] = true;
        }

        while (!queue.isEmpty()) {
            int size = queue.size(); // Number of elements to process in this wave
            for (int i = 0; i < size; i++) {
                int curr = queue.poll(); // to retrieve and remove the first element from queue & save it to curr
                // check add add neighbours if not visited
                if (curr - 1 >= 1 && !visited[curr - 1]) {
                    visited[curr - 1] = true;
                    queue.add(curr - 1);
                }
                if(curr + 1 <= N && !visited[curr + 1]) {
                    visited[curr + 1] = true;
                    queue.add(curr + 1);
                }
            }
            time++;
        }
        System.out.println(time - 1); // Corrected to account for the final increment
    }

    public static void main(String[] args) {
        int N = 6;
        int[] arr = {2, 6};
        int K = arr.length;
        minTime(arr, N, K);
    }
}
