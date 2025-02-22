import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingApp {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "@Anshulsingh66";  // Consider using environment variables

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found. Make sure to add the JDBC library.");
            return;
        }

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            User user = new User(con, scanner);
            Accounts accounts = new Accounts(con, scanner);
            AccountManager accountManager = new AccountManager(con, scanner);

            while (true) {
                System.out.println("\n*** Welcome To Banking System ***");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter Your Choice: ");

                int choice;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.next(); // Clear buffer
                    continue;
                }

                switch (choice) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        String email = user.login();
                        if (email != null) {
                            System.out.println("\nUser Logged IN Successfully!");

                            if (!accounts.account_exist(email)) {
                                System.out.println("\n1. Open a New Bank Account");
                                System.out.println("2. Exit");
                                System.out.print("Enter your choice: ");

                                int subChoice = scanner.nextInt();
                                if (subChoice == 1) {
                                    long accountNumber = accounts.openAccount(email);
                                    System.out.println("Account Created Successfully!");
                                    System.out.println("Your Account Number is: " + accountNumber);
                                } else {
                                    break;
                                }
                            }

                            long accountNumber = accounts.getAccountNumber(email);
                            int choice2 = 0;
                            while (choice2 != 5) {
                                System.out.println("\n1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Log Out");
                                System.out.print("Enter your choice: ");

                                try {
                                    choice2 = scanner.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input! Please enter a number.");
                                    scanner.next();
                                    continue;
                                }

                                switch (choice2) {
                                    case 1:
                                        accountManager.debit_amount(accountNumber);
                                        break;
                                    case 2:
                                        accountManager.credit_amount(accountNumber);
                                        break;
                                    case 3:
                                        accountManager.transfer_money(accountNumber);
                                        break;
                                    case 4:
                                        accountManager.getBalance(accountNumber);
                                        break;
                                    case 5:
                                        System.out.println("Logging out...");
                                        break;
                                    default:
                                        System.out.println("Invalid choice! Please enter a number between 1-5.");
                                }
                            }
                        } else {
                            System.out.println("Incorrect Email or Password!");
                        }
                        break;
                    case 3:
                        System.out.println("Thank you for using the Banking System!");
                        System.out.println("Exiting System...");
                        return;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1-3.");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}
