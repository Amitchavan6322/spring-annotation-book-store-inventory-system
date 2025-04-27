package com.amit.book.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private Long customerContact;
    private String customerEmailID;
}
