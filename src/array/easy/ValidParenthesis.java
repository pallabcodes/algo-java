package array.easy;


import java.util.HashMap;
import java.util.Stack;

public class ValidParenthesis {
    public static void main(String[] args) {
        String s = "()[]{}";

        System.out.println(isValidParenthesis(s));
    }

    // Q: https://leetcode.com/problems/valid-parentheses/
    /* pattern:
     step 1: make an empty stack && then start looping on the given string
     step 2: determine whether current iteration is an opening bracket & then push it to the "stack"
     step 3: else, see "if the stack empty or not"; "else if whether current value matches with the values in array or not"; "else stack.pop"
     step 4: return stack.isEmpty()
    * */
    private static boolean isValidParenthesis(String str) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            char curr = str.charAt(i);

            // is the curr is an opening ? if so, push within the stack
            if (isOpening(curr)) {
                stack.push(curr);
            } else {
                // if there's no opening bracket(s) within stack then just return false
                if (stack.isEmpty()) {
                    return false;

                } else if (!isMatching(curr, stack.peek())) {
                    return false;
                } else {
                    // curr == stack.peek() then pop
                    stack.pop();
                }
            }
        }

        return stack.isEmpty();
    }


    private static boolean isOpening(char c) {
        return (c == '(') || (c == '{') || (c == '[');
    }

    private static boolean isMatching(char a, char b) {
        return (a == '(' && b == ')') || (a == '{' && b == '}') || (a == '[' && b == ']');
    }


}
