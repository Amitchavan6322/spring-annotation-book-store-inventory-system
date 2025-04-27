package com.amit.book.inventory.service;

import java.sql.SQLException;

public interface CustomerServiceInterface {
    void acceptCustomerInfo() throws SQLException;
    void displayCustomerInfo() throws SQLException;

}
