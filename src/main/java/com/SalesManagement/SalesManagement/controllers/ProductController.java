package com.SalesManagement.SalesManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SalesManagement.SalesManagement.dto.ProductDTO;
import com.SalesManagement.SalesManagement.entities.Product;
import com.SalesManagement.SalesManagement.services.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService prodService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) throws Exception {
        return ResponseEntity.ok(prodService.listAllProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws Exception {
        Product product = prodService.findProductById(id);

        if (product != null) {
            return ResponseEntity.ok(prodService.findProductById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO product) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(prodService.saveNewProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO product)
            throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(prodService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws Exception {
        this.prodService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }
}
