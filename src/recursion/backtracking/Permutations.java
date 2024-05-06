package recursion.backtracking;

// Total permutations = str = "abc" n = str.length() / 3, so total permutations n! = 3 * 2 * 1 = 6
public class Permutations {

    public static void findPermutations(String str, String ans) { // T = O(n * n!), S = O(n^2)

        // base case
        if (str.length() == 0) {
            System.out.println(ans);
            return;

        }

        // recursive case

        for (int i = 0; i < str.length(); i++) {
            char curr = str.charAt(i);

            // assume currently i = 2; "abcde" => "ab" + "de" = "abde"
            // str = str.substring(0, i) + str.substring(i + 1); // WRONG: don't modify str

            String newStr = str.substring(0, i) + str.substring(i + 1); // this will work since it's a new string
            findPermutations(newStr, ans + curr);
        }

    }

    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void findPermutations(char[] arr, int currentIndex) { // T = O(n * n!), S = O(n)
        if (currentIndex == arr.length - 1) {
            System.out.println(new String(arr));
            return;
        }

        for (int i = currentIndex; i < arr.length; i++) {
            swap(arr, currentIndex, i);
            findPermutations(arr, currentIndex + 1);
            swap(arr, currentIndex, i); // backtrack
        }
    }

    public static void main(String[] args) {
        String str = "abc";
        findPermutations(str, "");

        /*
         * Below version avoids string concatenation and substring operations by
         * working directly with a character array and swapping elements to form
         * permutations that can reduce runtime for especially longer strings. However,
         * time complexity will remain O(n * n!) due to nature of the problem
         *
         */
        findPermutations(str.toCharArray(), 0); // another implementation with helper swap
    }
}
