package com.SalesManagement.SalesManagement.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.SalesManagement.SalesManagement.dto.SaleItemsWithSalesDTO;
import com.SalesManagement.SalesManagement.repositories.SaleItemRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SaleItemService {

    @Autowired
    private final SaleItemRepository siRepository;

    public Page<SaleItemsWithSalesDTO> listAllSaleItems(Pageable pageable) throws Exception {
        return siRepository.getAllSaleItems(pageable);
    }

    public Page<SaleItemsWithSalesDTO> listSaleItemsPerPeriod(Pageable pageable, LocalDate initialDate, LocalDate finalDate) throws Exception {
        return siRepository.getSaleItemsPerPeriod(pageable, initialDate, finalDate);
    }
}
