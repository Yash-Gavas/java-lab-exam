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

            // Create the Country table if it doesn't exist
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Country (" +
                                    "Country_Code VARCHAR(10) PRIMARY KEY, " +
                                    "Capital VARCHAR(100), " +
                                    "Continent VARCHAR(50), " +
                                    "Population BIGINT)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Country table is ready!");

            // Insert rows into the Country table using user input
            String insertSQL = "INSERT INTO Country (Country_Code, Capital, Continent, Population) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            Scanner scanner = new Scanner(System.in);

            System.out.print("How many countries do you want to insert? ");
            int numberOfCountries = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            for (int i = 0; i < numberOfCountries; i++) {
                System.out.println("Enter details for country " + (i + 1) + ":");
                System.out.print("Country Code: ");
                String countryCode = scanner.nextLine();

                System.out.print("Capital: ");
                String capital = scanner.nextLine();

                System.out.print("Continent: ");
                String continent = scanner.nextLine();

                System.out.print("Population: ");
                long population = scanner.nextLong();
                scanner.nextLine(); // Consume the newline character

                // Set values for the PreparedStatement
                preparedStatement.setString(1, countryCode);
                preparedStatement.setString(2, capital);
                preparedStatement.setString(3, continent);
                preparedStatement.setLong(4, population);

                // Execute the insertion
                preparedStatement.executeUpdate();
                System.out.println("Country " + countryCode + " inserted successfully.\n");
            }

            System.out.println("All country details inserted successfully!");

            // Display the country details in ascending order of population
            String selectSQL = "SELECT * FROM Country ORDER BY Population ASC";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            System.out.println("\nCountry Details in Ascending Order of Population:");
            System.out.printf("%-15s %-20s %-20s %-15s%n", "Country Code", "Capital", "Continent", "Population");
            System.out.println("--------------------------------------------------------------");

            while (resultSet.next()) {
                System.out.printf("%-15s %-20s %-20s %-15d%n",
                        resultSet.getString("Country_Code"),
                        resultSet.getString("Capital"),
                        resultSet.getString("Continent"),
                        resultSet.getLong("Population"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
