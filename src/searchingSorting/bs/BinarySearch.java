package searchingSorting.bs;

// BS works on a sorted array by dividing the array repeatedly in half and its time complexity O(Log n)

// sorted array => just use binary search

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 60, 110, 120, 130, 170};
        int x = 20;
        int ans = binarySearch(arr, x);
        System.out.println(ans);
    }

    static int binarySearch(int[] arr, int target) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2; // find the middle element
            if (target > arr[mid]) {
                start = mid + 1;
            } else if (target < arr[mid]) {
                end = mid - 1;
            } else {
                // if target is not greater / lesser than mid then it must be same as mid thus
                return mid;
            }
        }

        // if still not found then
        return -1;
    }
}
