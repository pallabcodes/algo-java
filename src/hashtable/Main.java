package hashtable;

import java.util.*;

// HashMap Implementation using array
class MapUsingHash {
    private Entity[] entities; // class could used as a Type which is what done here

    public MapUsingHash() {
        entities = new Entity[100];
    }

    public void put(String key, String value) {
        int hash = Math.abs(key.hashCode() % entities.length);
        entities[hash] = new Entity(key, value); // overriding (if the current hashKey has already occupied)
    }

    public String get(String key) {
        int hash = Math.abs(key.hashCode() % entities.length);
        if (entities[hash] != null && entities[hash].key.equals(key)) {
            return entities[hash].value;
        }

        return null;
    }

    public void remove(String key) {
        int hash = Math.abs(key.hashCode() % entities.length);
        if (entities[hash] != null && entities[hash].key.equals(key)) {
            entities[hash] = null;
        }

    }

    private class Entity {
        String key;
        String value;

        public Entity(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        HashTable myHashTable = new HashTable();

        myHashTable.set("nails", 100);
        myHashTable.set("tile", 50);
        myHashTable.set("lumber", 80);
        myHashTable.set("bolts", 200);
        myHashTable.set("screws", 140);

        myHashTable.printTable();
        // System.out.println(myHashTable.get("lumber"));
        // System.out.println(myHashTable.get("bolts"));

        System.out.println(myHashTable.keys());

        HashMap<String, Integer> map = new HashMap<>();

        map.put("age", 29);

        System.out.println(map.getOrDefault("rank", 10));
        // System.out.println(map.get("age"), map.containsKey("age"));

        HashSet<Integer> set = new HashSet<>(); // same as JS Set
        set.add(2);

        // TreeMap<String, Integer> treeMap = new TreeMap<>(); // same as the HashMap,
        // give items in sorted order


        // implemented hashmap

        MapUsingHash customMap = new MapUsingHash();
        
        customMap.put("age", "20");
        System.out.println(customMap.get("age"));


        HashMapFinal<String, String> mapFinal = new HashMapFinal<>();
        mapFinal.put("name", "john");
        mapFinal.put("hobby", "gaming");

        System.out.println("HashUp with the LinkedList: implementation");

        
        System.out.println(mapFinal); // System.out.println(mapFinal.toString()); does same

    }

}
