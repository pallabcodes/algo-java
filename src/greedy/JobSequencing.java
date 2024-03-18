package greedy;

import java.util.*;

public class JobSequencing {

    public static class Job {
        int deadline;
        int profit;
        int id;

        public Job(int deadline, int profit, int id) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    public static void main(String[] args) {
        int[][] jobsInfo = {
                {4, 20},
                {1, 10},
                {1, 40},
                {1, 30},
        };

        ArrayList<Job> jobs = new ArrayList<>();

        for (int i = 0; i < jobsInfo.length; i++) {
            jobs.add(new Job(i, jobsInfo[i][0], jobsInfo[i][1]));
        }

        // descending sort (based on profit property)
        Collections.sort(jobs, (obj1, obj2) -> obj2.profit - obj1.profit);

        ArrayList<Integer> seq = new ArrayList<>();
        int elapsedTime = 0;

        for (int i = 0; i < jobs.size(); i++) {
            Job curr = jobs.get(i);

            if (curr.deadline > elapsedTime) {
                seq.add(curr.id);
                // N.B: assuming every job that enter this if-block; will take exactly +1 unit of time to be completed. Regardless whether the job has 1 unit of time or more
                elapsedTime++;
            }
        }

        // print seq
        System.out.println("max jobs = " + seq.size());

        for (int i = 0; i < seq.size(); i++) System.out.println(seq.get(i) + " ");

        System.out.println();
    }
}
