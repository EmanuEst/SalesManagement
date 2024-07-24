package com.SalesManagement.SalesManagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.SalesManagement.SalesManagement.entities.Product;
import com.SalesManagement.SalesManagement.entities.Sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SaleItemsWithSalesDTO(
        @NotNull @NotBlank String product,

        @Size(min = 1, max = 500) String description,

        @NotNull BigDecimal price,

        LocalDate saleDate) {
    public SaleItemsWithSalesDTO(Sale s, Product p) {
        this(
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                s.getSaleDate());
    }
}
