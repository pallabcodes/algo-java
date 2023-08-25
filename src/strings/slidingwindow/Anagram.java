package strings.slidingwindow;

import java.util.HashMap;

public class Anagram {
    public static void main(String[] args) {

        String str = "aabaabaa";
        String str2 = "aaba";

        // each anagram must have same length as "str2" & same amount of letter (e.g.  3a 1b)

        getAnagrams(str, str2);


    }

    // Q: Find all anagrams in a string
    private static void getAnagrams(String str, String anagram) {
        int k = anagram.length();
        int len = str.length();

        // make a hashmap on anagram
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (int i = 0; i < anagram.length(); i++) {
            String key = String.valueOf(anagram.charAt(i));
            if (map.containsKey(key)) {
                int value = map.get(key) + 1;
                map.put(key, value);

            } else {
                map.put(key, 1);
            }
        }

        System.out.println(map);


        int i = 0, j = 0;
        int result = 0;


        while (j < len) {
            int wSize = j - i + 1;


            if (wSize < k) {
                j++;
            } else if (wSize == k) {

                i++;
                j++;
            }

        }
    }


}
