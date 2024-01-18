package searchingSorting;

import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        int[] arr = new int[]{5, 3, 4, 1, 2};
        // bubble(arr);
        // selection(arr);
        // insertion(arr);
        cyclic(arr);
        System.out.println(Arrays.toString(arr));

    }


    // best for small list
    private static void selection(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // find the last index for the current iteration
            int last = arr.length - i - 1;
            // now get the "index of max value" between first = 0 and last
            int maxIndex = getMaxIndex(arr, 0, last);
            swap(arr, maxIndex, last);
        }
    }

    private static void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    private static int getMaxIndex(int[] arr, int start, int end) {
        int max = start;

        for (int i = 0; i <= end; i++) {
            if (arr[max] < arr[i]) {
                max = i;
            }

        }


        return max;
    }

    private static void insertion(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                } else break;
            }
        }
    }


    private static void bubble(int[] arr) {
        boolean swapped;

        // run the steps n - 1 times
        for (int i = 0; i < arr.length; i++) {
            swapped = false;

            // for each iteration the max item will come at the last respective index
            for (int j = 1; j < arr.length - i; j++) {
                // swap if the current item is smaller than its previous item
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    swapped = true;
                }
            }

            // if swapped = false then here it means array is already sorted hence stop the program
            if (!swapped) {
                // so then stop this inner loop here
                break;
            }

        }


    }

    // when given nos. from range 1 to n then use cyclic sort (assuming n = 5 then array elements will be between 1 and 5)

    // when array element ranges between 0/1 to n like [1,2,4,5]
    private static void cyclic(int[] arr) {
        int i = 0;
        while (i < arr.length) {
            int correct = arr[i] - 1;
            if (arr[i] != arr[correct]) {
                swap(arr, i, correct);
            } else {
                i++;
            }
        }
    }
}
