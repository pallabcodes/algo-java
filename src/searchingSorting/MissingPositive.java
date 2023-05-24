package searchingSorting;

public class MissingPositive {
    public static void main(String[] args) {
        // System.out.println(missingPositive(new int[]{1, -1, 3, 4}));
        System.out.println(missingPositive(new int[]{1, 2, 0}));


    }

    private static int missingPositive(int[] arr) {
        int i = 0;
        while (i < arr.length) {
            int correct = arr[i] - 1;
            if (arr[i] > 0 && arr[i] <= arr.length && arr[i] != arr[correct]) {
                swap(arr, i, correct);
            } else {
                i++;
            }
        }

        // search for the first missing number
        for (int index = 0; index < arr.length; index++) {
            if (arr[index] != index + 1) {
                return index + 1;
            }

        }

        // case 2
        return arr.length + 1;
    }

    private static void swap(int[] arr, int first, int second) {
        // [ 1 0 ]
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;

    }
}
