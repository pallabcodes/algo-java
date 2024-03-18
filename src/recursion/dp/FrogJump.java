package recursion.dp;

import java.util.*;


// DP category = Counting Paths or Path-finding
public class FrogJump {
    public boolean canCross(int[] stones) {
        // Key: stone position, Value: possible jump sizes to this position
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();

        for (int stone : stones) {
            map.put(stone, new HashSet<>());
        }

        // The first jump to the first stone is 0
        map.get(0).add(0);

        for (int stone : stones) {
            for (int lastJumpSize : map.get(stone)) {
                // Try jump sizes of lastJumpSize - 1, lastJumpSize, and lastJumpSize + 1
                for (int nextJumpSize = lastJumpSize - 1; nextJumpSize <= lastJumpSize + 1; nextJumpSize++) {
                    // nextJumpSize > 0 to prevent infinite loops and ensure forward movement
                    if (nextJumpSize > 0 && map.containsKey(stone + nextJumpSize)) {
                        map.get(stone + nextJumpSize).add(nextJumpSize);
                    }
                }
            }
        }

        // The frog can reach the last stone if the set is not empty
        return !map.get(stones[stones.length - 1]).isEmpty();
    }

    public static void main(String[] args) {
        FrogJump solution = new FrogJump();
        System.out.println(solution.canCross(new int[]{0,1,3,5,6,8,12,17})); // Output: true
        System.out.println(solution.canCross(new int[]{0,1,2,3,4,8,9,11})); // Output: false
    }
}
