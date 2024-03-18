package queue;

import java.util.*;

/*
 * Q: Given a number N (e.g. 2), generate and print all binary numbers with decimal values from 1 to N
 * sample input = 2; sample output = 1 10
 * sample input = 5; sample output = 1 10 11 100 101
 *
 *
 * */
public class GenerateBinaryNo { // O(n), O(n)
//    private static void generateBinaryNo(int n) {
//        Queue<String> q = new LinkedList<String>();
//        q.add("1");
//
//        while (n-- > 0) {
//            String s1 = q.peek();
//            q.remove();
//            System.out.println("s1 " + s1);
//            String s2 = s1;
//            q.add(s1 + "0");
//            q.add(s2 + "1");
//        }
//    }
    public static void main(String[] args) {
        int n = 5;
//        generateBinaryNo(n);
    }
}
