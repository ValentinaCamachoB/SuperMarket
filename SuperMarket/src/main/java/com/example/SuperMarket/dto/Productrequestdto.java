package com.example.SuperMarket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDto {

    @NotBlank(message = "El nombre del producto es requerido")
    private String name;

    @NotBlank(message = "El código de barras es requerido")
    private String barcode;

    @NotNull(message = "El precio es requerido")
    @Min(value = 0, message = "El precio debe ser 0 o mayor")
    private Double price;

    @NotNull(message = "El stock es requerido")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El ID de la categoría es requerido")
    private Long categoryId;
}