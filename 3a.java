package a;
import java.sql.*;
import java.util.Scanner;

public class department {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/dept"; // Replace with your database URL
        String user = "root"; // Replace with your database username
        String password = ""; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Create the Bank_Account table if it doesn't exist
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS Bank_Account (" +
//                                    "Account_No INT PRIMARY KEY, " +
//                                    "Account_Name VARCHAR(100), " +
//                                    "Type_of_Account VARCHAR(50), " +
//                                    "Balance DOUBLE)";
           Statement statement = connection.createStatement();
//            statement.executeUpdate(createTableSQL);
//            System.out.println("Bank_Account table is ready!");
//
//            // Disable auto-commit for transaction control
//            connection.setAutoCommit(false);

            // i. Insert rows using PreparedStatement
            String insertSQL = "INSERT INTO Bank_Account (Account_No, Account_Name, Type_of_Account, Balance) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // Get user input for inserting new rows
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("Enter Account Number: ");
//            int accountNo = scanner.nextInt();
//            scanner.nextLine();  // Consume the newline left by nextInt()
//            System.out.print("Enter Account Name: ");
//            String accountName = scanner.nextLine();
//            System.out.print("Enter Type of Account: ");
//            String accountType = scanner.nextLine();
//            System.out.print("Enter Balance: ");
//            double balance = scanner.nextDouble();
//
//            // Set values for the PreparedStatement and execute it
//            preparedStatement.setInt(1, accountNo);
//            preparedStatement.setString(2, accountName);
//            preparedStatement.setString(3, accountType);
//            preparedStatement.setDouble(4, balance);
//            preparedStatement.executeUpdate();
//            System.out.println("Account inserted successfully.");
//
//            // ii. Display details of all the accounts
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM Bank_Account");
//            System.out.println("\nAll Bank Accounts:");
//            while (resultSet.next()) {
//                System.out.println("Account No: " + resultSet.getInt("Account_No") +
//                        ", Account Name: " + resultSet.getString("Account_Name") +
//                        ", Type of Account: " + resultSet.getString("Type_of_Account") +
//                        ", Balance: " + resultSet.getDouble("Balance"));
//            }

            // iii. Demonstrate the Working of Rollback and Commit
//            try {
//                // Simulate a transaction
//                preparedStatement.setInt(1, 101); // New Account No
//                preparedStatement.setString(2, "John Doe");
//                preparedStatement.setString(3, "Savings");
//                preparedStatement.setDouble(4, 1000.0);
//                preparedStatement.executeUpdate();
//                System.out.println("Inserted Account 101.");
//
//                // Simulate an error by trying to insert an account with a duplicate Account No
//                preparedStatement.setInt(1, 101); // Duplicate Account No
//                preparedStatement.setString(2, "Jane Doe");
//                preparedStatement.setString(3, "Current");
//                preparedStatement.setDouble(4, 2000.0);
//                preparedStatement.executeUpdate();
//                System.out.println("Inserted Account 101 again (this will cause an error).");
//
//                // Commit the transaction if no error occurs
//                connection.commit();
//                System.out.println("Transaction committed.");
//            } catch (SQLException e) {
//                // Rollback if an error occurs
//                System.out.println("Error occurred, rolling back transaction.");
//                connection.rollback();
//            }

            // iv. Demonstrate the Working of SavePoints
            Savepoint savepoint = connection.setSavepoint("Savepoint1");

            try {
                // Insert another account
                preparedStatement.setInt(1, 102);
                preparedStatement.setString(2, "Alice Smith");
                preparedStatement.setString(3, "Current");
                preparedStatement.setDouble(4, 1500.0);
                preparedStatement.executeUpdate();
                System.out.println("Inserted Account 102.");

                // Insert another account, but simulate an error by using an invalid value
                preparedStatement.setInt(1, 103);
                preparedStatement.setString(2, "Bob Brown");
                preparedStatement.setString(3, "Savings");
                preparedStatement.setDouble(4, -500.0); // Invalid balance (negative value)
                preparedStatement.executeUpdate();
                System.out.println("Inserted Account 103 (this will cause an error).");

                // If error occurs, rollback to the savepoint
                connection.rollback(savepoint);
                System.out.println("Rolled back to savepoint. Account 102 remains, Account 103 is not inserted.");
            } catch (SQLException e) {
                // Handle any errors
                System.out.println("Error occurred: " + e.getMessage());
            }

            // Display the final list of accounts
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Bank_Account");
            System.out.println("\nFinal Bank Accounts after Commit/Rollback:");
            while (resultSet.next()) {
                System.out.println("Account No: " + resultSet.getInt("Account_No") +
                        ", Account Name: " + resultSet.getString("Account_Name") +
                        ", Type of Account: " + resultSet.getString("Type_of_Account") +
                        ", Balance: " + resultSet.getDouble("Balance"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
