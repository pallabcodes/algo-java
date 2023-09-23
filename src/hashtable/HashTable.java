package hashtable;

import java.util.*;

public class HashTable {
    private int size = 7;
    private Node[] dataMap; // this dataMap is an array whose element(s) are class

    // A class could be defined within another class like below "Node class"
    class Node {
        String key;
        int value;
        Node next;

        Node(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public HashTable() {
        dataMap = new Node[size]; // dataMap array's size will be assigned during initialization
    }

    public void printTable() {
        System.out.println("dataMap " + Arrays.toString(dataMap)); // [null, null, null, null, null, null, null]
        // now simply iterate over dataMap
        for (int i = 0; i < dataMap.length; i++) {
            System.out.println(i + ":");
            Node temp = dataMap[i];
            while (temp != null) {
                System.out.println("   {" + temp.key + "= " + temp.value + "}");
                temp = temp.next;
            }
        }
    }

    // used public accessor so that "hash method" could run from "Main"
    // tc : O(1)
    public int hash(String key) {
        int hash = 0;
        char[] keyChars = key.toCharArray(); // ['p', 'a', 'i', 'n', 't']
        for (int i = 0; i < keyChars.length; i++) {
            // to parse char -> ascii , "just assign char to an int variable"
            int asciiValue = keyChars[i];

            // 23 (divisible by itself and 1) i.e. prime number & used to get random no
            hash = (hash + asciiValue * 23) % dataMap.length;
        }
        return hash;
    }

    // tc : O(1)
    public void set(String key, int value) {
        // 1. first, determine the index (address) where key-value pair will be stored
        int index = hash(key);

        // 2. now create the node
        Node newNode = new Node(key, value);

        if (dataMap[index] == null) {
            dataMap[index] = newNode;
        } else {
            // assigning dataMap[index] to temp (now both share same reference in memory)
            Node temp = dataMap[index];
            // now found the node that needs to be updated (basically which is null)
            while (temp.next != null) {
                temp = temp.next;
                if (temp.key == key) {
                    temp.value += value;
                    return;
                }
            }

            // now, temp should be on a nose whose next = null
            temp.next = newNode;
        }

    }

    // tc : O(1)
    public int get(String key) {
        int index = hash(key);
        Node temp = dataMap[index];

        // why it doesn't work if used temp.next != null
        while (temp != null) {
            if (temp.key == key) {
                return temp.value;
            }
            temp = temp.next;
        }
        return 0;
    }

    public ArrayList keys() {
        ArrayList<String> allKeys = new ArrayList<>();
        for (int i = 0; i < dataMap.length; i++) {
            // first, directly access key-value pair(s) from the current index
            Node temp = dataMap[i];
            // then get all of its keys
            while (temp != null) {
                allKeys.add(temp.key);
                temp = temp.next;
            }
        }
        return allKeys;
    }
}