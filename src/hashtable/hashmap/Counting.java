package hashtable.hashmap;

import java.util.HashMap;

// to find frequency/count and duplicate hashmap is the best
public class Counting {
    public static void main(String[] args) {
        String[] names = {"alice", "brad", "collin", "brad", "dylan", "jones"};
        HashMap<String, Integer> countMap = new HashMap<>();

        for (String name : names) {
            if (!countMap.containsKey(name)) {
                countMap.put(name, 1);
            } else {
                countMap.put(name, countMap.get(name) + 1);
            }
        }
        System.out.println(countMap);

    }

}
