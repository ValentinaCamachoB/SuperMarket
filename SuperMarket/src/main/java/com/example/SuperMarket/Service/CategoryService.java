package com.example.SuperMarket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.stereotype.Service;
 
import com.example.SuperMarket.dto.CategoryRequestDto;
import com.example.SuperMarket.dto.CategoryResponseDto;
import com.example.SuperMarket.dto.HttpGlobalResponse;
import com.example.SuperMarket.dto.MessageResponseDto;
import com.example.SuperMarket.dto.ProductResponseDto;
import com.example.SuperMarket.entity.Category;
import com.example.SuperMarket.entity.Product;
import com.example.SuperMarket.repository.CategoryRepository;
import com.example.SuperMarket.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public MessageResponseDto createCategory(CategoryRequestDto request) {
        MessageResponseDto response = new MessageResponseDto();

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);

        response.setMessage("La categoría fue creada exitosamente");
        return response;
    }

    public List<CategoryResponseDto> getCategories() {
        List<CategoryResponseDto> categoryList = new ArrayList<>();
        List<Category> categoriesFound = categoryRepository.findAll();

        for (Category category : categoriesFound) {
            CategoryResponseDto categoryDTO = new CategoryResponseDto();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());
            categoryList.add(categoryDTO);
        }

        return categoryList;
    }

    public HttpGlobalResponse<CategoryResponseDto> getCategory(Long id) {
        HttpGlobalResponse<CategoryResponseDto> response = new HttpGlobalResponse<>();
        Optional<Category> categoryFound = categoryRepository.findById(id);

        if (categoryFound.isEmpty()) {
            response.setMessage("Categoría no encontrada");
            return response;
        }

        Category category = categoryFound.get();

        CategoryResponseDto categoryDTO = new CategoryResponseDto();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());

        List<ProductResponseDto> activeProducts = new ArrayList<>();
        if (category.getProducts() != null) {
            for (Product product : category.getProducts()) {
                if (product.getActive() != null && product.getActive()) {
                    ProductResponseDto productDTO = new ProductResponseDto();
                    productDTO.setId(product.getId());
                    productDTO.setName(product.getName());
                    productDTO.setBarcode(product.getBarcode());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setStock(product.getStock());
                    productDTO.setActive(product.getActive());
                    productDTO.setCategoryName(category.getName());
                    activeProducts.add(productDTO);
                }
            }
        }
        categoryDTO.setProducts(activeProducts);

        response.setMessage("Categoría encontrada");
        response.setData(categoryDTO);
        return response;
    }

    public HttpGlobalResponse<CategoryResponseDto> updateCategory(Long id, CategoryRequestDto request) {
        HttpGlobalResponse<CategoryResponseDto> response = new HttpGlobalResponse<>();
        Optional<Category> categoryFound = categoryRepository.findById(id);

        if (categoryFound.isEmpty()) {
            response.setMessage("Categoría no encontrada");
            return response;
        }

        Category category = categoryFound.get();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);

        CategoryResponseDto categoryDTO = new CategoryResponseDto();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());

        response.setMessage("Categoría actualizada exitosamente");
        response.setData(categoryDTO);
        return response;
    }

    public HttpGlobalResponse<CategoryResponseDto> deleteCategory(Long id) {
        HttpGlobalResponse<CategoryResponseDto> response = new HttpGlobalResponse<>();
        Optional<Category> categoryFound = categoryRepository.findById(id);

        if (categoryFound.isEmpty()) {
            response.setMessage("Categoría no encontrada");
            return response;
        }

        long productCount = productRepository.countByCategoryId(id);
        if (productCount > 0) {
            response.setMessage("No se puede eliminar la categoría porque tiene "
                    + productCount + " producto(s) asociado(s).");
            return response;
        }

        categoryRepository.deleteById(id);
        response.setMessage("Categoría eliminada exitosamente");
        return response;
    }
}
