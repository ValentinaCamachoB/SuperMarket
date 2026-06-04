package com.example.SuperMarket.dto;

import lombok.Data;
 
@Data
public class HttpGlobalResponse<T> {
    private String message;
    private T data;
}
