package com.amit.book.inventory.service;

import com.amit.book.inventory.exception.InvalidBookIDException;
import com.amit.book.inventory.exception.InvalidBookNameException;
import com.amit.book.inventory.exception.InvalidBookPriceException;
import com.amit.book.inventory.model.Book;

import java.sql.SQLException;

public interface BookServiceInterface {
    void acceptingBookInfo() throws InvalidBookIDException, InvalidBookNameException, InvalidBookPriceException, SQLException;
    void displayBookInfo() throws SQLException;
    Book getBookById(int bookId) throws SQLException;
    void deleteBookById(int bookId) throws SQLException;

}
