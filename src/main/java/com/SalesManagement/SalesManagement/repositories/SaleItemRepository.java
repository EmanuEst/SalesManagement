package com.SalesManagement.SalesManagement.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SalesManagement.SalesManagement.dto.SaleItemsWithSalesDTO;
import com.SalesManagement.SalesManagement.entities.SaleItem;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    @Query(nativeQuery = false, value = """
            SELECT new com.SalesManagement.SalesManagement.dto.SaleItemsWithSalesDTO (
                    p.name,
                    p.description,
                    p.price,
                    s.saleDate
                )
                FROM SaleItem si
                INNER JOIN si.saleId s
                INNER JOIN si.productId p
            """)
    public Page<SaleItemsWithSalesDTO> getAllSaleItems(Pageable pageable);

    @Query(nativeQuery = false, value = """
            SELECT new com.SalesManagement.SalesManagement.dto.SaleItemsWithSalesDTO (
                    p.name,
                    p.description,
                    p.price,
                    s.saleDate
                )
                FROM SaleItem si
                INNER JOIN si.saleId s
                INNER JOIN si.productId p
                WHERE s.saleDate BETWEEN :initialDate AND :finalDate
            """)
    public Page<SaleItemsWithSalesDTO> getSaleItemsPerPeriod(Pageable pageable,
            @Param(value = "initialDate") LocalDate initialDate, @Param(value = "finalDate") LocalDate finalDate);
}
