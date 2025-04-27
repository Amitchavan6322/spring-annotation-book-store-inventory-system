package com.amit.book.inventory.controller;

import com.amit.book.inventory.exception.InvalidBookIDException;
import com.amit.book.inventory.exception.InvalidBookNameException;
import com.amit.book.inventory.exception.InvalidBookPriceException;
import com.amit.book.inventory.service.CustomerService;
import com.amit.book.inventory.util.SpringContextUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Scanner;

@Component
public class CustomerController {

    private static final Scanner scanner = new Scanner(System.in);
    @Autowired
    private CustomerService customerService;
    public void run() throws SQLException {
        int option = 0;

        do {
            System.out.println("Please select option from below list :");
            System.out.println("1. Fill the customer information");
            System.out.println("2. Display customer information");
            System.out.println("3. Get customer by id");
            System.out.println("4. Update the customer information");
            System.out.println("5. Delete the customer information");
            System.out.println("9: Go back to main menu");
            option = Integer.parseInt(scanner.nextLine());


            switch (option) {
                case 1:
                    customerService.acceptCustomerInfo();
                    break;

                case 2:
                    customerService.displayCustomerInfo();
                    break;

                case 3:
                    System.out.println("Enter customer id");
                    int customerId = Integer.parseInt(scanner.nextLine());
                    customerService.getCustomerById(customerId);
                    break;

                case 4:
                    try {
                        System.out.println("Enter the ID of the customer you want to update:");
                        int customer_Id = Integer.parseInt(scanner.nextLine());
                        boolean customerExists = customerService.isCustomerExist(customer_Id);
                        if (customerExists) customerService.updateCustomerInfo(customer_Id);
                    } catch (NumberFormatException | InvalidBookNameException | InvalidBookIDException |
                             InvalidBookPriceException e) {
                        System.out.println("Invalid input. Please enter a valid customer ID.");
                    }
                    break;

                case 5:
                    try {
                        System.out.println("Enter the ID of the customer you want to delete:");
                        int customer_Id = Integer.parseInt(scanner.nextLine());
                        boolean customerExists = customerService.isCustomerExist(customer_Id);
                        if (customerExists) customerService.deleteCustomerById(customer_Id);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid customer ID.");
                    }
                    break;
            }

        }
        while (option != 9);
       // System.out.println("Welcome back to main menu!");
    }
}
