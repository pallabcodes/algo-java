package recursion.backtracking;

public class MaxNumberBySwapping {
    private static String maxNum = "";

    private static void findMaximumNumber(String num, int k) {
        // Update maxNum if the current permutation is greater
        if (Integer.parseInt(num) > Integer.parseInt(maxNum)) {
            maxNum = num;
        }

        if (k == 0) {
            return;
        }

        int n = num.length();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                // Swap if the current digit is less than the next digit
                if (num.charAt(i) < num.charAt(j)) {
                    num = swap(num, i, j);
                    findMaximumNumber(num, k - 1);
                    num = swap(num, i, j); // Backtrack
                }
            }
        }
    }

    private static String swap(String s, int i, int j) {
        char[] charArray = s.toCharArray();
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }

    public static void main(String[] args) {
        int M = 254, K = 1;
        maxNum = String.valueOf(M);
        findMaximumNumber(String.valueOf(M), K);
        System.out.println(maxNum); // 524

        M = 254; K = 2;
        maxNum = String.valueOf(M);
        findMaximumNumber(String.valueOf(M), K);
        System.out.println(maxNum); // Output: 542

        M = 68543; K = 1;
        maxNum = String.valueOf(M);
        findMaximumNumber(String.valueOf(M), K);
        System.out.println(maxNum); // Output: 86543

        M = 7599; K = 2;
        maxNum = String.valueOf(M);
        findMaximumNumber(String.valueOf(M), K);
        System.out.println(maxNum); // Output: 9975

        M = 76543; K = 1;
        maxNum = String.valueOf(M);
        findMaximumNumber(String.valueOf(M), K);
        System.out.println(maxNum); // Output: 76543

        M = 129814999; K = 4;
        maxNum = String.valueOf(M);
        findMaximumNumber(String.valueOf(M), K);
        System.out.println(maxNum); // Output: 999984211
    }

}
