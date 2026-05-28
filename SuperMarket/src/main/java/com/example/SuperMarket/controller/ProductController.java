package com.example.SuperMarket.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperMarket.Service.ProductService;
import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;
import com.example.SuperMarket.dto.Productrequestdto;
import com.example.SuperMarket.dto.Productresponsedto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class Productcontroller {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Messageresponsedto> createProduct(@Valid @RequestBody Productrequestdto request) {
        try {
            Messageresponsedto response = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/get-products")
    public List<Productresponsedto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/get-product/{id}")
    public Httpglobalresponse<Productresponsedto> getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }


    @PutMapping("/update-product/{id}")
    public Httpglobalresponse<Productresponsedto> updateProduct(@PathVariable Long id,
            @Valid @RequestBody Productrequestdto request) {
        return productService.updateProduct(id, request);
    }

   
    @DeleteMapping("/delete-product/{id}")
    public Httpglobalresponse<Productresponsedto> deactivateProduct(@PathVariable Long id) {
        return productService.deactivateProduct(id);
    }
}