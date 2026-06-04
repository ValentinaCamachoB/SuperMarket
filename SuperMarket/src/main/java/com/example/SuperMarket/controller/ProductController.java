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

import com.example.SuperMarket.dto.HttpGlobalResponse;
import com.example.SuperMarket.dto.MessageResponseDto;
import com.example.SuperMarket.dto.ProductRequestDto;
import com.example.SuperMarket.dto.ProductResponseDto;
import com.example.SuperMarket.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Crea un nuevo producto
     */
    @PostMapping("/create")
    public ResponseEntity<MessageResponseDto> createProduct(@Valid @RequestBody ProductRequestDto request) {
        try {
            MessageResponseDto response = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Obtiene la lista de todos los productos
     */
    @GetMapping("/get-products")
    public List<ProductResponseDto> getProducts() {
        return productService.getProducts();
    }

    /**
     * Obtiene un producto por su identificador
     */
    @GetMapping("/get-product/{id}")
    public HttpGlobalResponse<ProductResponseDto> getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * Actualiza un producto existente
     */
    @PutMapping("/update-product/{id}")
    public HttpGlobalResponse<ProductResponseDto> updateProduct(@PathVariable Long id,
            @Valid @RequestBody ProductRequestDto request) {
        return productService.updateProduct(id, request);
    }

    /**
     * Desactiva un producto
     */
    @DeleteMapping("/delete-product/{id}")
    public HttpGlobalResponse<ProductResponseDto> deactivateProduct(@PathVariable Long id) {
        return productService.deactivateProduct(id);
    }
}
