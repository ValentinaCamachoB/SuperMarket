package com.example.SuperMarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
 
@Data
public class Categoryrequestdto {
 
    @NotBlank(message = "Category name is required")
    private String name;
 
    private String description;
}
