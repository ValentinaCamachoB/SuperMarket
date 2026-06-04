package com.example.SuperMarket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Data
public class WarehouseEntryRequestDto {
 
    @NotNull(message = "El ID del producto es requerido")
    private Long productId;
 
    @NotNull(message = "Se requiere identificación del proveedor")
    private Long supplierId;
 
    @NotNull(message = "Se requiere cantidad")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;
}