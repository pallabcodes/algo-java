package hashtable.hashset;

import java.util.*;

public class SetAndHashset {
    public static void main(String[] args) {
        // unlike hashMap "Set only take (unique) keys" & discard duplicates (rather than throwing error)
        // Set is an interface, so it must be implemented by HashSet
         Set<String> names = new HashSet<>(); // new HashSet<>(); gives value "in the random order"
        // Set<String> names = new LinkedHashSet<>(); // new LinkedHashSet<>(); gives value "in the insertion order"
        // Set<String> names = new TreeSet<>(); // new TreeSet<>(); gives value in "alphabetical order"
        names.add("John");
        names.add("John");
        names.add("Jones");
        names.add("Jude");
        names.add("Jose");
        names.add("Andrew");

        // names.clear();
        // System.out.println(names.isEmpty());

        // System.out.println(names.size());

        // System.out.println(names.contains("Jose")); // this method is case-sensitive


        // System.out.println(names);

        //for (String name : names) System.out.println(name);
        // names.forEach(System.out::println);

        Iterator<String> namesIterator = names.iterator();

        while (namesIterator.hasNext()) {
            System.out.println(namesIterator.next());
        }

        List<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);
        numberList.add(4);
        numberList.add(1);

        System.out.println("numberList " + numberList); // [1, 2, 3, 4, 1]

        Set<Integer> numberSet = new HashSet<>();
        numberSet.add(1);
        numberSet.add(2);
        numberSet.add(3);
        numberSet.add(4);
        numberSet.add(1);

        System.out.println("numberSet " + numberSet); // [1, 2, 3, 4]
    }
}
