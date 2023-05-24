package recursion.arrays;

// This is Recursive Binary Search
public class RBS {
    public static void main(String[] args) {
        int[] rotated = {5, 6, 1, 2, 3, 4}; // increasing decreasing not working
        int[] asc = {1, 2, 3, 4, 5};
        int[] desc = {5, 4, 3, 2, 1}; // not working as left-right movement on desc array is different
        System.out.println(search(asc, 5, 0, asc.length - 1));
        System.out.println(searchDesc(desc, 4, 0, desc.length - 1));

    }

    // function local variable's value is specific the current function call
    // returnType arguments and what to do with function body

    // "All the values that are beneficial to future calls"; just "take them as parameter" like e.g. int s, int e
    // while mid is not necessary to future function calls thus it goes within function body
    private static int search(int[] arr, int target, int s, int e) {
        // for now or the default binary search code just works for asc
        if (s > e) return -1;

        int mid = s + (e - s) / 2;
        if (arr[mid] == target) return mid;

        if (target < arr[mid]) {
            return search(arr, target, s, mid - 1);
        }
        return search(arr, target, mid + 1, e);
    }

    // unable to figure out recursion; then simply use pen-paper to for better understanding (& visualizing)
    private static int searchDesc(int[] arr, int target, int start, int end) {
        if (start > end) return -1;

        int mid = start + (end - start) / 2;
        if (arr[mid] == target) return mid;

        if (target > arr[mid]) {
            return searchDesc(arr, target, start, mid - 1);
        }
        return searchDesc(arr, target, start + 1, end);
    }


}
