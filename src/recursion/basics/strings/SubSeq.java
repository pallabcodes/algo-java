package recursion.basics.strings;

import java.util.ArrayList;

public class SubSeq {
    public static void main(String[] args) {
        // [359] = [ [3], [5], [9], [359], [3,5], [3,9], [5, 9] ]
        // str = [["abc"], ["a"],["b"],["c"], ["ab"], ["ac"], ["bc"]]

        // as seen here except the input itself "abc" each time it does inclusion or exclusion & i.e. subset pattern
        // here left = include;  right = exclude
        // subseq("", "abc");
        subseqAscii("", "abc");
        System.out.println(subseqAsciiRet("", "abc"));
        // System.out.println(subseqRet("", "abc"));
    }

    private static void subseq(String p, String up) {
        if (up.isEmpty()) {
            System.out.println(p);
            return;
        }
        char ch = up.charAt(0);

        subseq(p, up.substring(1)); // right = exclusion
        subseq(p + ch, up.substring(1)); // left = inclusion


    }

    private static ArrayList<String> subseqRet(String p, String up) {
        if (up.isEmpty()) {
            ArrayList<String> list = new ArrayList<>();
            list.add(p);
            return list;
        }
        char ch = up.charAt(0);

        ArrayList<String> left = subseqRet(p + ch, up.substring(1)); // left = inclusion

        ArrayList<String> right = subseqRet(p, up.substring(1)); // right = exclusion

        left.addAll(right);

        return left;
    }

    private static void subseqAscii(String p, String up) {
        if (up.isEmpty()) {
            System.out.println(p);
            return;
        }
        char ch = up.charAt(0);

        subseqAscii(p + ch, up.substring(1)); // taking
        subseqAscii(p, up.substring(1)); // ignoring
        subseqAscii(p + (ch + 0), up.substring(1)); // taking the next char like p = 'a' + (ch = 'b' + 0) = 195

    }

    private static ArrayList<String> subseqAsciiRet(String p, String up) {
        if (up.isEmpty()) {
            ArrayList<String> list = new ArrayList<>();
            list.add(p);
            return list;
        }
        char ch = up.charAt(0);

        ArrayList<String> first = subseqAsciiRet(p + ch, up.substring(1)); // taking

        ArrayList<String> second = subseqAsciiRet(p, up.substring(1)); // ignoring

        ArrayList<String> third = subseqAsciiRet(p + (ch + 0), up.substring(1)); // taking char but turning into no. by ch + 0

        first.addAll(second);
        first.addAll(third);

        return first;
    }
}
