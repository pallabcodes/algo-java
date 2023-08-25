package array.slidingwindow;

import java.util.ArrayList;

public class FindFirstNegativeNo {
    public static void main(String[] args) {
        int[] arr = {12, -1, -7, 8, -15, 30, 16, 28};

        int k = 3;

        System.out.println(firstNegative(arr, k));


    }

    // Q: First negative Integer in every window of size k
    private static ArrayList<Integer> firstNegative(int[] arr, int k) {
        int i = 0, j = 0;
        int size = arr.length;
        ArrayList<Integer> negatives = new ArrayList<Integer>();
        ArrayList<Integer> result = new ArrayList<Integer>();


        while (j < size) {
            int wSize = j - i + 1;

            if (arr[j] < 0) {
                negatives.add(arr[j]);
            }

            // System.out.println("negatives " + negatives);


            if (wSize < k) {
                j++;
            } else if (wSize == k) {
                // System.out.println("n " + negatives);
                // get the last value and store it
                if (negatives.size() != 0) {
                    result.add(negatives.get(0));
                    negatives.remove(0);
                    // System.out.println("after " + negatives);

                }


                i++;
                j++;


            }
        }


        return result;
    }

    private static void FirstNegativeBruteForce(int[] arr, int k) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < i + k; j++) {
                if (arr[j] < 0) {
                    // do something
                }

            }

        }
    }


}
