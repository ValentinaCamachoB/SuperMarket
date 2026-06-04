package com.example.SuperMarket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.SuperMarket.dto.HttpGlobalResponse;
import com.example.SuperMarket.dto.MessageResponseDto;
import com.example.SuperMarket.dto.ProductRequestDto;
import com.example.SuperMarket.dto.ProductResponseDto;
import com.example.SuperMarket.entity.Category;
import com.example.SuperMarket.entity.Product;
import com.example.SuperMarket.repository.CategoryRepository;
import com.example.SuperMarket.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public MessageResponseDto createProduct(ProductRequestDto request) {
        MessageResponseDto response = new MessageResponseDto();

        Optional<Product> productWithSameBarcode = productRepository.findByBarcode(request.getBarcode());
        if (productWithSameBarcode.isPresent()) {
            response.setMessage("Ya existe un producto con ese código de barras");
            return response;
        }

        Optional<Category> categoryFound = categoryRepository.findById(request.getCategoryId());
        if (categoryFound.isEmpty()) {
            response.setMessage("Categoría no encontrada");
            return response;
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setBarcode(request.getBarcode());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setActive(true);
        product.setCategory(categoryFound.get());
        productRepository.save(product);

        response.setMessage("Producto creado exitosamente");
        return response;
    }

    public List<ProductResponseDto> getProducts() {
        List<ProductResponseDto> productList = new ArrayList<>();
        List<Product> productsFound = productRepository.findAll();

        for (Product product : productsFound) {
            productList.add(mapToDTO(product));
        }

        return productList;
    }

    public HttpGlobalResponse<ProductResponseDto> getProduct(Long id) {
        HttpGlobalResponse<ProductResponseDto> response = new HttpGlobalResponse<>();
        Optional<Product> productFound = productRepository.findById(id);

        if (productFound.isEmpty()) {
            response.setMessage("Producto no encontrado");
            return response;
        }

        response.setMessage("Producto encontrado");
        response.setData(mapToDTO(productFound.get()));
        return response;
    }

    public HttpGlobalResponse<ProductResponseDto> updateProduct(Long id, ProductRequestDto request) {
        HttpGlobalResponse<ProductResponseDto> response = new HttpGlobalResponse<>();
        Optional<Product> productFound = productRepository.findById(id);

        if (productFound.isEmpty()) {
            response.setMessage("Producto no encontrado");
            return response;
        }

        Optional<Product> productWithSameBarcode = productRepository.findByBarcode(request.getBarcode());
        if (productWithSameBarcode.isPresent() && !productWithSameBarcode.get().getId().equals(id)) {
            response.setMessage("Otro producto ya utiliza ese código de barras");
            return response;
        }

        Optional<Category> categoryFound = categoryRepository.findById(request.getCategoryId());
        if (categoryFound.isEmpty()) {
            response.setMessage("Categoría no encontrada");
            return response;
        }

        Product product = productFound.get();
        product.setName(request.getName());
        product.setBarcode(request.getBarcode());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(categoryFound.get());
        productRepository.save(product);

        response.setMessage("Producto actualizado exitosamente");
        response.setData(mapToDTO(product));
        return response;
    }


    public HttpGlobalResponse<ProductResponseDto> deactivateProduct(Long id) {
        HttpGlobalResponse<ProductResponseDto> response = new HttpGlobalResponse<>();
        Optional<Product> productFound = productRepository.findById(id);

        if (productFound.isEmpty()) {
            response.setMessage("Producto no encontrado");
            return response;
        }

        Product product = productFound.get();
        product.setActive(false);
        productRepository.save(product);

        response.setMessage("Producto desactivado exitosamente");
        response.setData(mapToDTO(product));
        return response;
    }

    private ProductResponseDto mapToDTO(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBarcode(product.getBarcode());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setActive(product.getActive());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
}
