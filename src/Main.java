import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Main().usingScanner());
    }

    boolean usingScanner() {
        // scanner
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        System.out.println(str);

        int skills = sc.nextInt();
        System.out.println("I have these " + skills + " skills");

        sc.nextLine();

        String food = sc.nextLine();
        System.out.println("favourite food is " + food);
        return true;
    }


}