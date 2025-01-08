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

            // Create the Employee table if it doesn't exist
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Employee (" +
                                    "Name VARCHAR(100), " +
                                    "Designation VARCHAR(50), " +
                                    "Department VARCHAR(50), " +
                                    "Salary DOUBLE)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Employee table is ready!");

            // Insert rows into the Employee table using user input
            String insertSQL = "INSERT INTO Employee (Name, Designation, Department, Salary) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            Scanner scanner = new Scanner(System.in);

            System.out.print("How many employees do you want to insert? ");
            int numberOfEmployees = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            for (int i = 0; i < numberOfEmployees; i++) {
                System.out.println("Enter details for employee " + (i + 1) + ":");
                System.out.print("Name: ");
                String name = scanner.nextLine();

                System.out.print("Designation: ");
                String designation = scanner.nextLine();

                System.out.print("Department: ");
                String department = scanner.nextLine();

                System.out.print("Salary: ");
                double salary = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character

                // Set values for the PreparedStatement
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, designation);
                preparedStatement.setString(3, department);
                preparedStatement.setDouble(4, salary);

                // Execute the insertion
                preparedStatement.executeUpdate();
                System.out.println("Employee " + name + " inserted successfully.\n");
            }

            System.out.println("All employee details inserted successfully!");

            // Update the designation of an employee
            System.out.print("Enter the name of the employee whose designation you want to update: ");
            String employeeName = scanner.nextLine();

            String updateSQL = "UPDATE Employee SET Designation = ? WHERE Name = ? AND Designation = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSQL);

            updateStatement.setString(1, "Associate Professor");
            updateStatement.setString(2, employeeName);
            updateStatement.setString(3, "Assistant Professor");

            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Updated designation of employee '" + employeeName + "' successfully.");
            } else {
                System.out.println("No employee found with the name '" + employeeName + "' and designation 'Assistant Professor'.");
            }

            // Display all employee details
            String selectSQL = "SELECT * FROM Employee";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            System.out.println("\nDetails of all Employees:");
            System.out.printf("%-20s %-20s %-20s %-10s%n", "Name", "Designation", "Department", "Salary");
            System.out.println("---------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-20s %-20s %-20s %-10.2f%n",
                        resultSet.getString("Name"),
                        resultSet.getString("Designation"),
                        resultSet.getString("Department"),
                        resultSet.getDouble("Salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
