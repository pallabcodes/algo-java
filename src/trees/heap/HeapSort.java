package trees.heap;

import java.util.Arrays;

// tc = O(n log n), sc = O(1)
public class HeapSort {
    private static void sort(int[] arr) {
        int N = arr.length;

        for (int i = N / 2 - 1; i >= 0; i--) {
            heapify(arr, N, i);
        }

        System.out.println("Max Heap is now ready = " + Arrays.toString(arr));

        for (int i = N - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    public static void heapify(int[] arr, int N, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < N && arr[left] > arr[largest])
            largest = left;

        if (right < N && arr[right] > arr[largest])
            largest = right;

        if (largest != i) {
            int temp = arr[largest];
            arr[largest] = arr[i];
            arr[i] = temp;

            heapify(arr, N, largest);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 12, 9, 5, 6, 10};
        sort(arr);

        System.out.println("Sorted Array is");
        for (int j : arr) {
            System.out.print(j + " ");
        }

    }
}
