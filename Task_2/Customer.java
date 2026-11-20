
public class Customer {

    private String name;
    private String pin;
    private Account account;

    public Customer(String name, String pin, Account account) {

        this.name = name;
        this.pin = pin;
        this.account = account;
    }

    public void verifyPIN(String enteredPIN)
            throws InvalidPINException {

        if (!pin.equals(enteredPIN)) {
            throw new InvalidPINException("Invalid PIN!");
        }
    }

    public Account getAccount() {
        return account;
    }

    public void displayDetails() {

        System.out.println("Customer Name: " + name);
        account.displayAccountDetails();
    }
}
