package a;

import java.sql.*;
import java.util.Scanner;

public class department {
    public static void main(String[] args) {
        // Replace with your database details
        String url = "jdbc:mysql://localhost:3306/dept"; // Replace with your database name
        String user = "root"; // Your database username
        String password = ""; // Your database password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

//            // Create the Subject table if it doesn't exist
//            Statement statement = connection.createStatement();
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS Subject (" +
//                                    "Code VARCHAR(10) PRIMARY KEY, " +
//                                    "Name VARCHAR(100), " +
//                                    "Department VARCHAR(50), " +
//                                    "Credits INT)";
//            statement.executeUpdate(createTableSQL);
//            System.out.println("Subject table is ready!");
//
//            // Insert rows into the Subject table using user input
//            String insertSQL = "INSERT INTO Subject (Code, Name, Department, Credits) VALUES (?, ?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
//
           Scanner scanner = new Scanner(System.in);
//
//            System.out.print("How many subjects do you want to insert? ");
//            int numberOfSubjects = scanner.nextInt();
//            scanner.nextLine(); // Consume the newline character
//
//            for (int i = 0; i < numberOfSubjects; i++) {
//                System.out.println("Enter details for subject " + (i + 1) + ":");
//                System.out.print("Code: ");
//                String code = scanner.nextLine();
//
//                System.out.print("Name: ");
//                String name = scanner.nextLine();
//
//                System.out.print("Department: ");
//                String department = scanner.nextLine();
//
//                System.out.print("Credits: ");
//                int credits = scanner.nextInt();
//                scanner.nextLine(); // Consume the newline character
//
//                // Set values for the PreparedStatement
//                preparedStatement.setString(1, code);
//                preparedStatement.setString(2, name);
//                preparedStatement.setString(3, department);
//                preparedStatement.setInt(4, credits);
//
//                // Execute the insertion
//                preparedStatement.executeUpdate();
//                System.out.println("Subject " + code + " inserted successfully.\n");
//            }
//
//            System.out.println("All subject details inserted successfully!");

            // i. Update the Name of the subject
            String selectSQL = "SELECT * FROM Subject";
            Statement updatableStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = updatableStatement.executeQuery(selectSQL);

            while (resultSet.next()) {
                if (resultSet.getString("Code").equals("CSL56")) {
                    resultSet.updateString("Name", "Advanced Java Programming Lab");
                    resultSet.updateRow();
                    System.out.println("Updated Name of subject with Code CSL56.");
                }
            }

            // ii. Delete a subject using user input
            System.out.print("Enter the name of the subject to delete: ");
            String subjectToDelete = scanner.nextLine();

            String deleteSQL = "DELETE FROM Subject WHERE Name = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setString(1, subjectToDelete);
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Deleted the subject '" + subjectToDelete + "' successfully.");
            } else {
                System.out.println("No subject found with the name '" + subjectToDelete + "'.");
            }

            // iii. Display details of all the Subjects
            resultSet = updatableStatement.executeQuery(selectSQL);
            System.out.println("\nDetails of all Subjects:");
            System.out.printf("%-10s %-30s %-20s %-10s%n", "Code", "Name", "Department", "Credits");
            System.out.println("---------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-10s %-30s %-20s %-10d%n",
                        resultSet.getString("Code"),
                        resultSet.getString("Name"),
                        resultSet.getString("Department"),
                        resultSet.getInt("Credits"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
