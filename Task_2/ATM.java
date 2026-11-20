
import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Account account;

        System.out.println("1. Savings Account");
        System.out.println("2. Checking Account");

        System.out.print("Select Account: ");
        int type = sc.nextInt();
        sc.nextLine();

        if (type == 1) {

            account = new SavingsAccount(
                "1001", "Jagan Maharana", 10000, 5
            );

        } else if (type == 2) {

            account = new CheckingAccount(
                "1002", "Jagan Maharana", 10000, 5000
            );

        } else {

            System.out.println("Invalid Account Type!");
            sc.close();
            return;
        }

        Customer customer = new Customer(
            "Jagan Maharana", "1234", account
        );

        // PIN Verification
        try {

            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();

            customer.verifyPIN(pin);

            System.out.println("PIN Verified!");

        } catch (InvalidPINException e) {

            System.out.println(e.getMessage());
            sc.close();
            return;
        }

        int choice;

        do {

            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Account Details");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Calculate Interest");
            System.out.println("6. Transaction Log");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    customer.displayDetails();
                    break;

                case 2:
                    System.out.println(
                        "Balance: " + account.getBalance()
                    );
                    break;

                case 3:

                    System.out.print("Enter Amount: ");
                    double deposit = sc.nextDouble();

                    account.deposit(deposit);
                    break;

                case 4:

                    System.out.print("Enter Amount: ");
                    double amount = sc.nextDouble();

                    try {
                        account.withdraw(amount);
                    } catch (InsufficientFundsException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:

                    if (account instanceof SavingsAccount) {

                        ((SavingsAccount) account).calculateInterest();

                    } else {
                        System.out.println("Interest not applicable.");
                    }
                    break;

                case 6:
                    account.displayTransactionLog();
                    break;

                case 7:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 7);

        sc.close();
    }
}


// Custom Exception 1
class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }
}


// Custom Exception 2
class InvalidPINException extends Exception {

    public InvalidPINException(String message) {
        super(message);
    }
}
