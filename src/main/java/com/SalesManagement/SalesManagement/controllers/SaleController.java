package com.SalesManagement.SalesManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SalesManagement.SalesManagement.dto.SaleDTO;
import com.SalesManagement.SalesManagement.services.SaleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/sales")
@RequiredArgsConstructor
public class SaleController {

    @Autowired
    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody SaleDTO saleRequest) throws Exception {
        try {
            this.saleService.createSale(saleRequest);
        } catch (Exception e) {
            throw e;
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
