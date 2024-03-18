package queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class DynamicQueue extends CircularQueue {

    public DynamicQueue() {
        super();
    }

    public DynamicQueue(int size) {
        super(size);
    }

    @Override
    public boolean insert(int item) {

        // this takes care of it being full
        if (this.isFull()) {
            // double the array size
            int[] temp = new int[data.length * 2];

            // copy all previous items in new data
            for (int i = 0; i < data.length; i++) {
                temp[i] = data[(front + i) % data.length];
            }
            front = 0;
            end = data.length;
            data = temp;
        }

        // at this point we know that array is not full
        // insert item
        return super.insert(item);
    }

    public static class InBuiltExamplesForQueue {
        public static void main(String[] args) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(4);
            queue.add(3);
            queue.add(2);
            queue.add(1);

            // System.out.println(queue.remove()); // this removes the first element i.e. 4
            System.out.println(queue);
            System.out.println(queue.peek()); // peek at the current first element i.e. 3

            Deque<Integer> deque = new ArrayDeque<>();
            deque.add(89);
            deque.addLast(78);
            System.out.println(deque);
            deque.removeFirst();
        }

    }
}
