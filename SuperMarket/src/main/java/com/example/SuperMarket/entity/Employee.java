package com.example.SuperMarket.entity;

import java.time.LocalDate;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Table(name = "employees")
@Data
public class Employee {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "id_number", unique = true)
    private String idNumber;
 
    @Column(name = "name")
    private String name;
 
    @Column(name = "position")
    private String position;
 
    @Column(name = "hire_date")
    private LocalDate hireDate;
 
    @Column(name = "salary")
    private Double salary;
}

