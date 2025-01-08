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

            // Create the emp1 table if it doesn't exist
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS emp1 (" +
                                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                    "name VARCHAR(100), " +
                                    "designation VARCHAR(100), " +
                                    "department VARCHAR(50), " +
                                    "salary DECIMAL(10, 2), " +
                                    "project VARCHAR(100))";
            statement.executeUpdate(createTableSQL);
            System.out.println("emp1 table is ready!");

            // Insert employee details from user input
            String insertSQL = "INSERT INTO emp1 (name, designation, department, salary, project) VALUES (?, ?, ?, ?, ?)";
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
                String department = scanner.nextLine();

                System.out.print("Salary: ");
                double salary = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character

                System.out.print("Project: ");
                String project = scanner.nextLine();

                // Set values for the PreparedStatement
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, designation);
                preparedStatement.setString(3, department);
                preparedStatement.setDouble(4, salary);
                preparedStatement.setString(5, project);

                // Execute the insertion
                preparedStatement.executeUpdate();
                System.out.println("Employee " + name + " inserted successfully.\n");
            }

            // i. Delete records of employees working on a project specified by the user
            System.out.print("Enter the project name to delete employees from: ");
            String projectToDelete = scanner.nextLine();

            String deleteSQL = "DELETE FROM emp1 WHERE project = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setString(1, projectToDelete);
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Deleted employees working on project: " + projectToDelete);
            } else {
                System.out.println("No employees found for the project: " + projectToDelete);
            }

            // ii. Retrieve and display details of all employees sorted by their salary in descending order
            String selectSQL = "SELECT * FROM emp1 ORDER BY salary DESC";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            System.out.println("\nEmployee details sorted by salary (descending):");
            System.out.printf("%-5s %-30s %-20s %-15s %-10s %-20s%n", "ID", "Name", "Designation", "Department", "Salary", "Project");
            System.out.println("-----------------------------------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-5d %-30s %-20s %-15s %-10.2f %-20s%n",
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("designation"),
                        resultSet.getString("department"),
                        resultSet.getDouble("salary"),
                        resultSet.getString("project"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
