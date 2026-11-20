
import java.util.ArrayList;
import java.time.LocalDateTime;

public abstract class Account {

    protected String accountNumber;
    protected String accountHolder;
    protected double balance;

    protected ArrayList<String> transactionLog = new ArrayList<>();

    public Account(String accountNumber, String accountHolder,
                   double balance) {

        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public void deposit(double amount) {

        if (amount > 0) {
            balance += amount;
            logTransaction("Deposit");
            System.out.println("Deposit Successful!");
        } else {
            System.out.println("Invalid Amount!");
        }
    }

    public abstract void withdraw(double amount)
            throws InsufficientFundsException;

    protected void logTransaction(String type) {

        transactionLog.add(
            LocalDateTime.now() + " | " + type +
            " | Balance: " + balance
        );
    }

    public void displayTransactionLog() {

        for (String transaction : transactionLog) {
            System.out.println(transaction);
        }
    }

    public double getBalance() {
        return balance;
    }

    public void displayAccountDetails() {

        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: " + balance);
    }
}
