package com.example.SuperMarket.dto;

import lombok.Data;
 
@Data
public class SaleDetailResponseDto {
    private Long id;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
}
