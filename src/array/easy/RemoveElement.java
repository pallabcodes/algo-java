package array.easy;

public class RemoveElement {
    public static void main(String[] args) {
        System.out.println(removeElement(new int[]{3, 2, 2, 3}, 3));
        System.out.println(removeElement(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2));

    }

    // https://leetcode.com/problems/remove-element/description/
    private static int removeElement(int[] nums, int val) {
        return remove(nums, 0, nums.length, val, 0);
    }

    private static int remove(int[] arr, int i, int n, int val, int c) {
        if (i == n) {
            return c;
        }
        if (arr[i] == val) {
            return remove(arr, i + 1, n, val, c);
        } else {
            return remove(arr, i + 1, n, val, c + 1);
        }
    }
}
