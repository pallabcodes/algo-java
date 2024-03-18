package trie;

public class UniquePrefix {

    private static class Node {
        Node[] children = new Node[26]; // to store 'a' - 'z'
        boolean endOfWord = false;

        int freq; // Frequency of nodes

        public Node() {
            for (int i = 0; i < children.length; i++) {
                children[i] = null;
            }

            freq = 1;
        }

    }

    public static Node root = new Node();

    private static void insert(String word) { // O(L)
        Node curr = root;
        for (int level = 0; level < word.length(); level++) {
            int idx = word.charAt(level) - 'a';
            if (curr.children[idx] == null) {
                curr.children[idx] = new Node();
            } else {
                curr.children[idx].freq++;
            }
            curr = curr.children[idx];
        }

        curr.endOfWord = true;
    }

    // Finds the shortest unique prefix for a given word
    private static String findPrefix(String word) {
        Node curr = root;
        StringBuilder prefix = new StringBuilder();
        for (int level = 0; level < word.length(); level++) {
            int idx = word.charAt(level) - 'a';
            if (curr.children[idx] != null && curr.children[idx].freq == 1) {
                prefix.append(word.charAt(level));
                return prefix.toString();
            }
            prefix.append(word.charAt(level));
            curr = curr.children[idx];
        }
        return prefix.toString(); // In case the word itself is unique
    }


    public static void main(String[] args) {
        String[] arr = {"zebra", "dog", "duck", "dove"};

        for (String word : arr) {
            insert(word);
        }

        // Displaying the unique prefix for each word
        for (String word : arr) {
            System.out.println("Unique prefix for " + word + ": " + findPrefix(word));
        }
    }
}
