package a;
import java.sql.*;
import java.util.Scanner;

public class Department {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/dept";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Movies (" +
                                    "ID INT PRIMARY KEY, " +
                                    "Movie_Name VARCHAR(100) NOT NULL, " +
                                    "Genre VARCHAR(50), " +
                                    "IMDB_Rating DOUBLE, " +
                                    "Year INT)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Movies table is ready!");

            String insertInitialDataSQL = "INSERT IGNORE INTO Movies (ID, Movie_Name, Genre, IMDB_Rating, Year) VALUES " +
                                          "(1, 'The Shawshank Redemption', 'Drama', 9.3, 1994), " +
                                          "(2, 'The Godfather', 'Crime', 9.2, 1972), " +
                                          "(3, 'The Dark Knight', 'Action', 9.0, 2008), " +
                                          "(4, '12 Angry Men', 'Drama', 9.0, 1957), " +
                                          "(5, 'Schindler''s List', 'Biography', 8.9, 1993), " +
                                          "(6, 'The Lord of the Rings: The Return of the King', 'Fantasy', 8.9, 2003), " +
                                          "(7, 'Pulp Fiction', 'Crime', 8.9, 1994), " +
                                          "(8, 'The Good, the Bad and the Ugly', 'Western', 8.8, 1966), " +
                                          "(9, 'Forrest Gump', 'Drama', 8.8, 1994), " +
                                          "(10, 'Inception', 'Sci-fi', 8.8, 2010)";
            statement.executeUpdate(insertInitialDataSQL);
            System.out.println("Initial data inserted into Movies table!");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Movies");
            System.out.println("\nAll Movies:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + " | " +
                                   resultSet.getString("Movie_Name") + " | " +
                                   resultSet.getString("Genre") + " | " +
                                   resultSet.getDouble("IMDB_Rating") + " | " +
                                   resultSet.getInt("Year"));
            }
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Movie ID to insert: ");
            int movieID = scanner.nextInt();  
            scanner.nextLine();
            System.out.print("Enter Movie Name: ");
            String movieName = scanner.nextLine();
            System.out.print("Enter Genre: ");
            String genre = scanner.nextLine();
            System.out.print("Enter IMDB Rating: ");
            double imdbRating = scanner.nextDouble();  
            System.out.print("Enter Year: ");
            int year = scanner.nextInt();
            
            String insertSQL = "INSERT INTO Movies (ID, Movie_Name, Genre, IMDB_Rating, Year) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, movieID);
            preparedStatement.setString(2, movieName);
            preparedStatement.setString(3, genre);
            preparedStatement.setDouble(4, imdbRating);
            preparedStatement.setInt(5, year);
            preparedStatement.executeUpdate();
            System.out.println("\nInserted a new movie.");
            
            resultSet = statement.executeQuery("SELECT * FROM Movies");
            System.out.println("Updated Movies List:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + " | " +
                                   resultSet.getString("Movie_Name") + " | " +
                                   resultSet.getString("Genre") + " | " +
                                   resultSet.getDouble("IMDB_Rating") + " | " +
                                   resultSet.getInt("Year"));
            }

            String deleteSQL = "DELETE FROM Movies WHERE IMDB_Rating < 5";
            int rowsDeleted = statement.executeUpdate(deleteSQL);
            System.out.println("\nDeleted " + rowsDeleted + " movies with IMDB_Rating < 5.");
            
            resultSet = statement.executeQuery("SELECT * FROM Movies");
            System.out.println("Movies List After Deletion:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + " | " +
                                   resultSet.getString("Movie_Name") + " | " +
                                   resultSet.getString("Genre") + " | " +
                                   resultSet.getDouble("IMDB_Rating") + " | " +
                                   resultSet.getInt("Year"));
            }

            String updateSQL = "UPDATE Movies SET Genre = ? WHERE ID = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
            updateStatement.setString(1, "Sci-fi");  
            updateStatement.setInt(2, 10);
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("\nUpdated Genre of Movie with ID 10 to Sci-fi.");
            }

            resultSet = statement.executeQuery("SELECT * FROM Movies");
            System.out.println("\nFinal Movies List:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + " | " +
                                   resultSet.getString("Movie_Name") + " | " +
                                   resultSet.getString("Genre") + " | " +
                                   resultSet.getDouble("IMDB_Rating") + " | " +
                                   resultSet.getInt("Year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
