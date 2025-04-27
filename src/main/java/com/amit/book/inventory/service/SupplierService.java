package com.amit.book.inventory.service;

import com.amit.book.inventory.exception.InvalidBookIDException;
import com.amit.book.inventory.exception.InvalidBookNameException;
import com.amit.book.inventory.exception.InvalidBookPriceException;
import com.amit.book.inventory.model.Supplier;
import com.amit.book.inventory.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Scanner;

@AllArgsConstructor
@Service
public class SupplierService implements SupplierServiceInterface {

    private static Scanner scanner = new Scanner(System.in);

    @Autowired
    private final SupplierRepository supplierRepository;

    public void acceptingSupplierInfo() throws SQLException {

        Supplier supplier = Supplier.builder().build();

        System.out.println("Enter supplier id");
        int supplierId = Integer.parseInt(scanner.nextLine());
        supplier.setSupplierID(supplierId);

        System.out.println("Enter supplier name");
        String name = scanner.nextLine();
        supplier.setSupplierName(name);

        System.out.println("Enter supplier address");
        String address = scanner.nextLine();
        supplier.setSupplierAddress(address);

        System.out.println("Enter supplier contact number");
        Long contact = Long.valueOf(scanner.nextLine());
        supplier.setSupplierContact(contact);

        System.out.println("Enter supplier Email ID");
        String emailID = scanner.nextLine();
        supplier.setSupplierEmailId(emailID);

        boolean isSupplierAdded = supplierRepository.fillSupplierInfo(supplier);
        System.out.println(isSupplierAdded ? "Supplier entry added in DB" : "Failed to add customer entry in DB");
    }

    public void displaySupplierInfo() throws SQLException {
        supplierRepository.displaySupplierInfo();
    }

    public void getSupplierById(int supplier_Id) throws SQLException {
        supplierRepository.getSupplierById(supplier_Id);
    }

    public void deleteSupplierById(int supplierId) throws SQLException {
        supplierRepository.deleteSupplierById(supplierId);
    }

    public boolean isSupplierExist(int supplierId) throws SQLException {
        return supplierRepository.isSupplierExists(supplierId);
    }

    public void updateSupplierInfo(int supplier_Id) throws InvalidBookNameException, SQLException, InvalidBookIDException, InvalidBookPriceException {
        Supplier supplier = Supplier.builder().build();
        supplier.setSupplierID(supplier_Id);

        System.out.println("Enter new supplier name");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            supplier.setSupplierName(name);
        }

        System.out.println("Enter new supplier address");
        String address = scanner.nextLine();
        if (!address.isEmpty()) {
            supplier.setSupplierAddress(address);
        }

        System.out.println("Enter new supplier contact");
        long contact = 0L;
        contact = Long.parseLong(scanner.nextLine());
        if (!(contact == 0L)) {
            supplier.setSupplierContact(contact);
        }

        System.out.println("Enter new supplier email address");
        String emailId = scanner.nextLine();
        if (!emailId.isEmpty()) {
            supplier.setSupplierEmailId(emailId);
        }

        boolean isUpdated = supplierRepository.updateSupplierInfo(supplier);
        System.out.println(isUpdated ? "Supplier info updated successfully." : "Failed to update supplier info.");
    }

}
