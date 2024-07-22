package com.SalesManagement.SalesManagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.SalesManagement.SalesManagement.dto.ProductDTO;
import com.SalesManagement.SalesManagement.entities.Product;
import com.SalesManagement.SalesManagement.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository prodRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public Page<Product> listAllProducts(Pageable pageable) {
        return prodRepository.findAll(pageable);
    }

    public Product saveNewProduct(ProductDTO productDTO) {
        Product newProduct = modelMapper.map(productDTO, Product.class);

        return prodRepository.save(newProduct);
    }
}
