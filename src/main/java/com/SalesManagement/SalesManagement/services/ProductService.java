package com.SalesManagement.SalesManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SalesManagement.SalesManagement.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository prodRepository;

    
}
