package stacks;


import java.util.*;

public class InBuiltExamplesForQueue {
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
