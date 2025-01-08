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

            // Create the 'emp' table if it doesn't exist
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS emp (" +
                                    "name VARCHAR(100), " +
                                    "designation VARCHAR(100), " +
                                    "dept VARCHAR(50), " +
                                    "salary DECIMAL(10, 2))";
            statement.executeUpdate(createTableSQL);
            System.out.println("emp table is ready!");

            // Insert employee details from user input
            String insertSQL = "INSERT INTO emp (name, designation, dept, salary) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of employees to insert: ");
            int numberOfEmployees = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            for (int i = 0; i < numberOfEmployees; i++) {
                System.out.println("Enter details for employee " + (i + 1) + ":");
                System.out.print("Name: ");
                String name = scanner.nextLine();

                System.out.print("Designation: ");
                String designation = scanner.nextLine();

                System.out.print("Department: ");
                String dept = scanner.nextLine();

                System.out.print("Salary: ");
                double salary = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character

                // Set values for the PreparedStatement
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, designation);
                preparedStatement.setString(3, dept);
                preparedStatement.setDouble(4, salary);

                // Execute the insertion
                preparedStatement.executeUpdate();
                System.out.println("Employee " + name + " inserted successfully.\n");
            }

            // Display employees with salary above 50,000
            String selectSQL = "SELECT name, designation, dept, salary FROM emp WHERE salary > 50000";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            System.out.println("\nEmployees with salary above Rs. 50,000:");
            System.out.printf("%-30s %-30s %-20s %-10s%n", "Name", "Designation", "Department", "Salary");
            System.out.println("---------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-30s %-30s %-20s %-10.2f%n",
                        resultSet.getString("name"),
                        resultSet.getString("designation"),
                        resultSet.getString("dept"),
                        resultSet.getDouble("salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
