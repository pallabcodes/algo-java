package recursion.basics.strings;

import java.util.*;

public class SubsetStr {
    private static void generateSubStrings(String str, int index, String processed) {
        if (index == str.length()) {
            System.out.println(processed);
            return;
        }

        // Include the current character
        /*
         * The expression : processed + str.charAt(index) first concatenates the character using the current index, and then index + 1 is used for the next recursive call.
         * */
        generateSubStrings(str, index + 1, processed + str.charAt(index));

        // Exclude the current character
        generateSubStrings(str, index + 1, processed);

    }

    // O(2^n)
    private static List<String> generateSubstrings(String str) {
        // Base case: when the string is empty, return a list with an empty string
        if (str.isEmpty()) {
            List<String> baseResult = new ArrayList<>();
            baseResult.add("");
            return baseResult;
        }

        // Split the problem: first character and the rest of the string
        char firstChar = str.charAt(0);
        String restOfString = str.substring(1);

        // Recursively find substrings of the restOfString
        List<String> substringsOfRest = generateSubstrings(restOfString);

        // Create a new list to store results including the first character
        List<String> resultWithFirstChar = new ArrayList<>();

        // For each substring of the restOfString, add two versions:
        // one including the first character and one without it (already in substringsOfRest)
        for (String substr : substringsOfRest) {
            resultWithFirstChar.add(firstChar + substr);
        }

        // Combine the lists and return
        List<String> finalResult = new ArrayList<>(substringsOfRest);
        finalResult.addAll(resultWithFirstChar);
        return finalResult;
    }

    // O(2^n)
    private static void generateSubStrings(String str, int idx, String processed, ArrayList<String> result) {
        // Base case: when the current index is equal to the string length
        if (idx == str.length()) {
            // System.out.println(processed); // rather than printing now add it to the result
            result.add(processed);
            return;
        }

        // Form a new string by including the current character
        String included = processed + str.charAt(idx);

        // Recursive call with the current character included
        generateSubStrings(str, idx + 1, included, result);

        // Recursive call with the current character excluded
        generateSubStrings(str, idx + 1, processed, result);

    }

    private static void generateSubstringsV2(String str, int idx, String processed) {
        // Base case: when the current index is equal to the string length
        if (idx == str.length()) {
            System.out.println(processed);
            return;
        }

        // Form a new string by including the current character
        String included = processed + str.charAt(idx);

        // Recursive call with the current character included
        generateSubstringsV2(str, idx + 1, included);

        // Recursive call with the current character excluded
        generateSubstringsV2(str, idx + 1, processed);

    }


    public static void main(String[] args) {
        // generateSubStrings("ab", 0, "");
        ArrayList<String> list = new ArrayList<>();
        generateSubStrings("ab", 0, "", list);
        System.out.println(Collections.unmodifiableList(list));

        List<String> result = generateSubstrings("ab");
        System.out.println(result);

        // generateSubstringsV2("ab", 0, "");
    }
}
