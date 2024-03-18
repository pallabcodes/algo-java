package recursion.basics.strings;

public class Ascii {
    public static void main(String[] args) {
        char ch = 'a';
        System.out.println('a' + 0);
        System.out.println((char) (ch + 0));
        System.out.println((char) (ch + 1));
        System.out.println((char) ('a' + 1));
        System.out.println('a' + (char) ('b' + 0)); // p = 'a' so 97 + (ch = 'b' + 0 = 98) = 97+98 = 195

    }
}
