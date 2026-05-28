package com.example.SuperMarket.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.stereotype.Service;
 
import com.example.SuperMarket.dto.Categoryrequestdto;
import com.example.SuperMarket.dto.Categoryresponsedto;
import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;
import com.example.SuperMarket.dto.Productresponsedto;
import com.example.SuperMarket.entity.Category;
import com.example.SuperMarket.entity.Product;
import com.example.SuperMarket.repository.Categoryrepository;
 
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class CategoryService {
 
    private final Categoryrepository categoryRepository;
 
    public Messageresponsedto createCategory(Categoryrequestdto request) {
        Messageresponsedto response = new Messageresponsedto();
 
        if (request.getName() == null || request.getName().isEmpty()) {
            response.setMessage("Nombre de categoria es requerido");
            return response;
        }
 
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
 
        response.setMessage("La categoria fue creada completamente");
        return response;
    }
 
    public List<Categoryresponsedto> getCategories() {
        List<Categoryresponsedto> categoryList = new ArrayList<>();
        List<Category> categoriesFound = categoryRepository.findAll();
 
        for (Category category : categoriesFound) {
            Categoryresponsedto categoryDTO = new Categoryresponsedto();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());
            categoryList.add(categoryDTO);
        }
 
        return categoryList;
    }
 
    public Httpglobalresponse<Categoryresponsedto> getCategory(Long id) {
        Httpglobalresponse<Categoryresponsedto> response = new Httpglobalresponse<>();
        Optional<Category> categoryFound = categoryRepository.findById(id);
 
        if (categoryFound.isEmpty()) {
            response.setMessage("Categoria no encontrada");
            return response;
        }
 
        Category category = categoryFound.get();
 
        Categoryresponsedto categoryDTO = new Categoryresponsedto();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
 
        
        List<Productresponsedto> activeProducts = new ArrayList<>();
        if (category.getProducts() != null) {
            for (Product product : category.getProducts()) {
                if (product.getActive() != null && product.getActive()) {
                    Productresponsedto productDTO = new Productresponsedto();
                    productDTO.setId(product.getId());
                    productDTO.setName(product.getName());
                    productDTO.setBarcode(product.getBarcode());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setStock(product.getStock());
                    productDTO.setActive(product.getActive());
                    activeProducts.add(productDTO);
                }
            }
        }
        categoryDTO.setProducts(activeProducts);
 
        response.setMessage("Categoria encontrada");
        response.setData(categoryDTO);
        return response;
    }
 
    public Httpglobalresponse<Categoryresponsedto> updateCategory(Long id, Categoryrequestdto request) {
        Httpglobalresponse<Categoryresponsedto> response = new Httpglobalresponse<>();
        Optional<Category> categoryFound = categoryRepository.findById(id);
 
        if (categoryFound.isEmpty()) {
            response.setMessage("La categoria no se encuentra");
            return response;
        }
 
        Category category = categoryFound.get();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
 
        Categoryresponsedto categoryDTO = new Categoryresponsedto();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
 
        response.setMessage("Categoria actualizada completamente");
        response.setData(categoryDTO);
        return response;
    }
 
    public Httpglobalresponse<Categoryresponsedto> deleteCategory(Long id) {
        Httpglobalresponse<Categoryresponsedto> response = new Httpglobalresponse<>();
        Optional<Category> categoryFound = categoryRepository.findById(id);
 
        if (categoryFound.isEmpty()) {
            response.setMessage("Categoria no encontrada");
            return response;
        }
 
        categoryRepository.deleteById(id);
        response.setMessage("Categoria eliminada completamente");
        return response;
    }
}
