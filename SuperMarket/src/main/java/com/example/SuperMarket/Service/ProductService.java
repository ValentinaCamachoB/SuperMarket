package com.example.SuperMarket.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;
import com.example.SuperMarket.dto.Productrequestdto;
import com.example.SuperMarket.dto.Productresponsedto;
import com.example.SuperMarket.entity.Category;
import com.example.SuperMarket.entity.Product;
import com.example.SuperMarket.repository.Categoryrepository;
import com.example.SuperMarket.repository.Productrepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final Productrepository productRepository;
    private final Categoryrepository categoryRepository;

    public Messageresponsedto createProduct(Productrequestdto request) {
        Messageresponsedto response = new Messageresponsedto();

        if (request.getName() == null || request.getName().isEmpty()) {
            response.setMessage("Product name is required");
            return response;
        }

        if (request.getBarcode() == null || request.getBarcode().isEmpty()) {
            response.setMessage("Barcode is required");
            return response;
        }

        Optional<Product> productWithSameBarcode = productRepository.findByBarcode(request.getBarcode());
        if (productWithSameBarcode.isPresent()) {
            response.setMessage("A product with that barcode already exists");
            return response;
        }

        if (request.getPrice() == null) {
            response.setMessage("Price is required");
            return response;
        }

        if (request.getCategoryId() == null) {
            response.setMessage("Category is required");
            return response;
        }

        Optional<Category> categoryFound = categoryRepository.findById(request.getCategoryId());
        if (categoryFound.isEmpty()) {
            response.setMessage("Category not found");
            return response;
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setBarcode(request.getBarcode());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock() != null ? request.getStock() : 0);
        product.setActive(true);
        product.setCategory(categoryFound.get());
        productRepository.save(product);

        response.setMessage("Product created successfully");
        return response;
    }

    public List<Productresponsedto> getProducts() {
        List<Productresponsedto> productList = new ArrayList<>();
        List<Product> productsFound = productRepository.findAll();

        for (Product product : productsFound) {
            Productresponsedto productDTO = new Productresponsedto();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setBarcode(product.getBarcode());
            productDTO.setPrice(product.getPrice());
            productDTO.setStock(product.getStock());
            productDTO.setActive(product.getActive());
            if (product.getCategory() != null) {
                productDTO.setCategoryName(product.getCategory().getName());
            }
            productList.add(productDTO);
        }

        return productList;
    }

    public Httpglobalresponse<Productresponsedto> getProduct(Long id) {
        Httpglobalresponse<Productresponsedto> response = new Httpglobalresponse<>();
        Optional<Product> productFound = productRepository.findById(id);

        if (productFound.isEmpty()) {
            response.setMessage("Product not found");
            return response;
        }

        Product product = productFound.get();

        Productresponsedto productDTO = new Productresponsedto();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setBarcode(product.getBarcode());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setActive(product.getActive());
        if (product.getCategory() != null) {
            productDTO.setCategoryName(product.getCategory().getName());
        }

        response.setMessage("Product found");
        response.setData(productDTO);
        return response;
    }

    public Httpglobalresponse<Productresponsedto> updateProduct(Long id, Productrequestdto request) {
        Httpglobalresponse<Productresponsedto> response = new Httpglobalresponse<>();
        Optional<Product> productFound = productRepository.findById(id);

        if (productFound.isEmpty()) {
            response.setMessage("Product not found");
            return response;
        }

        if (request.getBarcode() != null) {
            Optional<Product> productWithSameBarcode = productRepository.findByBarcode(request.getBarcode());
            if (productWithSameBarcode.isPresent() && !productWithSameBarcode.get().getId().equals(id)) {
                response.setMessage("Another product already uses that barcode");
                return response;
            }
        }

        Product product = productFound.get();
        product.setName(request.getName());
        product.setBarcode(request.getBarcode());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        if (request.getCategoryId() != null) {
            Optional<Category> categoryFound = categoryRepository.findById(request.getCategoryId());
            if (categoryFound.isPresent()) {
                product.setCategory(categoryFound.get());
            }
        }

        productRepository.save(product);

        Productresponsedto productDTO = new Productresponsedto();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setBarcode(product.getBarcode());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setActive(product.getActive());

        response.setMessage("Product updated successfully");
        response.setData(productDTO);
        return response;
    }

    // Business Rule 1: Soft delete - only deactivates, never physically deletes
    public Httpglobalresponse<Productresponsedto> deactivateProduct(Long id) {
        Httpglobalresponse<Productresponsedto> response = new Httpglobalresponse<>();
        Optional<Product> productFound = productRepository.findById(id);

        if (productFound.isEmpty()) {
            response.setMessage("Product not found");
            return response;
        }

        Product product = productFound.get();
        product.setActive(false);
        productRepository.save(product);

        response.setMessage("Product deactivated successfully");
        return response;
    }
}