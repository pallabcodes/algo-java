package recursion.basics.easy;

public class Nto1 {
    public static void main(String[] args) {
        // nto1(5);
        // concept(5);
        // printDec(5);
        printInc(5);

    }

    private static void nto1(int n) {
        if (n == 0) return;
        System.out.println("desc " + n);
        nto1(n - 1);
        System.out.println("asc " + n);
    }

    private static void printDec(int n) {
        if (n == 1) {
            System.out.println(n);
            return;
        }
        System.out.println(n);
        printDec(n - 1);

    }
    private static void printInc(int n) {
        if (n == 1) {
            System.out.println("increasing " + n);
            return;
        }
        printInc(n - 1);
        System.out.println("increasing " + n);
    }

    private static void concept(int n) {
        if (n == 0) return;
        System.out.println(n);
        concept(--n);
    }


}
