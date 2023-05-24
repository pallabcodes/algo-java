package stacks;

import java.util.Stack;

// remove efficient
public class QueueByUsingStackR {
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


