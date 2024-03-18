package recursion.basics.arrays;

public class IsSorted {
    // "when pop": if one of the function call returns false; then that will become the value for all of its previous calls
    private static boolean isSorted(int[] arr, int i) {
        if (i == arr.length - 1) return true;

        if (arr[i] > arr[i + 1]) {
            return false;
        }

        return isSorted(arr, i + 1);
    }

    public static void main(String[] args) {
        System.out.println(isSorted(new int[]{1, 2}, 0));
        System.out.println(isSorted(new int[]{2, 1}, 0));
    }

}
