package com.amit.book.inventory.controller;

import com.amit.book.inventory.exception.InvalidBookIDException;
import com.amit.book.inventory.exception.InvalidBookNameException;
import com.amit.book.inventory.exception.InvalidBookPriceException;
import com.amit.book.inventory.service.SupplierService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Scanner;

@Component
public class SupplierController {

    private static final Scanner scanner = new Scanner(System.in);
    @Autowired
    private SupplierService supplierService;
    public void run() throws SQLException {
        int option = 0;

        do {
            System.out.println("Please select option from below list :");
            System.out.println("1. Fill supplier information");
            System.out.println("2. Display supplier information");
            System.out.println("3. Get supplier by id");
            System.out.println("4. Update the supplier information");
            System.out.println("5. Delete the supplier information");
            System.out.println("9: Go back to main menu");
            option = Integer.parseInt(scanner.nextLine());


            switch (option) {
                case 1:
                    supplierService.acceptingSupplierInfo();
                    break;

                case 2:
                    supplierService.displaySupplierInfo();
                    break;

                case 3:
                    System.out.println("Enter supplier id");
                    int supplierId = Integer.parseInt(scanner.nextLine());
                    supplierService.getSupplierById(supplierId);
                    break;

                case 4:
                    try {
                        System.out.println("Enter the ID of the supplier you want to update:");
                        int supplier_Id = Integer.parseInt(scanner.nextLine());
                        boolean customerExists = supplierService.isSupplierExist(supplier_Id);
                        if (customerExists) supplierService.updateSupplierInfo(supplier_Id);
                    } catch (NumberFormatException | InvalidBookNameException | InvalidBookIDException |
                             InvalidBookPriceException e) {
                        System.out.println("Invalid input. Please enter a valid supplier ID.");
                    }
                    break;

                case 5:
                    try {
                        System.out.println("Enter the ID of the supplier you want to delete:");
                        int supplier_Id = Integer.parseInt(scanner.nextLine());
                        boolean customerExists = supplierService.isSupplierExist(supplier_Id);
                        if (customerExists) supplierService.deleteSupplierById(supplier_Id);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid supplier ID.");
                    }
                    break;
            }

        }
        while (option != 9);
        //System.out.println("Welcome back to main menu!");
    }
}
