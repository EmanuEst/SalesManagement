package com.SalesManagement.SalesManagement.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SalesManagement.SalesManagement.services.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/report")
@RequiredArgsConstructor
public class ReportController {

    @Autowired
    private final ReportService reportService;

    @GetMapping("/products")
    public ResponseEntity<byte[]> registredProducts() throws IOException {
        ByteArrayInputStream bis = reportService.productsExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Products.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bis.readAllBytes());
    }

    // @GetMapping("/products/period")
    // public ResponseEntity<byte[]> registredProductsMaxValue(
    // @RequestParam BigDecimal maxValue
    // ) throws IOException {

    // }

    @GetMapping("/sale-items")
    public ResponseEntity<byte[]> allSaleItems() throws IOException {
        ByteArrayInputStream bis = reportService.saleItemsExcel(null);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=SaleItems.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bis.readAllBytes());
    }

    @GetMapping("sale-items/period")
    public ResponseEntity<byte[]> saleItemsPerPeriod(
            @RequestParam LocalDate initialDate,
            @RequestParam LocalDate finalDate) throws IOException {
        ByteArrayInputStream bis = reportService.saleItemsPerPeriod(null, initialDate, finalDate);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=SaleItemsPeriod.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bis.readAllBytes());
    }
}
