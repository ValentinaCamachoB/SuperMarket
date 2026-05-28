package com.example.SuperMarket.dto;

import java.util.List;
 
import lombok.Data;
 
@Data
public class Categoryresponsedto {
    private Long id;
    private String name;
    private String description;
    // Only active products are included (Business Rule Module I)
    private List<Productresponsedto> products;
}
