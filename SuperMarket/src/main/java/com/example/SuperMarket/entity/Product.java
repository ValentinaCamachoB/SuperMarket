package com.example.SuperMarket.entity;

import java.util.List;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Table(name = "products")
@Data
public class Product {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "name")
    private String name;
 
    @Column(name = "barcode", unique = true)
    private String barcode;
 
    @Column(name = "price")
    private Double price;
 
    @Column(name = "stock")
    private Integer stock;
 
    private Boolean active;
 
    // productos pertenecen a una categoria
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
 
    // productos pueden tener muchos provedores
    @ManyToMany
    @JoinTable(
        name = "product_supplier",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private List<Supplier> suppliers;
}
