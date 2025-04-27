package com.amit.book.inventory.repository;

import com.amit.book.inventory.model.Supplier;
import com.amit.book.inventory.service.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class SupplierRepository {

    private static Connection connection = null;

    private void initConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = new ConnectionService().getConnection();
        }
    }

    public boolean fillSupplierInfo(Supplier supplier) throws SQLException {
        this.initConnection();
        String query = "INSERT INTO supplier VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, supplier.getSupplierID());
            preparedStatement.setString(2, supplier.getSupplierName());
            preparedStatement.setString(3, supplier.getSupplierAddress());
            preparedStatement.setLong(4, supplier.getSupplierContact());
            preparedStatement.setString(5, supplier.getSupplierEmailId());


            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting supplier: " + e.getMessage());
            throw new SQLException("Unable to insert supplier info", e); // Rethrow with a custom message
        } finally {
            if (connection != null) {
                connection.close(); // Ensure connection is closed
            }
        }
    }

    public void displaySupplierInfo() throws SQLException {
        this.initConnection();
        String query = "SELECT * FROM supplier";
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
                    "SupplierID", "Name", "Address", "Contact", "Email ID");
            System.out.println("-----------------------------------------------------------------------------------------------");

            // Print the customer information in a table format
            while (resultSet.next()) {
                int supplierId = resultSet.getInt("supplier_id");
                String name = resultSet.getString("supplier_name");
                String address = resultSet.getString("supplier_address");
                Long contact = resultSet.getLong("supplier_contact");
                String emailId = resultSet.getString("supplier_email_id");

                // Print each row of the result set in the specified format
                System.out.printf("%-" + idWidth + "d%-" + nameWidth + "s%-" + addressWidth + "s%-" + contactWidth + "s%-" + emailIdWidth + "s%n",
                        supplierId, name, address, contact, emailId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getSupplierById(int supplier_Id) throws SQLException {
        this.initConnection();
        String query = "SELECT * FROM supplier where supplier_id = " + supplier_Id;
        System.out.println("query inside getSupplierById = " + query);
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
                    "SupplierID", "Name", "Address", "Contact", "Email ID");
            System.out.println("-----------------------------------------------------------------------------------------------");

            // Print the customer information in a table format
            while (resultSet.next()) {
                int supplierId = resultSet.getInt("supplier_id");
                String name = resultSet.getString("supplier_name");
                String address = resultSet.getString("supplier_address");
                Long contact = resultSet.getLong("supplier_contact");
                String emailId = resultSet.getString("supplier_email_id");

                // Print each row of the result set in the specified format
                System.out.printf("%-" + idWidth + "d%-" + nameWidth + "s%-" + addressWidth + "s%-" + contactWidth + "s%-" + emailIdWidth + "s%n",
                        supplierId, name, address, contact, emailId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteSupplierById(int supplier_Id) throws SQLException {
        this.initConnection();
        String query = "DELETE FROM supplier where supplier_Id = ?";
        System.out.println("query inside deleteSupplierById = " + query);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, supplier_Id); // Set the parameter for book_id
            int affectedRows = preparedStatement.executeUpdate(); // Use executeUpdate for DELETE
            if (affectedRows > 0) {
                System.out.println("Supplier entry for id = " + supplier_Id + " has been deleted successfully");
            } else {
                System.out.println("No Supplier entry found with id = " + supplier_Id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) connection.close(); // Ensure connection is closed
        }
    }

    public boolean isSupplierTableEmpty() throws SQLException {
        this.initConnection();
        String query = "SELECT COUNT(*) FROM supplier";
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

    public boolean updateSupplierInfo(Supplier supplier) throws SQLException {

        String query = "UPDATE supplier SET supplier_name = ?, supplier_address = ?, supplier_contact = ?, supplier_email_id = ? WHERE supplier_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, supplier.getSupplierName());
            preparedStatement.setString(2, supplier.getSupplierAddress());
            preparedStatement.setLong(3, supplier.getSupplierContact());
            preparedStatement.setString(4, supplier.getSupplierEmailId());
            preparedStatement.setInt(5, supplier.getSupplierID());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if update was successful
        }
    }

    public boolean isSupplierExists(int supplierId) throws SQLException {
        this.initConnection();
        String query = "SELECT COUNT(*) FROM supplier WHERE supplier_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, supplierId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Returns true if book exists
                }
            }
        }
        return false;
    }
}
