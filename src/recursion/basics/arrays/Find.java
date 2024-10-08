package recursion.basics.arrays;

import java.util.ArrayList;

public class Find {
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 4, 4, 5};
        // System.out.println(findAllIndex2(arr, 4, 0));
        // System.out.println(findAllIndex(arr, 4, 0, new ArrayList<Integer>()));
        // System.out.println(findIndexLast(arr, 1, arr.length - 1));
        // System.out.println(findIndexLast2(arr, 2, arr.length - 1));
        System.out.println(find(arr, 1, 0));
    }


    static boolean find(int[] arr, int target, int index) {
        if (index == arr.length) return false;
        return arr[index] == target || find(arr, target, index + 1);
    }

    static int findIndexLast(int[] arr, int target, int index) {
        if (index == -1) return -1;
        if (arr[index] == target) return index;
        return findIndexLast(arr, target, index - 1);
    }

    static int findIndexLast2(int[] arr, int target, int index) {
        if (index == -1) return -1;
        if (arr[index] == target) {
            return index;
        } else {
            return findIndexLast2(arr, target, index - 1);
        }
    }


    static ArrayList<Integer> findAllIndex(int[] arr, int target, int index, ArrayList<Integer> list) {
        if (index == arr.length) return list;
        if (arr[index] == target) {
            list.add(index);
        }
        return findAllIndex(arr, target, index + 1, list);

    }

    static ArrayList<Integer> findAllIndex2(int[] arr, int target, int index) {

        ArrayList<Integer> list = new ArrayList<>();

        if (index == arr.length) return list;

        // this will contain answer for that function call only
        if (arr[index] == target) {
            list.add(index);
        }
        ArrayList<Integer> ansFromBelowCalls = findAllIndex2(arr, target, index + 1);

        list.addAll(ansFromBelowCalls);

        return list;
    }
}
