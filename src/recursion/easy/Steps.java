package recursion.easy;

public class Steps {
    public static void main(String[] args) {
        System.out.println(noOfSteps(14));
        System.out.println(noOfSteps(41));

    }

    private static int noOfSteps(int n) {
        // if some variable's value might update with each function call, then if possible take it as parameter like below
        return countSteps(n, 0); // since if no steps available then it would be 0 thus 0
    }

    private static int countSteps(int n, int steps) {
        if (n == 0) return steps;
        if (n % 2 == 0) {
            return countSteps(n / 2, steps + 1);
        } else {
            return countSteps(n - 1, steps + 1);
        }
    }
}
