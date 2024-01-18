package hashtable.hashmap;

public class Start {
    public static void main(String[] args) {
        String name = "John";

        // hashCode() generates a number(i.e. Object's memory address) from any object not
        // just strings, This number used store/retrieve object quickly from hashtable
        System.out.println(name.hashCode());

        Integer a = 4235678; // the value (i.e. number in the first place and unique) so it'll be used

        int code = a.hashCode();

        System.out.println(code);
    }

}
