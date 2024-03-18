package trie;

import java.util.*;

public class PhoneDirectory {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        Set<String> contacts = new TreeSet<>(); // Use TreeSet to store contacts in lexicographical order
    }

    TrieNode root = new TrieNode();

    // Insert a contact into the Trie
    void insert(String contact) {
        TrieNode node = root;
        for (char c : contact.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
            node.contacts.add(contact);
        }
    }

    // Search for a query prefix in the Trie and print all matching contacts
    void search(String query) {
        TrieNode node = root;
        for (int i = 0; i < query.length(); i++) {
            int index = query.charAt(i) - 'a';
            if (node.children[index] == null) {
                System.out.println("0");
                return;
            }
            node = node.children[index];
        }
        // Print contacts for the found prefix
        for (String contact : node.contacts) {
            System.out.print(contact + " ");
        }
        System.out.println();
    }

    // Run a search query for each prefix of the query string
    void searchQuery(String[] contacts, String query) {
        for (String contact : contacts) {
            insert(contact);
        }
        for (int i = 1; i <= query.length(); i++) {
            search(query.substring(0, i));
        }
    }

    public static void main(String[] args) {
        PhoneDirectory directory = new PhoneDirectory();
        String[] contacts = {"geeikistest", "geeksforgeeks", "geeksfortest"};
        String query = "geeips";
        directory.searchQuery(contacts, query);
    }
}
