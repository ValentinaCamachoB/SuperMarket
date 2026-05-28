package com.example.SuperMarket.dto;

import lombok.Data;
 
@Data
public class Productresponsedto {
    private Long id;
    private String name;
    private String barcode;
    private Double price;
    private Integer stock;
    private Boolean active;
    private String categoryName;
}
