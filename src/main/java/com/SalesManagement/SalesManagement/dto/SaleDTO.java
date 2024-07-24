package com.SalesManagement.SalesManagement.dto;

import java.time.LocalDate;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record SaleDTO(
        @NotNull LocalDate saleDate,

        @NotNull List<SaleItemDTO> saleItems) {}
