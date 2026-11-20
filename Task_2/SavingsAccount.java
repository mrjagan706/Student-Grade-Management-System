
public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder,
                          double balance, double interestRate) {

        super(accountNumber, accountHolder, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount)
            throws InsufficientFundsException {

        if (amount <= 0) {
            System.out.println("Invalid Amount!");
            return;
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient Funds!"
            );
        }

        balance -= amount;
        logTransaction("Withdrawal");

        System.out.println("Withdrawal Successful!");
        System.out.println("Balance: " + balance);
    }

    public void calculateInterest() {

        double interest = balance * interestRate / 100;

        balance += interest;

        logTransaction("Interest Added");

        System.out.println("Interest: " + interest);
        System.out.println("Updated Balance: " + balance);
    }
}
