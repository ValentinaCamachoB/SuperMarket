package com.example.SuperMarket.entity;

import java.time.LocalDate;
import java.util.List;
 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Table(name = "sales")
@Data
public class Sale {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "sale_date")
    private LocalDate saleDate;
 
    @Column(name = "subtotal")
    private Double subtotal;
 
    @Column(name = "tax")
    private Double tax;
 
    @Column(name = "total")
    private Double total;
 
     /**
     * Cada venta debe estar vinculada a un empleado
     */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
 
     /**
     * Una venta tiene muchas líneas de detalle
     */
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleDetail> details;
}

