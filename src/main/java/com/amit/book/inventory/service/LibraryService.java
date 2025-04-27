package com.amit.book.inventory.service;

import com.amit.book.inventory.exception.InvalidBookIDException;
import com.amit.book.inventory.model.Book;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;

@Service
public abstract class LibraryService {

    protected HashMap<Integer, Book> books = new HashMap<>();

    public void displayAllBooks() {

        // old for each loop
        /*for (Map.Entry<Integer, Book> entry : books.entrySet()) {
            System.out.println("Book ID: " + entry.getKey() + " - " + entry.getValue());
        }*/

        // java8 feature forEach loop
        books.forEach((id, book) -> System.out.println("Book ID: " + id + " = Book Info: " + book));
    }

    public Book getBookById(int bookId) throws InvalidBookIDException, SQLException {
        if (!books.containsKey(bookId)) {
            throw new InvalidBookIDException("Book ID not found");
        }
        return books.get(bookId);
    }

    public boolean isBookCollectionEmpty() {
        return books.isEmpty();
    }

    public void clearBooks() {
        books.clear();
    }
}
