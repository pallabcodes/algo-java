package searchingSorting;

public class LinearSearch {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(linearSearch(arr, arr.length, 7));
        System.out.println(sentinelLinearSearch(arr, arr.length, 8));
    }

    private static int linearSearch(int[] arr, int n, int key) {
        for (int i = 0; i < n; i++) if (arr[i] == key) return i;
        return -1;

    }


    // O(N) time complexity and auxiliary space O(1)
    private static int sentinelLinearSearch(int[] arr, int n, int key) {

        // example : [10 20 30 40 50], searchKey = 40; [10 20 30 40 50], searchKey = 50

        // last element of the array
        int last = arr[n - 1];

        // elements to be searched is played now as the last element of array
        arr[n - 1] = key; // searchKey = 40 => [10 20 30 40 40]; searchKey = 50 => [10 20 30 40 50]

        int i = 0;


        // keep incrementing i++ when current element is not same to searching.searching key
        while (arr[i] != key) i++;

        // when searchKey is 40 then index is found 3; and array is still as 10 20 30 40 40
        // when searchKey is 50 then index is found 4; and array is still as 10 20 30 40 50

        // now , put back the og last element : key = 40 => 10 20 30 40 50, key = 50 => 10 20 30 40 50

        arr[n - 1] = last;


        // now if the search key is at last element then i < n - 1 or arr[currentIndex] == key && return i
        if (i < n - 1 || arr[i] == key) return i;

        return -1;
    }


    private static void binarySearch(int[] arr, int n) {}


}
