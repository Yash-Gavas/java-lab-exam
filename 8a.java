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

            // Create the StudentInfo table if it doesn't exist
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS StudentInfo (" +
                                    "student_name VARCHAR(100), " +
                                    "usn VARCHAR(20) PRIMARY KEY, " +
                                    "sem INT, " +
                                    "course VARCHAR(50), " +
                                    "grade CHAR(1))";
            statement.executeUpdate(createTableSQL);
            System.out.println("StudentInfo table is ready!");

            // Insert rows into the StudentInfo table using user input
            String insertSQL = "INSERT INTO StudentInfo (student_name, usn, sem, course, grade) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            Scanner scanner = new Scanner(System.in);

            System.out.print("How many students do you want to insert? ");
            int numberOfStudents = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            for (int i = 0; i < numberOfStudents; i++) {
                System.out.println("Enter details for student " + (i + 1) + ":");
                System.out.print("Student Name: ");
                String studentName = scanner.nextLine();

                System.out.print("USN: ");
                String usn = scanner.nextLine();

                System.out.print("Semester: ");
                int sem = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                System.out.print("Course: ");
                String course = scanner.nextLine();

                System.out.print("Grade: ");
                String grade = scanner.nextLine();

                // Set values for the PreparedStatement
                preparedStatement.setString(1, studentName);
                preparedStatement.setString(2, usn);
                preparedStatement.setInt(3, sem);
                preparedStatement.setString(4, course);
                preparedStatement.setString(5, grade);

                // Execute the insertion
                preparedStatement.executeUpdate();
                System.out.println("Student " + studentName + " inserted successfully.\n");
            }

            System.out.println("All student details inserted successfully!");

            // Update grade from 'A' to 'S' for student name "Ram"
            String updateSQL = "UPDATE StudentInfo SET grade = ? WHERE student_name = ? AND grade = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
            updateStatement.setString(1, "S");
            updateStatement.setString(2, "Ram");
            updateStatement.setString(3, "A");

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Updated grade from 'A' to 'S' for student 'Ram'.");
            } else {
                System.out.println("No student named 'Ram' with grade 'A' found.");
            }

            // Display all student details
            String selectSQL = "SELECT * FROM StudentInfo";
            ResultSet resultSet = statement.executeQuery(selectSQL);
            System.out.println("\nDetails of all Students:");
            System.out.printf("%-20s %-20s %-10s %-20s %-10s%n", "Student Name", "USN", "Semester", "Course", "Grade");
            System.out.println("-----------------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-20s %-20s %-10d %-20s %-10s%n",
                        resultSet.getString("student_name"),
                        resultSet.getString("usn"),
                        resultSet.getInt("sem"),
                        resultSet.getString("course"),
                        resultSet.getString("grade"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
