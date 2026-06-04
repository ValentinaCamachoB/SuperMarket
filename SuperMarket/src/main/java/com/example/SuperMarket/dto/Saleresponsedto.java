package com.example.SuperMarket.dto;

import java.time.LocalDate;
import java.util.List;
 
import lombok.Data;
 
@Data
public class SaleResponseDto {
    private Long id;
    private LocalDate saleDate;
    private String employeeName;
    private Double subtotal;
    private Double tax;
    private Double total;
    private List<SaleDetailResponseDto> details;
}
