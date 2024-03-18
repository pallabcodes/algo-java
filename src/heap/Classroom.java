package heap;

import java.util.*;

public class Classroom {
    // N.B: Heap is basically a data structure that implements Priority Queue using an array
    public static class Heap {
        ArrayList<Integer> list = new ArrayList<>();

        public void add(int data) { // O(log n) TC
            // 1. Add the element to last index within ArrayList i.e using add()
            list.add(data);

            int x = list.size() - 1; // child index
            int par = (x - 1) / 2; // parent index

            // 2. Fix Heap (it will go to each level above it from itself so if total nodes = 6 then it will traverse 3 times i.e. O(log n) times)
            // To make it Max Heap: comparator below from less than to ">"
            while (list.get(x) < list.get(par)) {
                int temp = list.get(x);
                list.set(x, list.get(par)); // at the child index set value from parent index
                list.set(par, temp); // now, at the parent index set value from child index

                x = par;
                par = (x - 1) / 2;
            }

        }

        public int peek() {
            return list.get(0); // get the min element from heap
        }

        private void heapify(int i) {
            int left = 2 * i + 1; // left child
            int right = 2 * i + 2; // right child

            int minIdx = i; // assumed root index as min (change name to maxIdx for Max Heap)

            // To make it Max Heap: list.get(minIdx) < list.get(left)
            if (left < list.size() && list.get(minIdx) > list.get(left)) {
                minIdx = left;
            }

            // To make it Max Heap: list.get(minIdx) < list.get(right)
            if (right < list.size() && list.get(minIdx) > list.get(right)) {
                minIdx = right;
            }

            // if minIdx == i; then no need to swap which is why checked below
            if (minIdx != i) {
                // swap
                int temp = list.get(i);
                list.set(i, list.get(minIdx));
                list.set(minIdx, temp);

                // 1. don't know how many level it needs to fix so since range is unknown thus recursion

                heapify(minIdx);
            }
        }

        public int remove() { // O(log n)
            int data = list.get(0);

            // 1. Swap the first and last value
            int temp = list.get(0);
            int lastIdx = list.size() - 1;
            list.set(0, list.get(lastIdx));
            list.set(lastIdx, temp);

            // 2. delete the last value
            list.remove(lastIdx);

            // 3. Heapify (i.e. fix Heap) call on root node which should be 0 (it fixes from top - down level) and since it travels all levels from top -> bottom thus O(log n)
            heapify(0);

            return data;
        }

        public boolean isEmpty() {
            return list.size() == 0;
        }
    }

    // heapfiy for Min/Max Heap (to sort in asc , make a Max Heap and for desc make Min Heap)
    public static void heapify(int i, int size, int[] arr) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int maxIdx = i;

        // to create min heap: arr[left] < arr[maxIdx]
        if (left < size && arr[left] > arr[maxIdx]) {
            maxIdx = left;
        }

        // to create min heap: arr[right] < arr[maxIdx]
        if (right < size && arr[right] > arr[maxIdx]) {
            maxIdx = right;
        }

        if (maxIdx != i) {
            // swap
            int temp = arr[i];
            arr[i] = arr[maxIdx];
            arr[maxIdx] = temp;

            heapify(maxIdx, size, arr); // keep calling it recursively until the this condition is truthy
        }

    }

    public static void heapSort(int[] arr) { // O(nlogn) + O(nlogn) = O(2nlogn) => (drop constant) O(n.log.n) and O(1) SC
        // 1. build the Max Heap

        int n = arr.length;

        // get access to "level before the last level by using n / 2"

        for (int i = n / 2; i >= 0; i--) {
            heapify(i, n, arr); // O(n/2*log n) => (drop constant i.e. /2) so now O(n * log n)

        }

        // 2. push largest at end
        for (int i = n - 1; i > 0; i--) { // O(n)
            // swap (largest-first with last value)
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(0, i, arr); // O(n) * O(log n) => O(n log n)

        }
    }

    public static class Student implements Comparable<Student> {
        String name;
        int rank;

        public Student(String name, int rank) {
            this.name = name;
            this.rank = rank;
        }

        @Override
        public int compareTo(Student s2) {
            return this.rank - s2.rank;
        }
    }

    public static void main(String[] args) {
        // N.B: by default it follows ascending order when "remove()" i.e. smallest value one after another
        // PriorityQueue<Integer> pq = new PriorityQueue<>();

        // N.B: now it follows descending order when "remove()" i.e. largest value one after another
        // PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());

        PriorityQueue<Student> pq = new PriorityQueue<>();
        pq.add(new Student("A", 4)); // O(logn)
        pq.add(new Student("B", 5)); // O(logn)
        pq.add(new Student("C", 2)); // O(logn)
        pq.add(new Student("D", 12)); // O(logn)

        while (!pq.isEmpty()) {
            System.out.println(pq.peek().name + " -> " + pq.peek().rank); // O(1)
            // When using integer , the smallest value will have highest priority during "remove()" therefore it will removed first
            pq.remove(); // O(logn)
        }

        // Heap

        Heap heap = new Heap();
        heap.add(3);
        heap.add(4);
        heap.add(1);
        heap.add(5);

        while (!heap.isEmpty()) { // heap sort O(n log n)
            System.out.println(heap.peek());
            heap.remove();
        }

        int[] arr = {1, 4, 3, 5, 2};
        heapSort(arr);
        for (int no : arr) System.out.print(no + " ");
    }
}
