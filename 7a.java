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

            // Create the Student table if it doesn't exist
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Student (" +
                                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                                    "Name VARCHAR(100), " +
                                    "Department VARCHAR(50), " +
                                    "CGPA DOUBLE)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Student table is ready!");

            // Insert rows into the Student table using user input
            String insertSQL = "INSERT INTO Student (Name, Department, CGPA) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            Scanner scanner = new Scanner(System.in);

            System.out.print("How many students do you want to insert? ");
            int numberOfStudents = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            for (int i = 0; i < numberOfStudents; i++) {
                System.out.println("Enter details for student " + (i + 1) + ":");
                System.out.print("Name: ");
                String name = scanner.nextLine();

                System.out.print("Department: ");
                String department = scanner.nextLine();

                System.out.print("CGPA: ");
                double cgpa = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character

                // Set values for the PreparedStatement
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, department);
                preparedStatement.setDouble(3, cgpa);

                // Execute the insertion
                preparedStatement.executeUpdate();
                System.out.println("Student " + name + " inserted successfully.\n");
            }

            System.out.println("All student details inserted successfully!");

            // Display students with CGPA less than 9
            String selectSQL = "SELECT * FROM Student WHERE CGPA < 9";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            System.out.println("\nStudents with CGPA less than 9:");
            System.out.printf("%-10s %-20s %-20s %-10s%n", "ID", "Name", "Department", "CGPA");
            System.out.println("-----------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-10d %-20s %-20s %-10.2f%n",
                        resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Department"),
                        resultSet.getDouble("CGPA"));
            }

            // Update the CGPA of a student named "John" using an updatable result set
            String updatableSQL = "SELECT * FROM Student WHERE Name = 'John'";
            Statement updatableStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = updatableStatement.executeQuery(updatableSQL);

            if (resultSet.next()) {
                double oldCGPA = resultSet.getDouble("CGPA");
                if (oldCGPA == 8.50) {
                    resultSet.updateDouble("CGPA", 9.4);
                    resultSet.updateRow();
                    System.out.println("\nUpdated CGPA of 'John' from 8.50 to 9.4.");
                } else {
                    System.out.println("\nJohn's CGPA is not 8.50. No update performed.");
                }
            } else {
                System.out.println("\nNo student named 'John' found.");
            }

            // Display all student details
            resultSet = statement.executeQuery("SELECT * FROM Student");
            System.out.println("\nDetails of all Students:");
            System.out.printf("%-10s %-20s %-20s %-10s%n", "ID", "Name", "Department", "CGPA");
            System.out.println("-----------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-10d %-20s %-20s %-10.2f%n",
                        resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Department"),
                        resultSet.getDouble("CGPA"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
