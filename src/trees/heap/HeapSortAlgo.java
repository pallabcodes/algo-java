package trees.heap;

import java.util.Arrays;

// This is my own implementation based on my understanding on "Heap Sort"
public class HeapSortAlgo {

    private static void sort(int[] arr) {
        int N = arr.length;

        for (int i = N / 2 - 1; i >= 0; i--) {
            heapify(arr, N, i);
        }

        System.out.println("Max Heap is now ready = " + Arrays.toString(arr)); // this is enough max / min from array


        // begin sorting
        for (int i = N - 1; i > 0; i--) {

            // now swap
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // now after swap, Max Heap rule will be broken , so then "recreate the Max Heap"
            heapify(arr, i, 0);
        }
    }

    public static void heapify(int[] arr, int N, int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // N.B: "right" could be >= N but "left" will always be less than < N

        // if (left >= N) return;

        boolean isTheLeftGreater = false;

        if (right >= N) isTheLeftGreater = true;

        if (!isTheLeftGreater && left < N) {
            if (arr[left] > arr[right]) {
                isTheLeftGreater = true;
            }
        }


        if (isTheLeftGreater) {
            if (left < N && arr[left] > arr[i]) {
                int temp = arr[left];
                arr[left] = arr[i];
                arr[i] = temp;

                heapify(arr, N, left);
            }

        } else {
            if (right < N && arr[right] > arr[i]) {
                int temp = arr[right];
                arr[right] = arr[i];
                arr[i] = temp;

                heapify(arr, N, right);

            }

        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 12, 9, 5, 6, 10};
        sort(arr);

        System.out.println("The sorted Array is");
        for (int item : arr) {
            System.out.print(item + " ");
        }
    }

}