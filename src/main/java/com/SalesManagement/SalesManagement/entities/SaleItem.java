package com.SalesManagement.SalesManagement.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sale_items")
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Column(name = "sale_id", nullable = false)
    private Sale saleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Column(name = "product_id", nullable = false)
    private Product productId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
}
