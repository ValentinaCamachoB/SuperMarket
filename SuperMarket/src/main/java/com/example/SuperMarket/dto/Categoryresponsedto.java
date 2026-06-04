package com.example.SuperMarket.dto;

import java.util.List;

import lombok.Data;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
    private List<ProductResponseDto> products;
}
