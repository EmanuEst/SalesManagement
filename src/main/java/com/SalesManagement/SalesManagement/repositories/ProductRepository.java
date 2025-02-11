package com.SalesManagement.SalesManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SalesManagement.SalesManagement.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
