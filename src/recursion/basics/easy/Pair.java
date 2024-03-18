package recursion.basics.easy;

// "This is a discrete math: combinatorics problem" (https://chat.openai.com/c/0a3d9daf-43ca-4c28-bc1b-4d130087014f)
public class Pair {
    private static int friendsPairing(int n) {
        // if (n == 1 || n == 2) return n;

        if(n == 1) return 1; // 1 way, eating alone
        if( n == 2) return 2; // 2 ways, either they both eat alone or pair up


        // For when `n > 2`, a friend has two choices: eat alone(leaving `n-1` friends) or pair up with one of the `n-1` other friends (leaving `n-2` friends). When pairing up, we multiply by n-1 to account for the choice of which friend to pair with.


        // The friend can either eat alone (reduce the problem to n-1)
        // Or pair up with any of the remaining n-1 friends (reduce the problem to n-2 and multiply by n-1 for the choices of pairing)
        return friendsPairing(n - 1) + (n - 1) * friendsPairing(n - 2); // (n - 1) * f(n-2) This is a formula to represent no. of ways to form pairs (related to discrete match: combinatorics)
    }

    public static void main(String[] args) {
        System.out.println(friendsPairing(6));
    }
}
