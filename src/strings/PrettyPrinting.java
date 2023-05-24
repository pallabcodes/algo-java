package strings;

import java.util.ArrayList;

public class PrettyPrinting {
    public static void main(String[] args) {
        float a = 453.1274f;
        System.out.printf("Formatted number is %.2f", a);
        System.out.printf("Pie: %.3f", Math.PI);
        System.out.println();
        System.out.printf("Hello, my name is %s and I am learning %s", "Pallab", "Java");

        System.out.println();

        System.out.println('a' + 'b');
        System.out.println("a" + "b");
        System.out.println('a' + 1);
        System.out.println((char)('a' + 1));
        System.out.println("john" + new ArrayList<>());
        System.out.println("john" + new Integer(11));

        String ans = new Integer(10) + "" + new ArrayList<>();
        System.out.println("answer: " + ans);
    }
}
