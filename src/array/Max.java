package array;

public class Max {
    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 9, 18};
        System.out.println(maxRange(arr, 1, 3));
        System.out.println(maxSecond(new int[] {11, 12, 10, 5}));


    }

    // find the maximum value from the given range (start, end) from an array of integers
    static int maxRange(int[] arr, int start, int end) {
        if (arr == null || arr.length == 0) return -1;
        // if start = 4 and end = 2; then it needs to go backward but here it only allowed forward so if start > end then return -1
        if (start > end) return -1;

        int maxVal = arr[start]; // let's assume the maxValue at arr[start]

        for (int i = start; i <= end; i++) {
            if(arr[i] > maxVal) maxVal = arr[i];
        }

        return maxVal;

    }

    // find the max value from whole array (no range has specified)
    static int max(int[] arr) {
        if (arr.length == 0) {
            return -1;
        }
        int maxVal = arr[0]; // since there is no (start-end) range. so assume arr[0] as maxValue

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxVal) {
                maxVal = arr[i];
            }
        }
        return maxVal;
    }
    static int maxSecond(int[] arr) {
        int max = arr[0];
        int seondMax = 0;
        // 11 2 9 5
        for (int i = 1; i < arr.length; i++) {
            if(arr[i] > max) {
                seondMax = max;
                max = arr[i];
            } else if(arr[i] < max && arr[i] > seondMax) {
                seondMax = arr[i];
            }
        }
        // Math.min(Math.min(7, 6), 5) = that's how to find third min
        return seondMax;
    }



}
