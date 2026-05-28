package com.example.SuperMarket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Data
public class Saledetailrequestdto {
 
    @NotNull(message = "Se requiere el ID del producto")
    private Long productId;
 
    @NotNull(message = "Se requiere la cantidad")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;
}
