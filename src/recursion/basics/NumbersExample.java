package recursion.basics;

public class NumbersExample {
    public static void main(String[] args) {
        printTo5(1);
    }

    private static void printTo5(int n) {
        if (n == 5) {
            System.out.println(n);
            return;
        }
        //System.out.println(n);
        printTo5(n + 1);
        System.out.println(n); // reverse
    }
}
