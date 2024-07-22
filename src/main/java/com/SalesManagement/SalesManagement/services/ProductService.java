package com.SalesManagement.SalesManagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.SalesManagement.SalesManagement.dto.ProductDTO;
import com.SalesManagement.SalesManagement.entities.Product;
import com.SalesManagement.SalesManagement.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

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

    @Transactional
    public Product saveNewProduct(ProductDTO productDTO) {
        Product newProduct = modelMapper.map(productDTO, Product.class);

        return prodRepository.save(newProduct);
    }

    public Product findProductById(Long id) {
        if (id != null && id > 0)
            return prodRepository.findById(id).orElse(null);
        return null;

    }

    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product productUpdate = modelMapper.map(productDTO, Product.class);

        if (id != null && id > 0) {
            Optional<Product> productOptional = this.prodRepository.findById(id);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                product.setName(productUpdate.getName());

                product.setDescription(productUpdate.getDescription());

                product.setPrice(productUpdate.getPrice());

                product.setStockQuantity(productUpdate.getStockQuantity());

                return this.prodRepository.save(product);
            }
        }
        return null;
    }

    @Transactional
    public Product deleteProductById(Long id) {
        if (id != null && id > 0)
            this.prodRepository.deleteById(id);
        return null;
    }
}
