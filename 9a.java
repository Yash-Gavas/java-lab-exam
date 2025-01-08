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

            // Create the 'student1' table if it doesn't exist
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS student1 (" +
                                    "roll_no VARCHAR(20) PRIMARY KEY, " +
                                    "name VARCHAR(100), " +
                                    "program VARCHAR(50), " +
                                    "year_of_admission INT)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Student1 table is ready!");

            // Insert rows into the 'student1' table
            String insertSQL = "INSERT INTO student1 (roll_no, name, program, year_of_admission) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // Inserting sample data
            preparedStatement.setString(1, "BE12345");
            preparedStatement.setString(2, "John Doe");
            preparedStatement.setString(3, "BE");
            preparedStatement.setInt(4, 2023);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "BE67890");
            preparedStatement.setString(2, "Alice Smith");
            preparedStatement.setString(3, "BE");
            preparedStatement.setInt(4, 2022);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "ME54321");
            preparedStatement.setString(2, "Bob Johnson");
            preparedStatement.setString(3, "ME");
            preparedStatement.setInt(4, 2023);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "BE98765");
            preparedStatement.setString(2, "Ram Kumar");
            preparedStatement.setString(3, "BE");
            preparedStatement.setInt(4, 2023);
            preparedStatement.executeUpdate();

            System.out.println("Sample student details inserted successfully!");

            // i. List the name and roll number of all the students enrolled in the year 2023
            String selectSQL = "SELECT roll_no, name FROM student1 WHERE year_of_admission = 2023";
            ResultSet resultSet = statement.executeQuery(selectSQL);
            System.out.println("\nStudents enrolled in the year 2023:");
            System.out.printf("%-20s %-30s%n", "Roll Number", "Name");
            System.out.println("-------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-20s %-30s%n", resultSet.getString("roll_no"), resultSet.getString("name"));
            }

            // ii. List the roll number and name of all students in the BE program
            selectSQL = "SELECT roll_no, name FROM student1 WHERE program = 'BE'";
            resultSet = statement.executeQuery(selectSQL);
            System.out.println("\nStudents enrolled in BE program:");
            System.out.printf("%-20s %-30s%n", "Roll Number", "Name");
            System.out.println("-------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-20s %-30s%n", resultSet.getString("roll_no"), resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
