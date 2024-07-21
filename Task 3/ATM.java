
/*  Codsoft Java Internship

    Task 3 ( ATM Interface )

    Create a class to represent an ATM machine with a user interface that includes options for withdrawing, depositing, and checking the balance. 
    Implement methods for these options, connect them to a user's bank account class to manage the account balance, validate user input, 
    and display appropriate messages based on the success or failure of transactions.

    By Mrunali Kamerikar
*/

import java.util.Scanner;

// Class to represent the user's bank account
class BankAccount {
    private final String accountHolderName;
    private double balance;
    private String pin;

    public BankAccount(String accountHolderName, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.pin = "";
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Funds added successfully. Current balance: $" + balance);
        } else {
            System.out.println("Unable to process deposit. Please check the amount and try again.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Funds withdrawn successfully. Current balance: $" + balance);
        } else if (amount > balance) {
            System.out.println("Transaction declined due to insufficient balance.");
        } else {
            System.out.println("Invalid amount entered. Withdrawal not processed.");
        }
    }
}

// Class to represent the ATM machine
class ATM {
    private final BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public boolean verifyPin(String inputPin) {
        return account.getPin().equals(inputPin);
    }

    public boolean isPinSet() {
        return !account.getPin().isEmpty();
    }

    public void setPin(String newPin) {
        if (newPin.matches("\\d{4}")) {
            account.setPin(newPin);
            System.out.println("Your PIN has been set successfully.");
        } else {
            System.out.println("Error: Invalid PIN format. Please enter a 4-digit number.");
        }
    }

    public void withdraw(double amount) {
        account.withdraw(amount);
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }

    public void checkBalance() {
        System.out.println("Available balance $" + account.getBalance());
    }

    public void showAccountDetails() {
        System.out.println("Account Owner: " + account.getAccountHolderName());
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = sc.nextDouble();
                    deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = sc.nextDouble();
                    withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.println("Exiting ATM. Thank you!");
                    break;
                default:
                    System.out.println("Error: Please choose a valid option from the menu.");
            }
        } while (choice != 4);

        sc.close();
    }

    public void startATM() {
        Scanner sc = new Scanner(System.in);

        if (!isPinSet()) {
            System.out.print("You don't have a PIN set. Enter a new 4-digit PIN: ");
            String newPin = sc.nextLine();
            setPin(newPin);
        }

        int attempts = 0;
        boolean accessGranted = false;

        while (attempts < 2 && !accessGranted) {
            System.out.print("Enter your PIN: ");
            String inputPin = sc.nextLine();

            if (verifyPin(inputPin)) {
                accessGranted = true;
                System.out.println("Login successful.");
                showAccountDetails();
                showMenu();
            } else {
                attempts++;
                if (attempts < 2) {
                    System.out.println("Error: Invalid PIN. Retry with the correct one.");
                } else {
                    System.out.println("Error: Access denied due to invalid PIN.");
                }
            }
        }

        sc.close();
    }

    // Main method to start the ATM application
    public static void main(String[] args) {
        BankAccount account = new BankAccount("Mrunali Kamerikar", 1000.00); // Initial balance
        ATM atm = new ATM(account);

        atm.startATM(); // Start ATM application
    }
}
