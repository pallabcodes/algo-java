package recursion.basics.strings;

public class BinaryStrings {
    private static void binaryStrings(int n, int lastPlace, String str) {
        // base
        if (n == 0) {
            System.out.println(str);
            return;
        }

        binaryStrings(n - 1, 0, str + "0");

        if (lastPlace == 0) {
            binaryStrings(n - 1, 1, str + "1");
        }
    }

    public static void main(String[] args) {
        binaryStrings(3, 0, "");

    }
}
