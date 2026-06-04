package com.example.SuperMarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @NotBlank(message = "El nombre de la categoría es requerido")
    private String name;

    private String description;
}
