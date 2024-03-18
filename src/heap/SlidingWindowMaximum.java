package heap;

import java.util.PriorityQueue;

public class SlidingWindowMaximum {

    static class Pair implements Comparable<Pair> {
        int val;
        int idx;

        public Pair(int val, int idx) {
            this.val = val;
            this.idx = idx;
        }

        @Override
        public int compareTo(Pair pair) {
            // return this.val - pair.val; // ascending order
            return pair.val - this.val; // descending order
        }
    }

    public static void main(String[] args) { // O (n log k)
        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int n = arr.length;
        int k = 3; // window size
        int res[] = new int[(n - k) + 1]; // size of result array : (n - k) + 1

        PriorityQueue<Pair> pq = new PriorityQueue<>();

        // 1. store elements in PQ by window size i.e. k
        for (int i = 0; i < k; i++) pq.add(new Pair(arr[i], i));
        res[0] = pq.peek().val; // store the first value to res[0]

        for (int i = k; i < n; i++) {
            // to remove the first item if it belongs to previous window (not the current window) ↓
            while (pq.size() > 0 && pq.peek().idx <= (i - k)) {
                pq.remove();
            }

            // otherwise, if the first add belongs to this current window, then add
            pq.add(new Pair(arr[i], i));
            res[i - k + 1] = pq.peek().val; // window no.
        }

        // print res
        for (int data : res) {
            System.out.print(data + " ");
        }

    }
}
