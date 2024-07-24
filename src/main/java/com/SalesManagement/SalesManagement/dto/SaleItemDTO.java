package com.SalesManagement.SalesManagement.dto;

import java.math.BigDecimal;

import com.SalesManagement.SalesManagement.entities.SaleItem;

import jakarta.validation.constraints.NotNull;

public record SaleItemDTO(
        @NotNull Long id,

        @NotNull Long productId,

        @NotNull Integer quantity,

        @NotNull BigDecimal totalPrice) {
    public SaleItemDTO(
            SaleItem si) {
        this(
                si.getId(),
                si.getProductId().getId(),
                si.getQuantity(),
                si.getTotalPrice());
    }
}
