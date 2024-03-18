package heap;

import java.util.*;

public class WeakestSoldier {
    public static class Row implements Comparable<Row> {
        int soldiers;
        int idx;

        public Row(int soldiers, int idx) {
            this.soldiers = soldiers;
            this.idx = idx;
        }

        @Override
        public int compareTo(Row r2) {
            // this is setting priority & it'll decide when doing pq.add() whether it will build min / max heap
            if (this.soldiers == r2.soldiers) {
                return this.idx - r2.idx; // ascending order
            } else {
                return this.soldiers - r2.soldiers; // ascending order
            }
        }
    }

    public static void main(String[] args) {
        int[][] army = {
                {1, 0, 0, 0},
                {1, 1, 1, 1},
                {1, 0, 0, 0},
                {1, 0, 0, 0},
        }; // m (row) = 4; c (cols) = 4 (This army array can be called "binary matrix" since it has 0 and 1)

        int k = 2; // if k = 4 or 5 then it would be either meaningless/wrong thus k < m

        PriorityQueue<Row> pq = new PriorityQueue<>();

        for (int row = 0; row < army.length; row++) {
            int count = 0;
            for (int col = 0; col < army[row].length; col++) {
                count += army[row][col] == 1 ? 1 : 0;
            }
            pq.add(new Row(count, row));
        }
        for (int i = 0; i < k; i++) {
            System.out.println("R" + pq.remove().idx);

        }
    }
}
