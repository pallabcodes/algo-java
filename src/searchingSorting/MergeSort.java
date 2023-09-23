package searchingSorting;

import java.util.*;

// This is my own implementation
public class MergeSort {

    private static List<Integer> mergeSort(int[] arr) {
        if (arr.length == 1)
            return new ArrayList<Integer>(arr[0]);
        int N = arr.length;

        int s = 0;
        int e = N - 1;

        int mid = (s + e) / 2;

        // "left"
        int[] left = merge(arr, s, mid);

        System.out.println("left: " + Arrays.toString(left));

        // right
        int[] right = merge(arr, mid + 1, e);
        System.out.println("right: " + Arrays.toString(right));

        List<Integer> result = new ArrayList<Integer>();

        // now just sort the result and return it

        int L = 0;
        int R = L;

        if (right.length == 1 && left.length == 1) {
            if (right[R] > left[L]) {
                result.add(left[L]);
                result.add(right[R]);
            } else {
                result.add(right[R]);
                result.add(left[L]);
            }
            return result;

        }

        // N.B: iterate on which has greater length (here right = 3, left = 4; so used
        // left.length)
        while (L < left.length) {

            // now compare : it should only be allowed when "R" is less than right.length

            if (R < right.length) {
                if (right[R] < left[L]) {
                    result.add(right[R]);
                    R++;
                } else {
                    result.add(left[L]);
                    if (L == left.length - 1 && R <= right.length - 1) {
                        for (int i = R; i < right.length; i++) {
                            result.add(right[i]);

                        }

                        // R = right.length - 1;
                    }
                    L++;
                }
            }

        }

        return result;
    }

    private static int[] merge(int[] arr, int s, int e) {
        if (s == e) {
            // as I know only stop when array length is 1, so made an array with length 1
            int[] res = new int[1];
            res[0] = arr[s];
            return res;
        }
        int mid = (s + e) / 2;
        int[] left = merge(arr, s, mid);
        int[] right = merge(arr, mid + 1, e);

        // N.B: every time array.length from both left and right will be same

        for (int i = 0; i < left.length; i++) {
            int j = 0;

            if (j < right.length) {
                if (left[i] > right[j]) {
                    int temp = left[i];
                    left[i] = right[j];
                    right[j] = temp;

                }
                j++;
            }
        }

        int[] result = new int[left.length + right.length];

        System.arraycopy(left, 0, result, 0, left.length);
        System.arraycopy(right, 0, result, left.length, right.length);

        return result;
    }

    public static void main(String[] args) {
        int[] arr = { 39, 27, 42, 4, 10, 83, 1 };
        int[] arr2 = { 3, 2, 1, 4, 0, -1 };
        int[] arr3 = { -3, 2, 1, 4, 0, -1 };
        int[] arr4 = { -3, -2, 1, 4 };
        int[] arr5 = { 1, 0 };

        System.out.println("Sorted " + Arrays.toString(mergeSort(arr).toArray()));
        System.out.println("Sorted " + Arrays.toString(mergeSort(arr2).toArray()));
        System.out.println("Sorted " + Arrays.toString(mergeSort(arr3).toArray()));
        System.out.println("Sorted " + Arrays.toString(mergeSort(arr4).toArray()));
        System.out.println("Sorted " + Arrays.toString(mergeSort(arr5).toArray()));
    }
}
