package com.amit.book.inventory.repository;

import com.amit.book.inventory.model.Customer;
import com.amit.book.inventory.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class CustomerRepository {

    private static Connection connection = null;

    private void initConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = new ConnectionService().getConnection();
        }
    }

    public boolean fillCustomerInfo(Customer customer) throws SQLException {
        this.initConnection();
        String query = "INSERT INTO customer VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.setString(2, customer.getCustomerName());
            preparedStatement.setString(3, customer.getCustomerAddress());
            preparedStatement.setLong(4, customer.getCustomerContact());
            preparedStatement.setString(5, customer.getCustomerEmailID());


            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting customer: " + e.getMessage());
            throw new SQLException("Unable to insert customer info", e); // Rethrow with a custom message
        } finally {
            if (connection != null) {
                connection.close(); // Ensure connection is closed
            }
        }
    }

    public void displayTheCustomerInfo() throws SQLException {
        this.initConnection();
        String query = "SELECT * FROM customer";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            // Setting column widths
            int idWidth = 15;
            int nameWidth = 20;
            int addressWidth = 20;
            int contactWidth = 20;
            int emailIdWidth = 20;


            // Print the header
            System.out.printf("%-" + idWidth + "s%-" + nameWidth + "s%-" + addressWidth + "s%-" + contactWidth + "s%-" + emailIdWidth + "s%n",
                    "CustomerID", "Name", "Address", "Contact", "Email ID");
            System.out.println("-----------------------------------------------------------------------------------------------");

            // Print the customer information in a table format
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("customer_name");
                String address = resultSet.getString("customer_address");
                Long contact = resultSet.getLong("customer_contact");
                String emailId = resultSet.getString("customer_email_id");

                // Print each row of the result set in the specified format
                System.out.printf("%-" + idWidth + "d%-" + nameWidth + "s%-" + addressWidth + "s%-" + contactWidth + "s%-" + emailIdWidth + "s%n",
                        customerId, name, address, contact, emailId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCustomerById(int customer_Id) throws SQLException {
        this.initConnection();
        String query = "SELECT * FROM customer where customer_id = " + customer_Id;
        System.out.println("query inside getCustomerById = " + query);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            // Setting column widths
            int idWidth = 15;
            int nameWidth = 20;
            int addressWidth = 20;
            int contactWidth = 20;
            int emailIdWidth = 20;


            // Print the header
            System.out.printf("%-" + idWidth + "s%-" + nameWidth + "s%-" + addressWidth + "s%-" + contactWidth + "s%-" + emailIdWidth + "s%n",
                    "CustomerID", "Name", "Address", "Contact", "Email ID");
            System.out.println("-----------------------------------------------------------------------------------------------");

            // Print the customer information in a table format
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("customer_name");
                String address = resultSet.getString("customer_address");
                Long contact = resultSet.getLong("customer_contact");
                String emailId = resultSet.getString("customer_email_id");

                // Print each row of the result set in the specified format
                System.out.printf("%-" + idWidth + "d%-" + nameWidth + "s%-" + addressWidth + "s%-" + contactWidth + "s%-" + emailIdWidth + "s%n",
                        customerId, name, address, contact, emailId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteCustomerById(int customer_id) throws SQLException {
        this.initConnection();
        String query = "DELETE FROM customer where customer_id = ?";
        System.out.println("query inside deleteCustomerById = " + query);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customer_id); // Set the parameter for book_id
            int affectedRows = preparedStatement.executeUpdate(); // Use executeUpdate for DELETE
            if (affectedRows > 0) {
                System.out.println("Customer entry for id = " + customer_id + " has been deleted successfully");
            } else {
                System.out.println("No Customer entry found with id = " + customer_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) connection.close(); // Ensure connection is closed
        }
    }

    public boolean isCustomerTableEmpty() throws SQLException {
        this.initConnection();
        String query = "SELECT COUNT(*) FROM customer";
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

    public boolean updateCustomerInfo(Customer customer) throws SQLException {

        String query = "UPDATE customer SET customer_name = ?, customer_address = ?, customer_contact = ?, customer_email_id = ? WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getCustomerAddress());
            preparedStatement.setLong(3, customer.getCustomerContact());
            preparedStatement.setString(4, customer.getCustomerEmailID());
            preparedStatement.setInt(5, customer.getCustomerID());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if update was successful
        }
    }

    public boolean isCustomerExists(int customerId) throws SQLException {
        this.initConnection();
        String query = "SELECT COUNT(*) FROM customer WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Returns true if book exists
                }
            }
        }
        return false;
    }
}
