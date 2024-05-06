package solid.srp;

public class LoanService {
    public static void getLoanInterestInfo(String loanType) {
        switch (loanType) {
            case "homeLoan":
                System.out.println("home loan");
            case "personalLoan":
                System.out.println("personal loan");
            case "educationLoan":
                System.out.println("education loan");
            case "car":
                System.out.println("car loan");
            default:
                System.out.println("check your input again");
        }
    }
}
