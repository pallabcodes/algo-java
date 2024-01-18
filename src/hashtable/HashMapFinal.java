package hashtable;

import java.util.*;

// implement hashmap using LinkedList : chaining method
public class HashMapFinal<K, V> {

    ArrayList<LinkedList<Entity>> list;
    private int size = 0;

    // if size is 50 and all slots are filled, then when adding 51st item, 1) double

    // size of slot 50*2 = 100 b) copy the e

    private float lf = 0.5f;

    public HashMapFinal() {

        list = new ArrayList<>(); // initialized an empty ArrayList whenever this class is instantiated

        for (int i = 0; i < 10; i++) {
            list.add(new LinkedList<>()); // initially it will have an empty LinkedList
        }
    }

    public void put(K key, V value) {
        int hash = Math.abs(key.hashCode() % list.size());

        LinkedList<Entity> entities = list.get(hash);

        for (Entity entity : entities) {
            if (entity.key.equals(key)) {
                entity.value = value;
                return;
            }
        }

        if ((float) (size) / list.size() > lf) {
            reHash(); // double the size of the list
        }

        entities.add(new Entity(key, value));
        size++;
    }

    private void reHash() {
        System.out.println("now reHashing");
        ArrayList<LinkedList<Entity>> old = list;
        list = new ArrayList<>();
        size = 0;

        for (int i = 0; i < old.size() * 2; i++) {
            list.add(new LinkedList<>());
        }

        for (LinkedList<Entity> entries : old) {
            for (Entity entry : entries) {
                put(entry.key, entry.value);
            }
        }
    }

    public V get(K key) {
        int hash = Math.abs(key.hashCode() % list.size());
        LinkedList<Entity> entities = list.get(hash);
        for (Entity entity : entities) {
            if (entity.key.equals(key)) {
                return entity.value;
            }
        }

        return null;
    }

    public void remove(K key) {
        int hash = Math.abs(key.hashCode() % list.size());
        LinkedList<Entity> entities = list.get(hash);

        Entity target = null;

        for (Entity entity : entities) {
            if (entity.key.equals(key)) {
                target = entity;
                break;
            }
        }

        entities.remove(target);
        size--;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // sout(instance of this class) -> it'll behave as bellow since sout(instance) === sout(instance.toString())
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        for (LinkedList<Entity> entities : list) {
            for (Entity entry : entities) {
                builder.append(entry.key);
                builder.append(" = ");
                builder.append(entry.value);
                builder.append(", ");
            }
        }

        builder.append("}");
        return builder.toString();
    }

    private class Entity {
        K key;
        V value;

        public Entity(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }

}
