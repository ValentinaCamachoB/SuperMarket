package com.example.SuperMarket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Data
public class Productrequestdto {
 
    @NotBlank(message = "Product name is required")
    private String name;
 
    @NotBlank(message = "Barcode is required")
    private String barcode;
 
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be 0 or greater")
    private Double price;
 
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;
 
    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
