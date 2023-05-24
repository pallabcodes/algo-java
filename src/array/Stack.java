package array;

import java.util.ArrayList;

public class Stack {
    public static void main(String[] args) {

    }

    ArrayList<Integer> stack = new ArrayList<Integer>();

    public Stack() {
    }

    public void push(int n) {
        stack.add(n);
    }

    public int pop() {
        return stack.remove(stack.size() - 1);
    }

    public int size() {
        return stack.size();
    }


    // https://leetcode.com/problems/baseball-game/
}





