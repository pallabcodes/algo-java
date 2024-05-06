package solid.srp;

public class BankService {

    // is this functionality / method below or required in this class ? Yeah
    public long deposit(long amount, String accountNo) {
        return 0;
    }

    // is this functionality / method below or required in this class ? Yeah
    public long withdraw(long amount, String accountNo) {
        return 0;
    }

    // is this functionality / method below or required in this class ? Nope. So move it into another class and remove it from here
    // N.B: Moved to `PrinterService` so this functionality must be removed (or at least commented out) from here

    /*
    public static void printPassbook () {
        // update transaction into a passbook
    }


     */



    // is this functionality / method below or required in this class ? Nope. So move it into another class and remove it from here
    // N.B: Moved to `LoanService` so this functionality must be removed (or at least commented out) from here

    /*
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

     */


    // is this functionality / method below or required in this class ? Nope. So move it into another class and remove it from here
    // N.B: Moved to `NotificationService` so this functionality must be removed (or at least commented out) from here



    /*
    public static void sendOTP(String medium) {
        if(medium == "email") {
            // send otp through email
        }

        if(medium == "mobile") {
            // send otp to mobile
        }
     }


   */

}
