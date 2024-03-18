package queue;

import java.util.*;

public class FirstNonRepeating {
//    public static void printNonRepeating(String str) {
//        int[] freq = new int[26]; // 'a' - 'z'
//        Queue<Character> queue = new LinkedList<>();
//
//        for (int i = 0; i < str.length(); i++) {
//            char ch = str.charAt(i);
//            queue.add(ch);
//            freq[ch - 'a']++;
//
//            while (!queue.isEmpty() && freq[queue.peek() - 'a'] > 1) {
//                // then I know it is repeated character so remove from the "queue"
//                queue.remove();
//            }
//
//            if (queue.isEmpty()) {
//                System.out.print(-1 + " ");
//            } else {
//                System.out.print(queue.peek()+" ");
//            }
//        }
//        System.out.println();
//    }

    public static void main(String[] args) {
        String str = "aabccxb";
        // printNonRepeating(str);
    }
}
