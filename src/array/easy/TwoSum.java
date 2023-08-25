package array.easy;

import java.util.Arrays;
import java.util.HashMap;

public class TwoSum {
    public static void main(String[] args) {
        int[] list = {3, 2, 4};
        int n = list.length;

        System.out.println(Arrays.toString(twoSum(list, 6)));
        System.out.println(Arrays.toString(twoSum(list, n, 6)));
    }

    // working solution: brute force
    private static int[] twoSum(int[] arr, int target) {
        int n = arr.length;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{}; // no solution found so return an empty array

    }

    // Q: https://leetcode.com/problems/two-sum/
    /*
    * [[ pattern ]]
    *
    * 1. first, make an empty hashmap
    * 2. loop on the array and put item as key and value
    * 3. loop on the array a
    * 3a.   remainingTotal = target - arr[i]
    * 3b.   if(map.containsKey(remainingTotal) && map.get(remainingTotal) != i then [i, map.get(remainingTotal)]
    * 4. return new int[]{}
    * */
    private static int[] twoSum(int[] arr, int n, int target) {
        HashMap<Integer, Integer> numMap = new HashMap<>();



        // iterate on the arr and build the hash table where value is the key and index is the value
        for (int i = 0; i < n; i++) {
            numMap.put(arr[i], i);
        }

        // {3: 0, 2: 1, 4: 2}

        // Find the complement
        for (int i = 0; i < n; i++) {
            int complement = target - arr[i];
            if (numMap.containsKey(complement) && numMap.get(complement) != i) {
                return new int[]{i, numMap.get(complement)};
            }
        }

        return new int[]{};

    }


}
