package patterns.iterative;

import java.util.Arrays;

//0. is the elements or some of the elements (clockwise/left-rotated, anti-clockwise/right rotated)
//1. correlation between n and i mainly, it could be in many ways, some examples: n - 1 - i, n - 1 * 2, n - i + 1, n / 2. n² etc. and nested loop
//  first element last element swap n - 1 - i = 7 - 1 - 0 = 6  so 0, 6; n - 1 - 1 : n - ( 1 + 1) = 5 so 1, 5
//2. how is their on expected result i.e. where are the elements are incrementing and decrementing e.g. [ 5 4 1 2 3 ]


// use recursion, 1. when output could be within the subset, 2. and it can be reached by doing same thing repeatedly


//https://www.geeksforgeeks.org/array-data-structure/

// swap: it's about placing 2 values at specific index
// rotate : moving element from one side to other (left rotate / right rotate)
// search, insert and delete in a sorted / unsorted array
// searchingSorting.sorting
// generate all sub-arrays


public class looping {

    private static void basicReverse(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            int current = arr[i];
            int swapAt = n - 1 - i; // 4 - 1 - i = 3 (0, 3), 4 - 1 - i = 2 (1, 2); 4 - 1 - i = 3 - 2 = 1, (3, 1)
            if (swapAt > i) {
                arr[i] = arr[swapAt];
                arr[swapAt] = current;
            }
        }
        System.out.println(Arrays.toString(arr));

    }

    // q: no such thing as reverse, rather swapping

    /*
     * 5 -  1 - i = swapIndex = 4, i = 0 , 4 = [ 5 2 3 4 1 ]
     * 5 -  1 - i = swapIndex = 3, i = 1 , 3 = [ 5 4 3 2 1 ] => break ; so this should be expected output after 2 swaps
     * */
    public static void SwapBy(int[] arr, int n, int times) {
        System.out.println("rotating an array twice to right");
        swapping(arr, n, times);
    }

    // clockwise
    public static void rotateToTheLeft(int[] arr, int n, int rotation) {
        // rotation = 2; [1 2 3 4 5 ] => [ 5 4 1 2 3 ] : only two elements moved from their og position so rotation is twice if not provided

        swapping(arr, n, rotation); // as the array has been rotated twice clockwise so now array is 5 4 3 2 1
        System.out.println("from here " + Arrays.toString(arr));

        // now exclude the no of times it has rotated before i.e. = 2; from there loop till the end of array i.e. for (i = 2; i < n; i++) {}
        // now as seen here the element between 2 to end of the array need a right / anti-clockwise rotation (simply leftmost with the rightmost element)

        for (int i = 2; i < n; i++) {
            int currentValue = arr[i];
            int swapIndexAtRight = (n - i) + 1; // 2nd: (5 - 2) = 4; 3rd = 5 - 3 + 1 = 3

            if (swapIndexAtRight == i) break; //  break when swapIndex and currentIndex is same

            arr[i] = arr[swapIndexAtRight];
            arr[swapIndexAtRight] = currentValue;
        }

        // expected result = 5 4 1 2 3
        System.out.println(Arrays.toString(arr));

    }

    // anti-clockwise
    public static void rotateToTheRight(int[] arr, int n, int rotation) {
    }

    // Q: reverse algo for a sorted array
    public static void reversal(int[] arr, int n, int digit) {
        // [1, 2, 3, 4, 5, 6, 7] => [3 4 5 6 7 1 2] :: [1 6 7 8] => [8 1 6 7]

        // first. let's shift array element to right / anti-clockwise by digit

        // note: input int[] {1,2, 3, 4, 5} output = {3 4 5 2 1} = can't just for (int i = 0; i < digit; i++) {  arr[i] = arr[n-1 - i] : this is hard coding swap value at this index and this logic won't work  }

        // secondly, since hard coding swap won't work so rotate (clockwise / anti-clockwise) ony be one till digit i.e. for (int i = 0; i < digit; i++) {}; here rotating right as 1 moves to right

        for (int i = 0; i < digit; i++) {
            System.out.println("index " + i);
            for (int j = 0; j < n; j++) {
                System.out.println(" index " + j + " : " + Arrays.toString(arr));
                int currentValue = arr[j];
                int next = j + 1;
                if (next < n) {
                    arr[j] = arr[next];
                    arr[next] = currentValue;

                }
            }
        }


    }

    private static void swapping(int[] arr, int n, int rotation) {
        for (int i = 0; i < rotation; i++) {
            int swapIndex = (n - 1) - i; // the index where to swap
            if (swapIndex == i) break;
            int currentValueBeforeSwap = arr[i]; // save the original current value
            arr[i] = arr[swapIndex];
            arr[swapIndex] = currentValueBeforeSwap;
        }
    }

    private static void swapByIndex(int[] arr, int i) {
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{1, 2, 3, 4, 5};
        int len = numbers.length;

        // count the total occurrence of no. 5 within the given number
        int n = 45535;
        int count = 0;

        while (n > 0) {
            int rem = n % 10;
            if (rem == 5) {
                count++;
            }
            n = n / 10; // n /= 10
        }

        System.out.println(count);


//        SwapBy(numbers, len, 2);
//        System.out.println("numbers after the reverse " + Arrays.toString(numbers));
//
//        rotateToTheLeft(numbers, len, 2);
//        reversal(new int[]{1, 2, 3, 4, 5, 6, 7}, 7, 2); // working
//        reversal(new int[]{1, 6, 7, 8}, 4, 3); // working
//        basicReverse(new int[]{4, 5, 1, 2}, 4); // working
//        basicReverse(new int[]{1, 2, 3}, 3); // working

    }
}

