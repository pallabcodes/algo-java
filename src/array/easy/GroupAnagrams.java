package array.easy;

import java.util.*;

// # My approach to problem-solving:

// 1) Read PS then figure out the cases based on the input
// 2) visualization + pseudocode ( on papert or digital paper )
// 3) brainstorm ( break down the PS into steps )
// 4) dry run
// 5) final code
public class GroupAnagrams {
    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(strs));
    }

    // Q: https://leetcode.com/problems/group-anagrams/
    /*
     * [[ pattern ]]
     * 0. if length == 0 (then there's no anagram) so return an empty ArrayList
     * 1. make an empty hashMap
     * 2. start looping on the given input i.e. strs and within loop do following things:
     *   2a. convert the current iteration i.e. string to chars
     *   2b. sort chars in non-decreasing order (i.e. ascending order)
     *   2c. convert it back to string and save it a variable named sorted
     *   2d. if map doesn't containsKey sorted; then add it to map as key and value empty ArrayList
     *   2e. after the if block access key: map.get(sorted).add(current_iteration i.e. s)
     * 3. now, return map.values()
     *
     * */

    private static List<List<String>> groupAnagrams(String[] strs) {
        // hashMap approach
        // map.put("1e,1a,1t", ["eat"])
        // if map.containsKey(key) then push onto that else put

        if (strs.length == 0) return new ArrayList<>();

        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] chars = s.toCharArray(); // ['e', 'a', 't']
            Arrays.sort(chars); // ['a', 'e', 't']
            String sorted = String.valueOf(chars); // or new String(chars) = "aet"

            if (!map.containsKey(sorted)) {
                map.put(sorted, new ArrayList<>());
            }

            map.get(sorted).add(s);
        }

        // now map.values() is an iterable & it'll be something like ["eat", "ate"], ["tan", "nat"]
        // since it's an array or array-like then it could be converted or wrapped into Array/ArrayList
        return new ArrayList<>(map.values());

    }
}
