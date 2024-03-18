package greedy;

import java.util.*;
import java.util.stream.*;

public class BalancedStrings {


    public static int balancedStringSplit(String s) { // TC = O(n) and SC = O(1)
        int counter = 0;
        int balance = 0; // Represents the difference between 'R' and 'L'

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'R') {
                balance++;
            } else {
                balance--;
            }

            if (balance == 0) counter++;
        }

        return counter;
    }

    public static List<String> splitStringBySeparator(List<String> words, char separator) { // T =O(n*m), S = O(k)
        ArrayList<String> ans = new ArrayList<>();
        String pk = String.valueOf(separator);

        for (String word : words) { // T = O(n), O(k)
            // N.B: if word has any special character(s) e.g. [, {, }, ] by default it could be treated as separator during split which is why explicitly said to consider "dot(.)" as separator by using Q and E
            String[] ak = word.split("\\Q" + pk + "\\E"); // O(m) -> it iterates over `word` and split by pk

            /*
             * why not time complexity O(n*m*k) but O(n*m)?
             * ---------------------------------
             * n = Number of strings in `words`; m = Average length of `word`
             * Each string i.e. `word` is processed exactly once by using `split()` thus O(n*m)
             * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
             *
             * [Attention]: `k` -> represents the average number of substrings resulting from split i.e. `ak` ↓
             * 1) Exactly once iterate and split => total substrings = O(k) depends on the length of the `word` = O(m)
             * [[ Iterating over these k substrings to add them to the `ans` list is a separate step
             * but it doesn't imply re-computing or additional complexity based on the length of the original strings.
             * The complexity of this step is directly tied to the number of substrings, not their lengths. ]]
             *
             * 2) `k` is a byproduct of the split operation, not an independent factor that multiplies the complexity.
             *
             * why not space complexity O(m * k) but O(k) ?
             * --------------------------------------------
             * The space complexity focuses on additional memory or space required by algorithm as the input size grows
             * 1) m = length of the string; k = total iterations and then splits
             * 2) `ans` added proportionally to `k` so space complexity is O(k)
             *
             *
             *
             * */


            for (String splitWord : ak) { // O(k) -> assuming `k` is the average no. of splits from word from each iteration
                if (!splitWord.isEmpty()) ans.add(splitWord);
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(balancedStringSplit("RLRRLLRLRL"));
        System.out.println(balancedStringSplit("RLRRRLLRLL"));
        System.out.println(balancedStringSplit("LLLLRRRR"));

        String regex = "\\Q" + '.' + "\\E";


        System.out.println(splitStringBySeparator(List.of("one.two.three", "four.five", "six"), '.'));
        System.out.println(splitStringBySeparator(List.of("$easy$", "$problem$"), '$'));
        System.out.println(splitStringBySeparator(List.of("|||"), '|'));

        // single line solution for `splitStringBySeparator`
        System.out.println("answer = " + List.of("one.two.three", "four.five", "six")
                .stream().flatMap(word -> Arrays.stream(word.split(regex)))
                .filter(splitWord -> !splitWord.isEmpty()).collect(Collectors.toList()));


    }
}
