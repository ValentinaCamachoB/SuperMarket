package com.example.SuperMarket.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperMarket.dto.HttpGlobalResponse;
import com.example.SuperMarket.dto.MessageResponseDto;
import com.example.SuperMarket.dto.SaleRequestDto;
import com.example.SuperMarket.dto.SaleResponseDto;
import com.example.SuperMarket.service.SaleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    /**
     * Crea una nueva venta
     */
    @PostMapping("/create")
    public ResponseEntity<MessageResponseDto> createSale(@Valid @RequestBody SaleRequestDto request) {
        try {
            MessageResponseDto response = saleService.createSale(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Obtiene la lista de todas las ventas
     */
    @GetMapping("/get-sales")
    public List<SaleResponseDto> getSales() {
        return saleService.getSales();
    }

    /**
     * Obtiene una venta por su identificador
     */
    @GetMapping("/get-sale/{id}")
    public HttpGlobalResponse<SaleResponseDto> getSale(@PathVariable Long id) {
        return saleService.getSale(id);
    }
}
