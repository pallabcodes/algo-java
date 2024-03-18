package queue;

import java.util.Stack;

public class Queue {
    ListNode left;  // front of Queue   front -> [1,2,3]
    ListNode right; // back of Queue   [1,2,3] <- back

    public Queue() {
        this.left = null;
        this.right = null;
    }

    public void enqueue(int val) {
        ListNode newNode = new ListNode(val);
        if (this.right != null) {
            // Queue is not empty
            this.right.next = newNode;
            this.right = this.right.next;
        } else {
            // Queue is empty
            this.left = newNode;
            this.right = newNode;
        }
    }

    public int dequeue() {
        if (this.left == null) {
            // Queue is empty
            System.exit(0);
        }
        int val = this.left.val;
        this.left = this.left.next;
        return val;

    }

    public void print() {
        ListNode cur = this.left;
        while (cur != null) {
            System.out.print(cur.val + " -> ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static class MakeQueue {

        private int[] data;

        private static final int DEFAULT_SIZE = 10;

        int end = 0;

        public MakeQueue(){
            this(DEFAULT_SIZE);
        }

        public MakeQueue(int size) {
            this.data = new int[size];
        }

        public boolean isFull() {
            return end == data.length; // ptr is at last index
        }

        public boolean isEmpty() {
            return end == 0;
        }

        public boolean insert(int item) {
            if (isFull()) {
                return false;
            }
            data[end++] = item;
            return true;
        }

        public int remove() throws Exception {
            if (isEmpty()) {
                throw new Exception("Queue is empty");
            }

            int removed = data[0];

            // shift the elements to left
            for (int i = 1; i < end; i++) {
                data[i-1] = data[i];
            }
            end--;
            return removed;
        }

        public int front() throws Exception{
            if (isEmpty()) {
                throw new Exception("Queue is empty");
            }
            return data[0];
        }

        public void display() {
            for (int i = 0; i < end; i++) {
                System.out.print(data[i] + " <- ");
            }
            System.out.println("END");
        }

    }

    // remove efficient
    public static class QueueByUsingStackR {
        private Stack<Integer> first = new Stack<>();
        private Stack<Integer> second = new Stack<>();

        public QueueByUsingStackR() {
            first = new Stack<>();
            second = new Stack<>();
        }

        public void add(int item) throws Exception {
            while (!first.isEmpty()) {
                second.push(first.pop());
            }
            first.push(item);

            while (!second.isEmpty()) {
                first.push(second.pop());
            }

        }

        public int remove() throws Exception {
            return first.pop();
        }

        public int peek() throws Exception {
            return first.peek();
        }

        public boolean isEmpty() {
            return first.isEmpty();
        }
    }

    public static class QueueMain {
        public static void main(String[] args) throws Exception {
            CircularQueue queue = new CircularQueue(5);
            queue.insert(3);
            queue.insert(6);
            queue.insert(5);
            queue.insert(19);
            queue.insert(1);

            queue.display();

            System.out.println(queue.remove());
            queue.insert(133);
            queue.display();

            System.out.println(queue.remove());
            queue.insert(99);
            queue.display();

        }
    }

    // insert efficient
    public static class QueueByUsingStack {
        private Stack<Integer> first = new Stack<>();
        private Stack<Integer> second = new Stack<>();

        public QueueByUsingStack() {
            first = new Stack<>();
            second = new Stack<>();
        }

        public void add(int item) {
            first.push(item);
        }

        public int remove() throws Exception {
            if(!first.isEmpty()) {
                second.push(first.pop());
            }
            int removed = second.pop();

            while (!second.isEmpty()) {
                first.push(second.pop());
            }
            return removed;
        }

        public int peek() throws Exception {
            while (!first.isEmpty()) {
                second.push(first.pop());
            }
            int peeked = second.peek();

            while (!second.isEmpty()) first.push(second.pop());
            return peeked;
        }

        public boolean isEmpty() {
            return first.isEmpty();
        }
    }
}