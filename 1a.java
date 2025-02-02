package a;

import java.sql.*;
import java.util.Scanner;

public class Department {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/dept";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String createDB = "CREATE DATABASE IF NOT EXISTS dept";
            stmt.executeUpdate(createDB);

            String useDB = "USE dept";
            stmt.executeUpdate(useDB);

            String createTable = "CREATE TABLE IF NOT EXISTS Department (" +
                    "Dept_ID INT PRIMARY KEY, " +
                    "Name VARCHAR(100) NOT NULL, " +
                    "Year_Established INT NOT NULL, " +
                    "Head_Name VARCHAR(100) NOT NULL, " +
                    "No_of_Employees INT NOT NULL)";
            stmt.executeUpdate(createTable);

            String checkData = "SELECT COUNT(*) FROM Department";
            rs = stmt.executeQuery(checkData);
            rs.next();
            int rowCount = rs.getInt(1);

            if (rowCount == 0) {
                String insertData = "INSERT INTO Department (Dept_ID, Name, Year_Established, Head_Name, No_of_Employees) VALUES " +
                        "(1, 'HR', 1995, 'Alice Johnson', 20), " +
                        "(2, 'IT', 2000, 'Bob Smith', 50), " +
                        "(3, 'Finance', 2010, 'Charlie Brown', 30), " +
                        "(4, 'Marketing', 2015, 'Diana Prince', 25)";
                stmt.executeUpdate(insertData);
                System.out.println("Initial data inserted.");
            }

            String queryAll = "SELECT * FROM Department";
            rs = stmt.executeQuery(queryAll);
            System.out.println("\nAll Departments:");
            while (rs.next()) {
                System.out.println("Dept_ID: " + rs.getInt("Dept_ID") +
                        ", Name: " + rs.getString("Name") +
                        ", Year_Established: " + rs.getInt("Year_Established") +
                        ", Head_Name: " + rs.getString("Head_Name") +
                        ", No_of_Employees: " + rs.getInt("No_of_Employees"));
            }

            System.out.print("\nEnter year to filter: ");
            int year = sc.nextInt();
            String queryByYear = "SELECT * FROM Department WHERE Year_Established = ?";
            pstmt = conn.prepareStatement(queryByYear);
            pstmt.setInt(1, year);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\nDepartments established in " + year + ":");
                do {
                    System.out.println("Dept_ID: " + rs.getInt("Dept_ID") +
                            ", Name: " + rs.getString("Name") +
                            ", Year_Established: " + rs.getInt("Year_Established") +
                            ", Head_Name: " + rs.getString("Head_Name") +
                            ", No_of_Employees: " + rs.getInt("No_of_Employees"));
                } while (rs.next());
            } else {
                System.out.println("No departments found for year " + year);
            }

            System.out.print("\nEnter Dept_ID: ");
            int deptId = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Department Name: ");
            String deptName = sc.nextLine();

            String queryByIdName = "SELECT * FROM Department WHERE Dept_ID = ? AND Name = ?";
            pstmt = conn.prepareStatement(queryByIdName);
            pstmt.setInt(1, deptId);
            pstmt.setString(2, deptName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\nDepartment Details:");
                do {
                    System.out.println("Dept_ID: " + rs.getInt("Dept_ID") +
                            ", Name: " + rs.getString("Name") +
                            ", Year_Established: " + rs.getInt("Year_Established") +
                            ", Head_Name: " + rs.getString("Head_Name") +
                            ", No_of_Employees: " + rs.getInt("No_of_Employees"));
                } while (rs.next());
            } else {
                System.out.println("No matching department found.");
            }

            System.out.print("\nEnter new Dept_ID: ");
            int newDeptId = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new Department Name: ");
            String newDeptName = sc.nextLine();
            System.out.print("Enter Year Established: ");
            int newYear = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Head Name: ");
            String newHead = sc.nextLine();
            System.out.print("Enter Number of Employees: ");
            int newEmployees = sc.nextInt();

            String insertQuery = "INSERT INTO Department (Dept_ID, Name, Year_Established, Head_Name, No_of_Employees) " +
                    "VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, newDeptId);
            pstmt.setString(2, newDeptName);
            pstmt.setInt(3, newYear);
            pstmt.setString(4, newHead);
            pstmt.setInt(5, newEmployees);

            int rows = pstmt.executeUpdate();
            System.out.println("\nNew department inserted, " + rows + " row(s) affected.");

            rs = stmt.executeQuery(queryAll);
            System.out.println("\nUpdated list of all Departments:");
            while (rs.next()) {
                System.out.println("Dept_ID: " + rs.getInt("Dept_ID") +
                        ", Name: " + rs.getString("Name") +
                        ", Year_Established: " + rs.getInt("Year_Established") +
                        ", Head_Name: " + rs.getString("Head_Name") +
                        ", No_of_Employees: " + rs.getInt("No_of_Employees"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                if (sc != null) sc.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
