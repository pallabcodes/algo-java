package strings;

public class Panagram {
    static int size = 26;

    public static void main(String[] args) {
        String str = "Abcdefghijklmnopqrstuvwxyz";
        int len = str.length();

// Time Complexity: O(N) Auxiliary Space: O(26)
//        if (allLetters(str, len)) {
//            System.out.println("Yes");
//        } else System.out.println("No");

        // Time Complexity: O(26*N) Auxiliary Space: O(1)

        allLetters(str);

    }

    private static boolean allLetters(String str, int len) {
        // convert the given string into lowercase
        str = str.toLowerCase();
        // make a frequency array to mark the present letters
        boolean[] present = new boolean[size];

        // now, traverse the character in the given string
        for (int i = 0; i < len; i++) {
            // is the current char is a letter?
            if (isLetter(str.charAt(i))) {
                // mark current letter to present
                int letter = str.charAt(i) - 'a';
                present[letter] = true;
            }
        }

        // traverse for every letter from a to z
        for (int i = 0; i < size; i++) {
            // if the current character isn't present in string then return false otherwise return true
            if (!present[i]) return false;
        }


        return true;
    }

    private static void allLetters(String str) {
        // convert the given string into lowercase
        str = str.toLowerCase();

        boolean allLettersPresent = true;


        // now, traverse the character in the given string
        for (char ch = 'a'; ch <= 'z'; ch++) {
            // check if the string does not contain all letters
            if (!str.contains(String.valueOf(ch))) {
                allLettersPresent = false;
                break;
            }
        }


        // check if all the letters are present then print "Yes" otherwise print "No"
        if (allLettersPresent) {
            System.out.println("Yes");
        } else System.out.println("No");


    }

    private static boolean isLetter(char ch) {
        return Character.isLetter(ch);
    }

}
