package com.example.SuperMarket.entity;

import java.util.List;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Table(name = "suppliers")
@Data
public class Supplier {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "name")
    private String name;
 
    @Column(name = "nit", unique = true)
    private String nit;
 
    @Column(name = "phone")
    private String phone;
 
    @Column(name = "email")
    private String email;
 
    // Inverse side of the ManyToMany relationship with products
    @ManyToMany(mappedBy = "suppliers")
    private List<Product> products;
}
