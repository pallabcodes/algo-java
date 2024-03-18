package trie;

import java.util.*;

public class LongestWordInDictionary {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord = false; // Flag to indicate a complete word

        // Method to add a word to the Trie
        public void addWord(String word) {
            TrieNode node = this;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            node.isWord = true; // Mark the end of a word
        }
    }

    private TrieNode root = new TrieNode(); // Trie root

    // Method to find the longest word that can be built one character at a time
    public String longestWord(String[] words) {
        for (String word : words) {
            root.addWord(word);
        }

        String longestWord = "";
        for (String word : words) {
            if (canBeBuilt(word)) {
                if (word.length() > longestWord.length() || (word.length() == longestWord.length() && word.compareTo(longestWord) < 0)) {
                    longestWord = word;
                }
            }
        }
        return longestWord;
    }

    // Helper method to check if a word can be built one character at a time
    private boolean canBeBuilt(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children[c - 'a'];
            if (!node.isWord) {
                return false; // Cannot be built one character at a time
            }
        }
        return true; // All prefixes are valid words
    }

    public static void main(String[] args) {
        LongestWordInDictionary solution = new LongestWordInDictionary();

        String[] words1 = {"w", "wo", "wor", "worl", "world"};
        System.out.println(solution.longestWord(words1)); // Output: "world" T, S = O(N = length of the word * L = maximum length of a word)

        String[] words2 = {"a", "banana", "app", "appl", "ap", "apply", "apple"};
        System.out.println(solution.longestWord(words2)); // Output: "apple" T, S = O(N = length of the word * L = maximum length of a word)
    }
}
