package trees.heap;

public class MaxPQ {
    int[] heap;
    int n; // size of max heap

    public MaxPQ(int capacity) {
        heap = new int[capacity + 1]; // since index 0 must be kept empty
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        int[] temp = new int[capacity];

        for (int i = 1; i < heap.length; i++) {
            // System.out.println("i " + i + " value "+ heap[i]);
            temp[i] = heap[i];
        }

        heap = temp;
    }

    public void insert(int x) {
        if (n == heap.length - 1) {
            resize(2 * heap.length);
        }
        n++;
        heap[n] = x;
        // whenever a value inserted, perform the "bottom-up heapify i.e. swim": used to insert in Max Heap
        swim(n);
    }

    // child index = k
    private void swim(int k) {
        while (k > 1 && heap[k / 2] < heap[k]) {
            int temp = heap[k];
            heap[k] = heap[k / 2];
            heap[k / 2] = temp;
            k = k / 2; // to continue shifting up till new value inserted at its correct position

        }

    }

    public int deleteMax() {
        int max = heap[1];
        swap(1, n);
        n--;
        sink(1); // this will follow "top-bottom heapify approach" to "maintain the max heap rule"
        // heap[n + 1] = null; // this basically will set the last value to null
        if (n > 0 && (n == (heap.length - 1) / 4)) {
            resize(heap.length / 2); // remove the unnecessary null-value positions
        }
        return max;
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && heap[j] < heap[j + 1]) j++; // this determines whether it has right child or not
            if (heap[k] >= heap[j]) break;
            swap(k, j);
            k = j;
        }
    }

    public void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    public void printMaxHeap() {
        for (int i = 1; i <= n; i++) {
            System.out.println(heap[i] + " ");
        }
    }

    public static void main(String[] args) {
        MaxPQ pq = new MaxPQ(3);
        // System.out.println(pq.isEmpty());
        pq.insert(4);
        pq.insert(5);
        pq.insert(2);
        pq.insert(6);
        pq.insert(1);
        pq.insert(3);
        // System.out.println(pq.size());
        // pq.printMaxHeap();
        pq.deleteMax();
        pq.printMaxHeap();
    }
}
