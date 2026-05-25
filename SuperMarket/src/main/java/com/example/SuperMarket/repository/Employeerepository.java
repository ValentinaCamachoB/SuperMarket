package com.example.SuperMarket.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.example.SuperMarket.entity.Employee;
 
@Repository
public interface Employeerepository extends JpaRepository<Employee, Long> {
 
    /**
     * Buscar por idNumber para validar que no haya duplicados
     */
    Optional<Employee> findByIdNumber(String idNumber);
 
     /**
     * Filtrar empleados por puesto
     */
    List<Employee> findByPosition(String position);
 
    /**
     * Filtrar empleados por rango de fecha de contratación
     */
    List<Employee> findByHireDateBetween(LocalDate startDate, LocalDate endDate);
}
