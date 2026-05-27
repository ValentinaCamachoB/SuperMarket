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

import com.example.SuperMarket.Service.Saleservice;
import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;
import com.example.SuperMarket.dto.Salerequestdto;
import com.example.SuperMarket.dto.Saleresponsedto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class Salecontroller {

    private final Saleservice saleService;
    
     /**
     * Crea una nueva venta
     */
    @PostMapping("/create")
    public ResponseEntity<Messageresponsedto> createSale(@Valid @RequestBody Salerequestdto request) {
        try {
            Messageresponsedto response = saleService.createSale(request);
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
    public List<Saleresponsedto> getSales() {
        return saleService.getSales();
    }

     /**
     * Obtiene una venta por su identificador
     */
    @GetMapping("/get-sale/{id}")
    public Httpglobalresponse<Saleresponsedto> getSale(@PathVariable Long id) {
        return saleService.getSale(id);
    }
}

