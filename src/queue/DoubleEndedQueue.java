package queue;

import java.util.*;

// Deque is part of Queue and it is a data structure whereas `"dequeue" means removing from Queue`: both different
public class DoubleEndedQueue {

    public static class MyStack {
        Deque<Integer> deque = new LinkedList<>();

        public void push(int data) {
            deque.addLast(data);
        }

        public int pop() {
            return deque.removeLast();
        }

        public int peek() {
            return deque.getLast();
        }
    }
    public static class MyQueue {
        Deque<Integer> deque = new LinkedList<>();

        // add (or enqueue) always add from the back
        public void add(int data) {
            deque.addLast(data);
        }

        // dequeue (remove the front value)
        public int remove() {
            return deque.removeFirst();
        }

        public int peek() {
            return deque.getFirst();
        }
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        System.out.println("peek " + myStack.peek());
        System.out.println();
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println();

        MyQueue myQueue = new MyQueue();
        myQueue.add(4);
        myQueue.add(5);
        myQueue.add(6);
        System.out.println("peek " + myQueue.peek());
        System.out.println();
        System.out.println(myQueue.remove());
        System.out.println(myQueue.remove());
        System.out.println(myQueue.remove());

    }

}
