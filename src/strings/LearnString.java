package strings;

import java.util.Arrays;

public class LearnString {
    public static void main(String[] args) {
        String a = "john"; // saved to the memory and with an address (e.g. #b2aff)
        String b = "john"; // now as it reads/parse the value & then it looks if the value is already available within string pool; then it just point to that address (e.g. #b2aff) rather than assigning anew

        // string is immutable as a = "john"; a = "jones" ; now just reference pointer has changed from "john" to "jones"

        System.out.println(a == b);

        String a1 = new String("a");
        String b1 = new String("a");

        System.out.println(a1 == b1); // different object so they have different reference
        System.out.println(a1.equals(b1)); // it works as it does value comparison

        String name = "John Johnson";
        System.out.println(Arrays.toString(name.toCharArray()));
        System.out.println(name.indexOf(0));
        System.out.println(name.length());
        System.out.println(Arrays.toString(name.split(" ")));


    }
}
