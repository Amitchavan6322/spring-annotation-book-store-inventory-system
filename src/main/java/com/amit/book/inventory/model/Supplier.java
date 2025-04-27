package com.amit.book.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    private int supplierID;
    private String supplierName;
    private String supplierAddress;
    private Long supplierContact;
    private String supplierEmailId;

}
