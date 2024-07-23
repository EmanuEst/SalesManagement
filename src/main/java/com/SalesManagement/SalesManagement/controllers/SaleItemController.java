package com.SalesManagement.SalesManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SalesManagement.SalesManagement.dto.SaleItemsWithSalesDTO;
import com.SalesManagement.SalesManagement.services.SaleItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/sale-item")
@RequiredArgsConstructor
public class SaleItemController {

    @Autowired
    private final SaleItemService siService;

    @GetMapping
    public ResponseEntity<Page<SaleItemsWithSalesDTO>> getAllSaleItems(Pageable pageable) throws Exception {
        return ResponseEntity.ok().body(siService.listAllSaleItems(pageable));
    }
}
