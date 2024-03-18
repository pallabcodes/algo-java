package queue;

import java.util.*;

public class Main {
    public static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public static class Queue {
        int[] arr;
        int size;

        int back;

        int front;

        Queue(int n) {
            arr = new int[n];
            size = n;
            back = -1; /* this initial value -1 means arr is empty */
            front = -1; /* this property needed to circular queue */
        }

        public boolean isEmpty() {
            return back == -1;
        }

        public boolean isEmptyForCircularQ() {
            return back == -1 && front == -1;
        }

        public boolean isFullForCircularQ() {
            return (back + 1) % size == front;

        }

        // add = O(1)
        public void add(int data) {
            // since array is fixed size so if it's already full, so then can't add any new value
            if (back == size - 1) {
                System.out.println("Queue is now full");
                return;
            }

            back = back + 1;
            arr[back] = data;
        }

        public void addForCircularQ(int data) {
            if (isFullForCircularQ()) {
                System.out.println("Queue is now full");
                return;
            }

            // adding the 1st element
            if (front == -1) front = 0;

            back = (back + 1) % size;
            arr[back] = data;
        }

        // remove O(n)
        public int remove() {
            if (isEmpty()) {
                System.out.println("Queue is empty right now");
                return -1; // once again, this -1 means the arr is empty
            }

            int front = arr[0];

            // N.B: front will be always 0th index this shift all value at right to left

            for (int i = 0; i < back; i++) arr[i] = arr[i + 1];

            back = back - 1;

            return front;
        }

        // remove (for the circular queue) O(1)
        public int removeForCircularQ() {
            if (isEmpty()) {
                System.out.println("Queue is empty right now");
                return -1;
            }

            int result = arr[front];


            // last element delete

            if (back == front) {
                back = front = -1;
            } else {
                // update front
                front = (front + 1) % size;
            }


            return result;
        }

        // peek = O(1)
        public int peek() {
            if (isEmpty()) {
                System.out.println("Queue is empty right now");
                return -1; // once again, this -1 means the arr is empty
            }

            return arr[0];
        }

        public int peekForCircularQ() {
            if (isEmpty()) {
                System.out.println("Queue is empty right now");
                return -1; // once again, this -1 means the arr is empty
            }

            return arr[front];
        }

    }

    // Queue implementation using LinkedList
    public static class QueueL {
        Node head = null;
        Node tail = null;

        public boolean isEmpty() {
            return head == null && tail == null;
        }

        // N.B: LinkedList has no fixed size thus no need for a method like `isFull`


        // add = O(1)
        public void add(int data) {
            Node newNode = new Node(data);

            if (head == null) {
                head = tail = newNode;
                return;
            }

            tail.next = newNode;
            tail = newNode;
        }


        // remove O(1)
        public int remove() {
            if (isEmpty()) {
                System.out.println("Queue is empty right now");
                return -1;
            }

            int front = head.data;

            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next; /* existing head will automatically garbage collected */
            }

            return front;

        }


        // peek = O(1)
        public int peek() {
            if (isEmpty()) {
                System.out.println("Queue is empty right now");
                return -1;
            }

            return head.data;
        }
    }

    // Queue implementation using 2 stacks
    public static class QueueSS {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();

        public boolean isEmpty() {
            return s1.isEmpty();
        }

        // add O(2n) => O(n)
        public void add(int data) {
            // O(n)
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }

            // O(1)
            s1.push(data);

            // O(n)
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }

        // remove O(1)
        public int remove() {
            if (isEmpty()) {
                System.out.println("queue empty");
                return -1;
            }

            return s1.pop();
        }

        // peek O(1)
        public int peek() {
            if (isEmpty()) {
                System.out.println("queue empty");
                return -1;
            }
            return s1.peek();
        }


    }

    public static class StackQQ {
        java.util.Queue<Integer> q1 = new LinkedList<>();
        java.util.Queue<Integer> q2 = new LinkedList<>();

        public boolean isEmpty() {
            return q1.isEmpty() && q2.isEmpty();
        }

        // O(1)
        public void push(int data) {
            if (!q1.isEmpty()) {
                q1.add(data);
            } else {
                q2.add(data);
            }
        }

        // O(n)
        public int pop() {
            if (isEmpty()) {
                System.out.println("queue empty");
                return -1;
            }

            int top = -1;

            if (!q1.isEmpty()) {
                do {
                    top = q1.remove();
                    if (q1.isEmpty()) break;
                    q2.add(top);
                } while (!q1.isEmpty());

            } else {
                do {
                    top = q2.remove();
                    if (q2.isEmpty()) break;
                    q1.add(top);
                } while (!q2.isEmpty());


            }

            return top;
        }

        // O(n)
        public int peek() {
            if (isEmpty()) return -1;

            int top = -1;

            if (!q1.isEmpty()) {
                do {
                    top = q1.remove();
                    q2.add(top);
                } while (!q1.isEmpty());

            } else {
                do {
                    top = q2.remove();
                    q1.add(top);
                } while (!q2.isEmpty());


            }

            return top;

        }
    }


    public static void main(String[] args) {

        // Queue implementation using array
        Queue q = new Queue(5);
        q.add(1);
        q.add(2);
        q.add(3);

//        while (!q.isEmpty()) {
//            System.out.println(q.peek());
//            q.remove();
//        }

        // Circular Queue implementation using array
        Queue queue = new Queue(10);
        queue.addForCircularQ(1);
        queue.addForCircularQ(2);
        queue.addForCircularQ(3);
        System.out.println(queue.removeForCircularQ());
        queue.addForCircularQ(4);
        System.out.println(queue.removeForCircularQ());
        queue.addForCircularQ(5);
        System.out.println();


//        while (!queue.isEmptyForCircularQ()) {
//            System.out.println(queue.peekForCircularQ());
//            queue.removeForCircularQ();
//        }

        // Queue implementation using LinkedList

        // Queue implementation using LinkedList
        QueueL ql = new QueueL();
        ql.add(10);
        ql.add(20);
        ql.add(30);

        while (!ql.isEmpty()) {
            System.out.println(ql.peek());
            ql.remove();
        }
        System.out.println();

        // Queue implementation using LinkedList (but using Java collection framework)

        // N.B: Queue is an interface and 2 classes which implements that are 1) LinkedList 2) ArrayDeque
        java.util.Queue<Integer> queue1 = new LinkedList<Integer>();
        // java.util.Queue<Integer> queue1 = new ArrayDeque<Integer>();
        queue1.add(4);
        queue1.add(5);
        queue1.add(6);

        while (!queue1.isEmpty()) {
            System.out.println(queue1.peek());

            // queue1.poll(); // retrieves and removes the head of the queue

            queue1.remove();
        }
        System.out.println();

        // Queue implementation using 2 stacks
        QueueSS queueSS = new QueueSS();
        queueSS.add(1);
        queueSS.add(2);
        queueSS.add(3);

        while (!queueSS.isEmpty()) {
            System.out.println(queueSS.peek());
            queueSS.remove();
        }
        System.out.println();

        // stack implementation using 2 queues
        Stack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);

        while (!s.isEmpty()) {
            System.out.println(s.peek());
            s.pop();
        }
        System.out.println();
    }
}
