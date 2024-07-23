package com.SalesManagement.SalesManagement.services;

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

    public Page<SaleItemsWithSalesDTO> listAllSaleItems(Pageable pageable) {
        return siRepository.getAllSaleItems(pageable);
    }
}
