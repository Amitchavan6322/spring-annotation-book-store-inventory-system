package com.amit.book.inventory.service;

import com.amit.book.inventory.exception.InvalidBookIDException;
import com.amit.book.inventory.exception.InvalidBookNameException;
import com.amit.book.inventory.exception.InvalidBookPriceException;
import com.amit.book.inventory.model.Book;
import com.amit.book.inventory.model.BookCategory;
import com.amit.book.inventory.repository.BookRepository;
import com.amit.book.inventory.util.SpringContextUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Scanner;



@Service
public class BookService extends LibraryService implements BookServiceInterface {

    private static Scanner scanner = new Scanner(System.in);
    @Autowired
    private BookRepository bookRepository;

    public void acceptingBookInfo() throws InvalidBookIDException, InvalidBookNameException, InvalidBookPriceException, SQLException {


        boolean value = bookRepository.isBookTableEmpty();
        if (value) System.out.println("No books currently in the inventory.");

        Book book = Book.builder().build();
        System.out.println("Enter book id");
        int bookId;
        try {
            bookId = Integer.parseInt(scanner.nextLine());
            book.setBookId(bookId);
        } catch (NumberFormatException e) {
            //System.out.println("Invalid input, please provide a valid numeric ID.");
            //return;
            throw new InvalidBookIDException("Invalid book id please provide valid id");
        }

        System.out.println("Enter book Name");
        String name = scanner.nextLine();
        try {
            if ((name.isEmpty())) {
                throw new InvalidBookNameException("Book name can't be empty");
            }
            book.setName(name);
        } catch (InvalidBookNameException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Enter book Author");
        String author = scanner.nextLine();
        book.setAuthor(author);

        System.out.println("Enter book Publisher");
        String publisher = scanner.nextLine();
        book.setPublisher(publisher);

        System.out.println("Enter no of book copies");

        try {
            int noOfCopies = Integer.parseInt(scanner.nextLine());
            book.setNoOfCopies(noOfCopies);
        } catch (NumberFormatException e) {
            throw new InvalidBookIDException("Please provide no. of copies in number");
        }


        // Updated category input
        System.out.println("Enter book category (Options: ACADEMIC, FICTION, HISTORY, MUSIC)");
        String categoryInput = scanner.nextLine().toUpperCase(); // Convert input to uppercase for enum compatibility
        try {
            BookCategory category = BookCategory.valueOf(categoryInput); // Convert input to enum value
            book.setCategory(category);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category. Please enter one of the following: ACADEMIC, FICTION, ACTION, MUSIC");
            return; // Exit the method if invalid input
        }

        System.out.println("Enter book store location");
        String storeLocation = scanner.nextLine();
        book.setStoreLocation(storeLocation);

        System.out.println("Enter book price");
        try {
            int price = Integer.parseInt(scanner.nextLine());
            book.setPrice(price);
        } catch (NumberFormatException e) {
            throw new InvalidBookPriceException("Invalid book price please provide valid id");
        }
        //System.out.println("Book to be added: " + book);
        //books.put(bookId, book);
        boolean isBookAdded = bookRepository.fillTheBookInfo(book);
        System.out.println(isBookAdded ? "Book entry added in DB" : "Failed to add book entry in DB");
    }


    public void displayBookInfo() throws SQLException {
        bookRepository.displayTheBookInfo();
    }

    // method to retrieve book by id
    public Book getBookById(int book_Id) throws SQLException {
        bookRepository.getBookById(book_Id);
        return null;
    }


    // Method to remove a book by ID
    public void deleteBookById(int bookId) throws SQLException {
        bookRepository.deleteBookById(bookId);
    }


    public void updateBookInfo(int book_Id) throws InvalidBookNameException, SQLException, InvalidBookIDException, InvalidBookPriceException {
        //Book book = SpringContextUtil.getContext().getBean("book", Book.class);
        Book book = Book.builder().build();
        book.setBookId(book_Id);

        System.out.println("Enter new book name");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            book.setName(name);
        }

        System.out.println("Enter new book author");
        String author = scanner.nextLine();
        if (!author.isEmpty()) {
            book.setAuthor(author);
        }

        System.out.println("Enter new book publisher");
        String publisher = scanner.nextLine();
        if (!publisher.isEmpty()) {
            book.setPublisher(publisher);
        }

        System.out.println("Enter new no of book copies");
        String copiesInput = scanner.nextLine();
        if (!copiesInput.isEmpty()) {
            try {
                int noOfCopies = Integer.parseInt(copiesInput);
                book.setNoOfCopies(noOfCopies);
            } catch (NumberFormatException e) {
                throw new InvalidBookIDException("Please provide the number of copies in a valid format.");
            }
        }

        System.out.println("Enter new book category");
        String categoryInput = scanner.nextLine();
        if (!categoryInput.isEmpty()) {
            try {
                BookCategory category = BookCategory.valueOf(categoryInput.toUpperCase());
                book.setCategory(category);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category. Please enter a valid category.");
                return;
            }
        }

        System.out.println("Enter new store location");
        String storeLocation = scanner.nextLine();
        if (!storeLocation.isEmpty()) {
            book.setStoreLocation(storeLocation);
        }

        System.out.println("Enter new book price");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) {
            try {
                int price = Integer.parseInt(priceInput);
                book.setPrice(price);
            } catch (NumberFormatException e) {
                throw new InvalidBookPriceException("Invalid book price. Please provide a valid price.");
            }
        }
        boolean isUpdated = bookRepository.updateBookInfo(book);
        System.out.println(isUpdated ? "Book info updated successfully." : "Failed to update book info.");
    }


    public boolean isBookExist(int bookId) throws SQLException {
        return bookRepository.isBookExists(bookId);
    }
}

