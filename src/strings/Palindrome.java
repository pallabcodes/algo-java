package strings;

public class Palindrome {
    public static void main(String[] args) {
        System.out.println(isPalindrome(""));
        System.out.println(isPalindrome("nogon"));

    }

    static boolean isPalindrome(String str) {
        if(str == null || str.length() == 0) return true;
        str = str.toLowerCase();
        // e.g. "moon"; n = 4; so how many times it needs to loop? i.e. n/2 or when s <= e with while loop
        for (int i = 0; i < str.length() / 2; i++) {
            char s = str.charAt(i);
            char e = str.charAt(str.length() - 1 - i);
            if(s != e) {
                return false;
            }
        }
        return true;
    }
}
