package array.stacks;

import java.util.Stack;

public class InBuiltExampleForStack {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(34);
        stack.push(35);
        stack.push(36);
        stack.push(37);
        stack.push(38);


        System.out.println("removed: " + stack.pop());; // remove the latest / last item


        System.out.println(stack);
    }
}
