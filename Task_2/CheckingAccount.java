
public class CheckingAccount extends Account {

    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolder,
                           double balance, double overdraftLimit) {

        super(accountNumber, accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount)
            throws InsufficientFundsException {

        if (amount <= 0) {
            System.out.println("Invalid Amount!");
            return;
        }

        if (amount > balance + overdraftLimit) {
            throw new InsufficientFundsException(
                "Overdraft Limit Exceeded!"
            );
        }

        balance -= amount;
        logTransaction("Withdrawal");

        System.out.println("Withdrawal Successful!");
        System.out.println("Balance: " + balance);
    }
}
