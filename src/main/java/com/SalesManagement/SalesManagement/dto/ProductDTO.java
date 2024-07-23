package com.SalesManagement.SalesManagement.dto;


import com.SalesManagement.SalesManagement.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductDTO(
        @NotNull @NotBlank @Size(min = 1, max = 100) String name,

        @Size(min = 1, max = 500) String description,

        @NotNull String price,

        @NotNull Integer stockQuantity) {
    public ProductDTO(
            Product prod) {
        this(
                prod.getName(),
                prod.getDescription(),
                prod.getPrice().toString(),
                prod.getStockQuantity());
    }
}
