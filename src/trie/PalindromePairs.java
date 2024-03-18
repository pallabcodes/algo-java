package trie;

import java.util.*;

public class PalindromePairs {

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int index = -1; // Index of the word in the array if this node is the end of the word
        List<Integer> list = new ArrayList<>(); // List of indices of words that form a palindrome from this node
    }

    private TrieNode root = new TrieNode();

    private void insert(String word, int index) {
        TrieNode node = root;
        for (int i = word.length() - 1; i >= 0; i--) {
            int j = word.charAt(i) - 'a';

            if (node.children[j] == null) {
                node.children[j] = new TrieNode();
            }

            if (isPalindrome(word, 0, i)) {
                node.list.add(index);
            }

            node = node.children[j];
        }
        node.index = index;
        node.list.add(index);
    }

    private boolean isPalindrome(String word, int i, int j) {
        while (i < j) {
            if (word.charAt(i++) != word.charAt(j--)) return false;
        }
        return true;
    }

    private List<List<Integer>> search(String word, int index) {
        List<List<Integer>> result = new ArrayList<>();
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            // Case 1: The word matches a word in the Trie completely, check for additional palindrome
            if (node.index >= 0 && node.index != index && isPalindrome(word, i, word.length() - 1)) {
                result.add(Arrays.asList(index, node.index));
            }

            node = node.children[word.charAt(i) - 'a'];
            if (node == null) return result;
        }

        // Case 2: The remaining part of the word forms a palindrome
        for (int j : node.list) {
            if (index != j) {
                result.add(Arrays.asList(index, j));
            }
        }

        return result;
    }

    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> pairs = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            insert(words[i], i);
        }

        for (int i = 0; i < words.length; i++) {
            pairs.addAll(search(words[i], i));
        }

        return pairs;
    }

    public static void main(String[] args) {
        PalindromePairs solution = new PalindromePairs();
        String[] words1 = {"abcd", "dcba", "lls", "s", "sssll"};
        System.out.println(solution.palindromePairs(words1));

        String[] words2 = {"bat", "tab", "cat"};
        System.out.println(solution.palindromePairs(words2));

        String[] words3 = {"a", ""};
        System.out.println(solution.palindromePairs(words3));
    }
}
