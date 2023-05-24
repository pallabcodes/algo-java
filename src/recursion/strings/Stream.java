package recursion.strings;

public class Stream {
    public static void main(String[] args) {
        skip("", "bcaad");
        // System.out.println(skip("bcad"));
        // System.out.println(skipApple("AforAnapple"));
        // System.out.println(skipAppNotApple("apples")); // it doesn't skip as it is apple not app
        // System.out.println(skipAppNotApple("appls")); // here it works as it is not apple rather appls


    }

    private static void skip(String p, String up) {
        if (up.isEmpty()) {
            System.out.println(p);
            return;
        }


        char ch = up.charAt(0);

        if (ch == 'a') {
            skip(p, up.substring(1));
        } else {
            skip(p + ch, up.substring(1));
        }
    }

    private static String skip(String up) {
        if (up.isEmpty()) return "";

        char ch = up.charAt(0);

        if (ch == 'a') {
            return skip(up.substring(1));

        } else {
            return ch + skip(up.substring(1));
        }
    }

    private static String skipApple(String up) {
        if (up.isEmpty()) {
            return "";
        }
        if (up.startsWith("apple")) {
            return skipApple(up.substring(5));
        } else {
            return up.charAt(0) + skipApple(up.substring(1));
        }
    }
    private static String skipAppNotApple(String up) {
        if (up.isEmpty()) {
            return "";
        }
        if (up.startsWith("app") && !up.startsWith("apple")) {
            return skipAppNotApple(up.substring(3));
        } else {
            return up.charAt(0) + skipAppNotApple(up.substring(1));
        }
    }
}
