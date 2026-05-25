package com.example.SuperMarket.dto;

import java.time.LocalDate;
 
import lombok.Data;
 
@Data
public class Employeeresponsedto {
    /**
     * id del empleado
     */
    private Long id;
    /**
     * numero empleado
     */
    private String idNumber;
    /**
     * Nombre del empleado
     */
    private String name;
    /**
     * Puesto del empleado
     */
    private String position;
    /**
     * fecha de contratacion del empleado
     */
    private LocalDate hireDate;
    /**
     * salario del empleado
     */
    private Double salary;
}
