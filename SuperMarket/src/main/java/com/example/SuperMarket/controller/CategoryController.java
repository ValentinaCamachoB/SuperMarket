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
 
import com.example.SuperMarket.dto.CategoryRequestDto;
import com.example.SuperMarket.dto.CategoryResponseDto;
import com.example.SuperMarket.dto.HttpGlobalResponse;
import com.example.SuperMarket.dto.MessageResponseDto;
import com.example.SuperMarket.service.CategoryService;
 
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
 
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Crea una nueva categoria
     */
    @PostMapping("/create")
    public ResponseEntity<MessageResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto request) {
        try {
            MessageResponseDto response = categoryService.createCategory(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Obtiene la lista de todas las categorias
     */
    @GetMapping("/get-categories")
    public List<CategoryResponseDto> getCategories() {
        return categoryService.getCategories();
    }

    /**
     * Obtiene una categoria por su identificador
     */
    @GetMapping("/get-category/{id}")
    public HttpGlobalResponse<CategoryResponseDto> getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    /**
     * Actualiza una categoria existente
     */
    @PutMapping("/update-category/{id}")
    public HttpGlobalResponse<CategoryResponseDto> updateCategory(@PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto request) {
        return categoryService.updateCategory(id, request);
    }

    /**
     * Elimina una categoria por su identificador
     */
    @DeleteMapping("/delete-category/{id}")
    public HttpGlobalResponse<CategoryResponseDto> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}