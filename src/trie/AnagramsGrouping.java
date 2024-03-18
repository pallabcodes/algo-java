package trie;

import java.util.*;

public class AnagramsGrouping {
    class TrieNode {
        TrieNode[] children = new TrieNode[26]; // Assuming lowercase English letters
        List<String> anagrams = new ArrayList<>(); // To store original words forming anagrams
    }

    // Method to insert words into the Trie
    private void insert(String word, TrieNode root) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();
            }
            node = node.children[c - 'a'];
        }
        node.anagrams.add(word); // Add the original word to the list of anagrams at the leaf node
    }

    // Method to collect groups of anagrams from the Trie
    private void collectAnagrams(TrieNode node, List<List<String>> result) {
        if (node == null) return;

        if (!node.anagrams.isEmpty()) {
            result.add(new ArrayList<>(node.anagrams)); // Add the list of anagrams to the result
        }

        for (TrieNode child : node.children) {
            collectAnagrams(child, result);
        }
    }

    public List<List<String>> groupAnagrams(String[] words) {
        TrieNode root = new TrieNode();

        // Insert sorted words into the Trie
        for (String word : words) {
            char[] charArray = word.toCharArray();
            Arrays.sort(charArray); // Sort the word alphabetically
            String sortedWord = new String(charArray);
            insert(sortedWord, root);
        }

        List<List<String>> result = new ArrayList<>();
        collectAnagrams(root, result);
        return result;
    }

    public static void main(String[] args) {
        AnagramsGrouping solution = new AnagramsGrouping();
        String[] words = {"act", "god", "cat", "dog", "tac"};

        // T = O(N * L * log(L)), S = O(N * L) [ using hashmap + sorting will have same time and space complexity)

        List<List<String>> groups = solution.groupAnagrams(words);
        for (List<String> group : groups) {
            System.out.println(group);
        }
    }
}
