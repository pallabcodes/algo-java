package math.bitwise;

public class BitFinder {
    public static void main(String[] args) {
        int num = 182;
        int pos = 5;
        int bitMask = 1 << pos;
        if ((bitMask & num) == 0) {
            System.out.println("Bit: 0");
        } else {
            System.out.println("Bit: 1");
        }
    }


}
