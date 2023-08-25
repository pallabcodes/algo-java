package trees.heap;

import java.util.Collections;
import java.util.PriorityQueue;

public class HeapApp {
    public static void minHeap(int[] arr) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        for (int j : arr) {
            heap.add(j);
            System.out.print(heap.peek() + " ");
        }

        System.out.println();

        for (int i = 0; i < arr.length; i++) {
            heap.poll();
            System.out.print(heap.peek() + " ");
        }

        System.out.println();
    }

    public static void maxHeap(int[] arr) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());

        for (int j : arr) {
            heap.add(j);
            System.out.print(heap.peek() + " ");
        }

        System.out.println();

        for (int i = 0; i < arr.length; i++) {
            heap.poll();
            System.out.print(heap.peek() + " ");
        }

        System.out.println();
    }


    public static void main(String[] args) {

        int[] arr = {10, 7, 11, 5, 2, 13, 1, 45};

        HeapApp.minHeap(arr);
        HeapApp.maxHeap(arr);


    }
}
