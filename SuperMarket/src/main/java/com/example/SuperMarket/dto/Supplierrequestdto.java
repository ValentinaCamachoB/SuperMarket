package com.example.SuperMarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
 
@Data
public class Supplierrequestdto {
 
    @NotBlank(message = "El nombre del provedor es requerido")
    private String name;
 
    @NotBlank(message = "El NIT es requerido")
    private String nit;
 
    private String phone;
 
    private String email;
}
