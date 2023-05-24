package searchingSorting.bs;

public class Floor {
    public static void main(String[] args) {
        int[] arr = {2, 3, 5, 9, 14, 16, 18};
        int x = 15;
        int ans = floor(arr, x);
        System.out.println(ans);
    }

    // return the smallest / equal no. based on the target from array
    private static int floor(int[] arr, int target) {
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


        return end;
    }
}
