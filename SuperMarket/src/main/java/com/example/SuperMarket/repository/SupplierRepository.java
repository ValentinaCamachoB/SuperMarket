package com.example.SuperMarket.repository;

import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.example.SuperMarket.entity.Supplier;
 
@Repository
public interface Supplierrepository extends JpaRepository<Supplier, Long> {
// Buscar proveedor por NIT para comprobar si hay duplicados
    Optional<Supplier> findByNit(String nit);
}
