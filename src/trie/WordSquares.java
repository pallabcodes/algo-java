package trie;

import java.util.*;

public class WordSquares {
    // Trie Node definition
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        List<String> startWith = new ArrayList<>();
    }

    // Trie with add and search functionality
    static class Trie {
        TrieNode root;

        Trie(String[] words) {
            root = new TrieNode();
            for (String w : words) {
                TrieNode cur = root;
                for (char ch : w.toCharArray()) {
                    cur.children.putIfAbsent(ch, new TrieNode());
                    cur = cur.children.get(ch);
                    cur.startWith.add(w);
                }
            }
        }

        List<String> findByPrefix(String prefix) {
            List<String> ans = new ArrayList<>();
            TrieNode cur = root;
            for (char ch : prefix.toCharArray()) {
                if (!cur.children.containsKey(ch)) {
                    return ans;
                }
                cur = cur.children.get(ch);
            }
            ans.addAll(cur.startWith);
            return ans;
        }
    }

    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> ans = new ArrayList<>();
        if (words == null || words.length == 0)
            return ans;
        int len = words[0].length();
        Trie trie = new Trie(words);
        List<String> square = new ArrayList<>();
        for (String word : words) {
            square.add(word);
            search(len, trie, square, ans);
            square.remove(square.size() - 1);
        }
        return ans;
    }

    private void search(int len, Trie trie, List<String> square, List<List<String>> ans) {
        if (square.size() == len) {
            ans.add(new ArrayList<>(square));
            return;
        }
        int idx = square.size();
        StringBuilder prefixBuilder = new StringBuilder();
        for (String s : square) {
            prefixBuilder.append(s.charAt(idx));
        }
        List<String> startWith = trie.findByPrefix(prefixBuilder.toString());
        for (String sw : startWith) {
            square.add(sw);
            search(len, trie, square, ans);
            square.remove(square.size() - 1);
        }
    }

    public static void main(String[] args) {
        WordSquares solver = new WordSquares();
        String[] words = { "area", "lead", "wall", "lady", "ball" };
        List<List<String>> wordSquares = solver.wordSquares(words);
        for (List<String> square : wordSquares) {
            for (String word : square) {
                System.out.println(word);
            }
            System.out.println();
        }
    }
}
