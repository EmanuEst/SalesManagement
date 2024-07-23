package com.SalesManagement.SalesManagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.SalesManagement.SalesManagement.entities.Product;
import com.SalesManagement.SalesManagement.entities.Sale;

public record SaleItemsWithSalesDTO(
        String product,

        String description,

        BigDecimal price,

        LocalDate saleDate) {
    public SaleItemsWithSalesDTO(Sale s, Product p) {
        this(
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                s.getSaleDate());
    }
}
