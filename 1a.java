package a;

import java.sql.*;
import java.util.Scanner;

public class Department {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dept";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             Scanner sc = new Scanner(System.in)) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS dept");
            stmt.executeUpdate("USE dept");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Department (Dept_ID INT PRIMARY KEY, Name VARCHAR(100), Year_Established INT, Head_Name VARCHAR(100), No_of_Employees INT)");

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Department");
            rs.next();
            if (rs.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO Department VALUES (1, 'HR', 1995, 'Alice', 20), (2, 'IT', 2000, 'Bob', 50), (3, 'Finance', 2010, 'Charlie', 30), (4, 'Marketing', 2015, 'Diana', 25)");
            }

            displayDepartments(stmt);

            System.out.print("\nEnter year to filter: ");
            int year = sc.nextInt();
            displayFilteredDepartments(conn, "SELECT * FROM Department WHERE Year_Established = ?", year);

            System.out.print("\nEnter Dept_ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Dept Name: ");
            String name = sc.nextLine();
            displayFilteredDepartments(conn, "SELECT * FROM Department WHERE Dept_ID = ? AND Name = ?", id, name);

            System.out.print("\nEnter new Dept_ID: ");
            int newId = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Dept Name: ");
            String newName = sc.nextLine();
            System.out.print("Enter Year Established: ");
            int newYear = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Head Name: ");
            String head = sc.nextLine();
            System.out.print("Enter No. of Employees: ");
            int employees = sc.nextInt();
            
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Department VALUES (?, ?, ?, ?, ?)");) {
                ps.setInt(1, newId);
                ps.setString(2, newName);
                ps.setInt(3, newYear);
                ps.setString(4, head);
                ps.setInt(5, employees);
                ps.executeUpdate();
            }
            System.out.println("New department added!");
            displayDepartments(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayDepartments(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM Department");
        System.out.println("\nAll Departments:");
        while (rs.next()) {
            System.out.printf("Dept_ID: %d, Name: %s, Year: %d, Head: %s, Employees: %d\n",
                    rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5));
        }
    }

    private static void displayFilteredDepartments(Connection conn, String query, Object... params) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Integer) {
                    ps.setInt(i + 1, (int) params[i]);
                } else {
                    ps.setString(i + 1, (String) params[i]);
                }
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("No matching departments found.");
            } else {
                do {
                    System.out.printf("Dept_ID: %d, Name: %s, Year: %d, Head: %s, Employees: %d\n",
                            rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5));
                } while (rs.next());
            }
        }
    }
}
