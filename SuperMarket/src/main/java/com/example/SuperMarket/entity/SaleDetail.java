package com.example.SuperMarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Table(name = "sale_details")
@Data
public class SaleDetail {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "quantity")
    private Integer quantity;
 
    @Column(name = "unit_price")
    private Double unitPrice;
 
    @Column(name = "subtotal")
    private Double subtotal;
 
    /**
     * Cada detalle pertenece a una venta
     */
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
 
    /**
     * Cada detalle tiene un producto
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

