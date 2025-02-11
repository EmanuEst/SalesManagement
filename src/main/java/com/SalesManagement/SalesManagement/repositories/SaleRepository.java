package com.SalesManagement.SalesManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SalesManagement.SalesManagement.entities.Sale;


@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
