package array.easy;

import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/problems/valid-anagram/


// anagram: both string must've same length and same no. of characters (e.g. s: 'moon', t: 'mono')
public class Anagram {
    public static void main(String[] args) {
        String source = "anagram";
        String target = "nagaram";
        System.out.println(isAnagram(source, target));
        System.out.println(validAnagram(source, target));

    }

    private static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] count = new int[26];

        // s = "anagram"; s.toCharArray() = ['a', 'n', 'a', 'g', 'r', 'a', 'm']

        for (char ch : s.toCharArray()) count[ch - 97]++;

        for (char ch : t.toCharArray()) count[ch - 97]--;

        for (int value : count) if (value != 0) return false;

        return true;
    }

    private static boolean validAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            Character sc = s.charAt(i); // ascii
            Character st = t.charAt(i); // ascii

            // if key sc doest not exist then defaultValue + 1 otherwise map[map.key++]
            map.put(sc, map.getOrDefault(sc, 0) + 1);
            // if key sc doest not exist then defaultValue - 1 otherwise map[map.key--]
            map.put(st, map.getOrDefault(st, 0) - 1);
            // System.out.println("map " + map.values());
        }

        // printing
        // for (Map.Entry<Character, Integer> entry : map.entrySet()) {
        //     System.out.println("key " + entry.getKey());
        //     System.out.println("value " + entry.getValue());
        // }

        for (int i : map.values()) {
            if (i != 0) return false;
        }

        return true;
    }
}
