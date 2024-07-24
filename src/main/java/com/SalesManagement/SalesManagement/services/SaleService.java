package com.SalesManagement.SalesManagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SalesManagement.SalesManagement.dto.SaleDTO;
import com.SalesManagement.SalesManagement.entities.Product;
import com.SalesManagement.SalesManagement.entities.Sale;
import com.SalesManagement.SalesManagement.entities.SaleItem;
import com.SalesManagement.SalesManagement.repositories.ProductRepository;
import com.SalesManagement.SalesManagement.repositories.SaleRepository;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SaleService {
    
    @Autowired
    private final SaleRepository saleRepository;

    @Autowired
    private final ProductRepository prodRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public Sale createSale(SaleDTO saleDTO) {

        Sale sale = modelMapper.map(saleDTO, Sale.class);

        for (SaleItem item : sale.getSaleItems()) {
            item.setSaleId(sale);

            Product product = prodRepository.findById(item.getProductId().getId())
                                            .orElseThrow(() -> new RuntimeException("Product Not Found"));

            item.setTotalPrice(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        return this.saleRepository.save(sale);
    }
}
