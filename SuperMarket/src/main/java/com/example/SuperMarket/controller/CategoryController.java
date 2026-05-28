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
 
import com.example.SuperMarket.Service.CategoryService;
import com.example.SuperMarket.dto.Categoryrequestdto;
import com.example.SuperMarket.dto.Categoryresponsedto;
import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;
 
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
 
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class Categorycontroller {
 
    private final CategoryService categoryService;
 
    @PostMapping("/create")
    public ResponseEntity<Messageresponsedto> createCategory(@Valid @RequestBody Categoryrequestdto request) {
        try {
            Messageresponsedto response = categoryService.createCategory(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
 
    @GetMapping("/get-categories")
    public List<Categoryresponsedto> getCategories() {
        return categoryService.getCategories();
    }
 
    @GetMapping("/get-category/{id}")
    public Httpglobalresponse<Categoryresponsedto> getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }
 
    @PutMapping("/update-category/{id}")
    public Httpglobalresponse<Categoryresponsedto> updateCategory(@PathVariable Long id,
            @Valid @RequestBody Categoryrequestdto request) {
        return categoryService.updateCategory(id, request);
    }
 
    @DeleteMapping("/delete-category/{id}")
    public Httpglobalresponse<Categoryresponsedto> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}