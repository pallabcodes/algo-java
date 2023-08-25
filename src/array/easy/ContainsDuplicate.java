package array.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ContainsDuplicate {
    public static void main(String[] args) {
        int[] list = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
        int n = list.length;
        // System.out.println(Arrays.toString(list));
        // System.out.println(hasDuplicate(list, n));
        // System.out.println(hasDuplicate(list));
        // System.out.println(hasDuplicateWithHashMap(list));
        System.out.println(hasDuplicateWithHashSet(list));
    }

    // Q: https://leetcode.com/problems/contains-duplicate/
    // Time complexity: O(n*n) Space complexity: O(1)
    private static boolean hasDuplicate(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) if (arr[i] == arr[j]) return true;
        }
        return false;
    }

    // since input is array so first i) is it a sorted or unsorted ii) does it have -ve values ?

    // Time complexity: O(n*n) Space complexity: O(1)
    private static boolean hasDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) return true;
        }
        return false;
    }

    // Time complexity: O(n) Space complexity: O(n)
    private static boolean hasDuplicateWithHashMap(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) return true;
            map.put(num, 1);
        }

        return false;
    }

    // Time complexity: O(n) Space complexity: O(n)
    private static boolean hasDuplicateWithHashSet(int[] nums) {
        HashSet<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num)) return true;
            set.add(num);
        }

        return false;
    }

}
