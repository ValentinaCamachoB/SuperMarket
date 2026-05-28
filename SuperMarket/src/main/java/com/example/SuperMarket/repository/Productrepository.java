package com.example.SuperMarket.repository;

import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.example.SuperMarket.entity.Product;
 
@Repository
public interface Productrepository extends JpaRepository<Product, Long> {
    // Find product by barcode to check for duplicates
    Optional<Product> findByBarcode(String barcode);
}
