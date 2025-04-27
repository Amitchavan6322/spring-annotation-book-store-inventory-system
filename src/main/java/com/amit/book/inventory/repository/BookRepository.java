package com.amit.book.inventory.repository;

import com.amit.book.inventory.model.Book;
import com.amit.book.inventory.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

@AllArgsConstructor
@Repository
public class BookRepository {

    private static Connection connection = null;

    private void initConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = new ConnectionService().getConnection();
        }
    }

    public boolean fillTheBookInfo(Book book) throws SQLException {
        this.initConnection();
        String query = "INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setInt(5, book.getNoOfCopies());
            preparedStatement.setString(6, String.valueOf(book.getCategory()));
            preparedStatement.setString(7, book.getStoreLocation());
            preparedStatement.setInt(8, book.getPrice());

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void displayTheBookInfo() throws SQLException {
        this.initConnection();
        String query = "SELECT * FROM book";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            // Setting column widths
            int idWidth = 10;
            int nameWidth = 20;
            int authorWidth = 20;
            int publisherWidth = 20;
            int copiesWidth = 10;
            int categoryWidth = 15;
            int locationWidth = 15;
            int priceWidth = 10;

            // Print the header
            System.out.printf("%-" + idWidth + "s%-" + nameWidth + "s%-" + authorWidth + "s%-" + publisherWidth + "s%-" + copiesWidth + "s%-" + categoryWidth + "s%-" + locationWidth + "s%-" + priceWidth + "s%n",
                    "Book ID", "Name", "Author", "Publisher", "Copies", "Category", "Location", "Price");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

            // Print the book information in a table format
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_Id");
                String name = resultSet.getString("book_name");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                int noOfCopies = resultSet.getInt("no_of_copies");
                String category = resultSet.getString("category");
                String storeLocation = resultSet.getString("store_location");
                int price = resultSet.getInt("price");

                // Print each row of the result set in the specified format
                System.out.printf("%-" + idWidth + "d%-" + nameWidth + "s%-" + authorWidth + "s%-" + publisherWidth + "s%-" + copiesWidth + "d%-" + categoryWidth + "s%-" + locationWidth + "s%-" + priceWidth + "d%n",
                        bookId, name, author, publisher, noOfCopies, category, storeLocation, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getBookById(int book_Id) throws SQLException {
        this.initConnection();
        String query = "SELECT * FROM book where book_id = " + book_Id;
        System.out.println("query inside getBookById = " + query);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            // Setting column widths
            int idWidth = 10;
            int nameWidth = 20;
            int authorWidth = 20;
            int publisherWidth = 20;
            int copiesWidth = 10;
            int categoryWidth = 15;
            int locationWidth = 15;
            int priceWidth = 10;

            // Print the header
            System.out.printf("%-" + idWidth + "s%-" + nameWidth + "s%-" + authorWidth + "s%-" + publisherWidth + "s%-" + copiesWidth + "s%-" + categoryWidth + "s%-" + locationWidth + "s%-" + priceWidth + "s%n",
                    "Book ID", "Name", "Author", "Publisher", "Copies", "Category", "Location", "Price");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

            // Print the book information in a table format
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_Id");
                String name = resultSet.getString("book_name");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                int noOfCopies = resultSet.getInt("no_of_copies");
                String category = resultSet.getString("category");
                String storeLocation = resultSet.getString("store_location");
                int price = resultSet.getInt("price");

                // Print each row of the result set in the specified format
                System.out.printf("%-" + idWidth + "d%-" + nameWidth + "s%-" + authorWidth + "s%-" + publisherWidth + "s%-" + copiesWidth + "d%-" + categoryWidth + "s%-" + locationWidth + "s%-" + priceWidth + "d%n",
                        bookId, name, author, publisher, noOfCopies, category, storeLocation, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteBookById(int book_id) throws SQLException {
        this.initConnection();
        String query = "DELETE FROM book where book_id = ?";
        System.out.println("query inside deleteBookById = " + query);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, book_id); // Set the parameter for book_id
            int affectedRows = preparedStatement.executeUpdate(); // Use executeUpdate for DELETE
            if (affectedRows > 0) {
                System.out.println("Book entry for id = " + book_id + " has been deleted successfully");
            } else {
                System.out.println("No book entry found with id = " + book_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) connection.close(); // Ensure connection is closed
        }
    }

    public boolean isBookTableEmpty() throws SQLException {
        this.initConnection();
        String query = "SELECT COUNT(*) FROM book";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateBookInfo(Book book) throws SQLException {

        String query = "UPDATE book SET book_name = ?, author = ?, publisher = ?, no_of_copies = ?, category = ?, store_location = ?, price = ? WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setInt(4, book.getNoOfCopies());
            preparedStatement.setString(5, String.valueOf(book.getCategory()));
            preparedStatement.setString(6, book.getStoreLocation());
            preparedStatement.setInt(7, book.getPrice());
            preparedStatement.setInt(8, book.getBookId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if update was successful
        }
    }

    public boolean isBookExists(int bookId) throws SQLException {
        this.initConnection();
        String query = "SELECT COUNT(*) FROM book WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Returns true if book exists
                }
            }
        }
        return false;
    }
}



