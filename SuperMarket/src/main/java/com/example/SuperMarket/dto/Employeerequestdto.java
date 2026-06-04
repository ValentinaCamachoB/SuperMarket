package com.example.SuperMarket.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EmployeeRequestDto {

    @NotBlank(message = "Se requiere el número de identificación")
    private String idNumber;

    @NotBlank(message = "Se requiere el nombre")
    private String name;

    @NotBlank(message = "Se requiere el puesto")
    private String position;

    @NotNull(message = "Se requiere la fecha de contratación")
    private LocalDate hireDate;

    @NotNull(message = "Se requiere el salario")
    @Positive(message = "El salario debe ser mayor a 0")
    private Double salary;
}
