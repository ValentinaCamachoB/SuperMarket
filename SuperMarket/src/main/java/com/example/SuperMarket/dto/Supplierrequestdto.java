package com.example.SuperMarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
 
@Data
public class Supplierrequestdto {
 
    @NotBlank(message = "El nombre del proveedor es requerido")
    private String name;
 
    @NotBlank(message = "NIT es requerido")
    private String nit;
 
    private String phone;
 
    private String email;
}
