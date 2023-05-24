package searchingSorting.bs;

public class Ceiling {
    public static void main(String[] args) {
        int[] arr = {-18, -12, -4, 0, 2, 3, 4, 15, 16, 18, 22, 45, 89};
        int x = 21;
        int ans = ceiling(arr, x);
        System.out.println(ans);
    }

    // return the greatest / equal no. based on the target from array
    private static int ceiling(int[] arr, int target) {
        if (target > arr[arr.length - 1]) return -1;
        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            // 5 + 6 / 2 = 5 or 5 + ((6 - 5) / 2) = 5
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


        return start;
    }
}
