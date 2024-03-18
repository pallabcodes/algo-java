package heap;

import java.util.PriorityQueue;

public class ConnectRopes {
    public static void main(String[] args) {
        int[] ropes = {2, 3, 3, 4, 6};

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int rope : ropes) pq.add(rope);

        int cost = 0;
        while (pq.size() > 1) {
            int min = pq.remove();
            int secondMin = pq.remove();
            cost += min + secondMin;
            pq.add(min + secondMin); // as used "add", pq will make the Min. Heap again which takes O(log n) times
        }
        System.out.println("cost of connecting n ropes= " + cost);
    }
}
