package stack;

import java.util.*;

public class StackB {
    private static class Stack {
        static ArrayList<Integer> list = new ArrayList<>();

        private static boolean isEmpty() {
            return list.size() == 0;
        }

        // push
        private static void push(int data) {
            list.add(data);
        }

        // pop

        private static int pop() {
            if (list.isEmpty()) return -1;
            int top = list.get(list.size() - 1);
            list.remove(list.size() - 1);
            return top;
        }

        // peek

        private static int peek() {
            if (list.isEmpty()) return -1;
            return list.get(list.size() - 1);
        }
    }

    public static void main(String[] args) {
        Stack s = new Stack();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);

        while (!s.isEmpty()) {
            System.out.println(s.peek());
            s.pop();
        }

        System.out.println("popped " + s.pop());
        System.out.println("peeked " + s.peek());

        // stack using collection framework -> Stack<Integer> stack = new Stack<>();
    }
}
