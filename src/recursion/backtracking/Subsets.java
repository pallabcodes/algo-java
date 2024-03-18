package recursion.backtracking;

public class Subsets {

    // since, to find each subset for a single character it takes n steps (i.e. here
    // 3) so 2^n for every characters

    // String is immutable, every time it has a new value, it'll be created anew

    public static void findSubsets(String str, String ans, int i) { // T = O(n * 2^n), S = O(n)

        // base case
        if (i == str.length()) {
            if (ans.length() == 0) {
                System.out.println("null");
            } else {
                System.out.println(ans);
            }
            return;
        }

        // recursive case

        findSubsets(str, ans + str.charAt(i), i + 1); // include
        findSubsets(str, ans, i + 1); // exclude

    }

    public static void findSubsets(String str, StringBuilder ans, int i) {
        // Base case
        if (i == str.length()) {
            if (ans.length() == 0) {
                System.out.println("null");
            } else {
                System.out.println(ans.toString());
            }
            return;
        }

        // Recursive case
        ans.append(str.charAt(i)); // Include the current character
        findSubsets(str, ans, i + 1); // Move to the next character

        ans.deleteCharAt(ans.length() - 1); // Remove the last character added for exclude case
        findSubsets(str, ans, i + 1); // Exclude the current character and move to the next
    }

    public static void main(String[] args) {
        String str = "abc";
        findSubsets(str, "", 0);

        System.out.println();

        StringBuilder ans = new StringBuilder("");
        findSubsets(str, ans, 0);
    }
}
