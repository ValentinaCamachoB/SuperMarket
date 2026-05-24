package com.example.SuperMarket.dto;

import lombok.Data;
 
@Data
public class Httpglobalresponse<T> {
    private String message;
    private T data;
}
