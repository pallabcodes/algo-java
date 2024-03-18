package greedy;

import java.util.*;

public class Activity {
    public static void main(String[] args) { // TC O(n) and if needed to sort then O(nlogn)
        int[] start = {1, 3, 0, 5, 8, 5};
        int[] end = {2, 4, 6, 7, 9, 9};

        // activities
        int[][] activities = new int[start.length][3];
        for (int i = 0; i < start.length; i++) {
            activities[i][0] = i;
            activities[i][1] = start[i];
            activities[i][2] = end[i];
        }

        // lambda function
        Arrays.sort(activities, Comparator.comparingDouble(o -> o[2])); // sorted based 2nd column in asc order


        // end time basis sorted
        int maxAct = 0;
        ArrayList<Integer> ans = new ArrayList<>();

        // 1st activity
        maxAct = 1; // As I know that, at least 1 activity will be performed so maxAct = 1
        ans.add(activities[0][0]);
        int lastEnd = activities[0][2]; // when does first activity end?

        for (int i = 0; i < end.length; i++) {
            if (activities[i][1] >= lastEnd) {
                // activity select
                maxAct++;
                ans.add(activities[i][0]);
                lastEnd = activities[i][2];
            }
        }
        System.out.println("max activities = " + maxAct);
        for (int i = 0; i < ans.size(); i++) System.out.print("A" + ans.get(i) + " ");
        System.out.println();
    }
}
