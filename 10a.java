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

            // Create the 'student2' table if it doesn't exist
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS student2 (" +
                                    "name VARCHAR(100), " +
                                    "usn VARCHAR(20) PRIMARY KEY, " +
                                    "branch VARCHAR(50), " +
                                    "admission_method VARCHAR(50))";
            statement.executeUpdate(createTableSQL);
            System.out.println("Student2 table is ready!");

            // Insert student details from user input
            String insertSQL = "INSERT INTO student2 (name, usn, branch, admission_method) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of students to insert: ");
            int numberOfStudents = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            for (int i = 0; i < numberOfStudents; i++) {
                System.out.println("Enter details for student " + (i + 1) + ":");
                System.out.print("Name: ");
                String name = scanner.nextLine();

                System.out.print("USN: ");
                String usn = scanner.nextLine();

                System.out.print("Branch: ");
                String branch = scanner.nextLine();

                System.out.print("Admission Method (CET/COMED_K): ");
                String admissionMethod = scanner.nextLine();

                // Set values for the PreparedStatement
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, usn);
                preparedStatement.setString(3, branch);
                preparedStatement.setString(4, admissionMethod);

                // Execute the insertion
                preparedStatement.executeUpdate();
                System.out.println("Student " + name + " inserted successfully.\n");
            }

            // Extract and display students admitted through CET and belonging to CSE branch
            String selectSQL = "SELECT name, usn, branch, admission_method FROM student2 WHERE admission_method = 'CET' AND branch = 'CSE'";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            System.out.println("\nStudents admitted through CET in CSE branch:");
            System.out.printf("%-30s %-20s %-20s %-20s%n", "Name", "USN", "Branch", "Admission Method");
            System.out.println("---------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-30s %-20s %-20s %-20s%n",
                        resultSet.getString("name"),
                        resultSet.getString("usn"),
                        resultSet.getString("branch"),
                        resultSet.getString("admission_method"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
