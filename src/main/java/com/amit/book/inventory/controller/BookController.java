package com.amit.book.inventory.controller;

import com.amit.book.inventory.exception.InvalidBookIDException;
import com.amit.book.inventory.exception.InvalidBookNameException;
import com.amit.book.inventory.exception.InvalidBookPriceException;
import com.amit.book.inventory.service.BookService;
import com.amit.book.inventory.util.SpringContextUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Scanner;

@AllArgsConstructor
@Component
public class BookController {

    private static final Scanner scanner = new Scanner(System.in);

    private final BookService bookService;


    public void run() throws SQLException, InvalidBookNameException, InvalidBookIDException, InvalidBookPriceException {
        int option = 0;

        do {
            System.out.println("Please select option from below list :");
            System.out.println("1. Fill the book information");
            System.out.println("2. Display book information");
            System.out.println("3. Get book by id");
            System.out.println("4. Update the book information");
            System.out.println("5. Delete the book information");
            System.out.println("9: Go back to main menu");
            option = Integer.parseInt(scanner.nextLine());


            switch (option) {
                case 1:
                    try {
                        bookService.acceptingBookInfo();
                        break;
                    } catch (InvalidBookIDException | InvalidBookNameException |
                             InvalidBookPriceException invalidBookIDException) {
                        System.err.println(invalidBookIDException.getMessage());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                case 2:
                    bookService.displayBookInfo();
                    break;

                case 3:
                    System.out.println("Enter book id");
                    int bookId = Integer.parseInt(scanner.nextLine());
                    bookService.getBookById(bookId);
                    break;

                case 4:
                    try {
                        System.out.println("Enter the ID of the book you want to update:");
                        int book_Id = Integer.parseInt(scanner.nextLine());
                        boolean bookExists = bookService.isBookExist(book_Id);
                        if (bookExists) bookService.updateBookInfo(book_Id);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid book ID.");
                    }
                    break;

                case 5:
                    try {
                        System.out.println("Enter the ID of the book you want to delete:");
                        int book_Id = Integer.parseInt(scanner.nextLine());
                        boolean bookExists = bookService.isBookExist(book_Id);
                        if (bookExists) bookService.deleteBookById(book_Id);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid book ID.");
                    }
                    break;

            }

        }
        while (option != 9);
        //System.out.println("Welcome back to main menu!");
    }
}
