package stacks;

import java.util.Stack;

// insert efficient
public class QueueByUsingStack {
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


