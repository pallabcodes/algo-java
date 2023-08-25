package strings;

import java.util.Arrays;

public class Anagram {
    public static void main(String[] args) {
        String str1 = "java";
        String str2 = "vjaa";
        // System.out.println(isAnagram(str1, str2));

        int[] count = new int[26];

        // index = str1.charAt(0) - 'a'; now at this index array have a default value of 0; so 0 + 1 = 1
        System.out.println(count[str1.charAt(0) - 'a']++);


        // index = str1.charAt(0) - 'a'; now at this index array have a default value of 0; so 0 - 1 = -1
        System.out.println(count[str2.charAt(0) - 'a']--);




        System.out.println(Arrays.toString(count));
    }


    private static boolean isAnagram(String s1, String s2) {
        // both string must have same length and same frequency of characters
        if (s1.length() != s2.length()) return false;

        int[] count = new int[26];

        for (char ch : s1.toCharArray()) {
            count[ch - 97]++;
        }
        for (char ch : s2.toCharArray()) {
            count[ch - 97]--;
        }
        for (int val : count) {
            if (val != 0) return false;
        }

        return true;
    }
}
