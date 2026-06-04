package com.example.SuperMarket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierRequestDto {

    @NotBlank(message = "El nombre del proveedor es requerido")
    private String name;

    @NotBlank(message = "El NIT es requerido")
    private String nit;

    private String phone;

    @Email(message = "El email debe tener un formato válido")
    private String email;
}
