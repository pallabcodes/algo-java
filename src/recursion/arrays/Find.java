package recursion.arrays;

import java.util.ArrayList;

// 1. When the expected output could be within subset
// 2. To reach the expected output; it requires same steps

// ##. assuming, a function has 2 recursive calls then

// #. The final recursive call is just to execute base case and return a value
// #. The other recursive call is to figure out "do something"

/*
* private static int[] flatten(int[] arr = new int[] {{1}, {2}}, index = 0) {
* // base case
* if(index == arr.length) return new int[];
*
* // now figure out "do something" ; also what to do with the return value from the final recursive call
* return flatten(arr, index + 1);
* }
*
*
*
* */

/*
 * function add(int x, int y) {
 * // "function invocation" ultimately return a "value" so below things can be done mainly:
 *
 * storing in a variable
 * using in if-else
 * passing it as argument to function/method
 * calculation
 * }
 * */


/*
 * local variable's value comes from the current call's local environment, (do something is done during push not pop)
 * Push:
 * findAllIndex2(): index = 6; stop and here in this call list only initialized so list = []
 * findAllIndex2(): index = 5; list = []
 * findAllIndex2(): index = 4; list = [index = 4]
 * findAllIndex2(): index = 3; list = [index = 3]
 * findAllIndex2(): index = 2; list = []
 * findAllIndex2(): index = 1; list = []
 * findAllIndex2(): index = 0; list = []
 * main()
 * */

/*
 * Pop:
 * [ if it has return statement then return & go to the line from where it invoked ] ↓
 *
 * findAllIndex2(): index = 6; stop and return list i.e. = [] & popped off, returned control from wherever it's invoked
 *
 * [ however, if it has no return statement (& has some code below); then those will now execute e.g. index 5 ]
 * [ now do whatever needed then return the necessary "value" & then it will popped off & once again the control will
 *  go back from where it's invoked ]
 * findAllIndex2(): index = 5; list = []
 * findAllIndex2(): index = 4; list = [index = 4]
 * findAllIndex2(): index = 3; list = [index = 3]
 * findAllIndex2(): index = 2; list = []
 * findAllIndex2(): index = 1; list = []
 * findAllIndex2(): index = 0; list = []
 * main()
 * */

public class Find {
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 4, 4, 5};
        // System.out.println(findAllIndex2(arr, 4, 0));
        // System.out.println(findAllIndex(arr, 4, 0, new ArrayList<>()));
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

        if (index == arr.length) {
            return list;
        }

        // this will contain answer for that function call only
        if (arr[index] == target) {
            list.add(index);
        }
        ArrayList<Integer> ansFromBelowCalls = findAllIndex2(arr, target, index + 1);

        list.addAll(ansFromBelowCalls);

        return list;
    }
}
