package com.example.SuperMarket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.stereotype.Service;
 
import com.example.SuperMarket.dto.HttpGlobalResponse;
import com.example.SuperMarket.dto.MessageResponseDto;
import com.example.SuperMarket.dto.SupplierRequestDto;
import com.example.SuperMarket.dto.SupplierResponseDto;
import com.example.SuperMarket.dto.WarehouseEntryRequestDto;
import com.example.SuperMarket.entity.Product;
import com.example.SuperMarket.entity.Supplier;
import com.example.SuperMarket.repository.ProductRepository;
import com.example.SuperMarket.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public MessageResponseDto createSupplier(SupplierRequestDto request) {
        MessageResponseDto response = new MessageResponseDto();

        
        Optional<Supplier> supplierWithSameNit = supplierRepository.findByNit(request.getNit());
        if (supplierWithSameNit.isPresent()) {
            response.setMessage("Ya existe un proveedor con ese NIT.");
            return response;
        }

        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setNit(request.getNit());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplierRepository.save(supplier);

        response.setMessage("Proveedor creado exitosamente");
        return response;
    }

    public List<SupplierResponseDto> getSuppliers() {
        List<SupplierResponseDto> supplierList = new ArrayList<>();
        List<Supplier> suppliersFound = supplierRepository.findAll();

        for (Supplier supplier : suppliersFound) {
            supplierList.add(mapToDTO(supplier));
        }

        return supplierList;
    }

    public HttpGlobalResponse<SupplierResponseDto> getSupplier(Long id) {
        HttpGlobalResponse<SupplierResponseDto> response = new HttpGlobalResponse<>();
        Optional<Supplier> supplierFound = supplierRepository.findById(id);

        if (supplierFound.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }

        response.setMessage("Proveedor encontrado");
        response.setData(mapToDTO(supplierFound.get()));
        return response;
    }

    public HttpGlobalResponse<SupplierResponseDto> updateSupplier(Long id, SupplierRequestDto request) {
        HttpGlobalResponse<SupplierResponseDto> response = new HttpGlobalResponse<>();
        Optional<Supplier> supplierFound = supplierRepository.findById(id);

        if (supplierFound.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }

        Optional<Supplier> supplierWithSameNit = supplierRepository.findByNit(request.getNit());
        if (supplierWithSameNit.isPresent() && !supplierWithSameNit.get().getId().equals(id)) {
            response.setMessage("Otro proveedor ya utiliza ese NIT.");
            return response;
        }

        Supplier supplier = supplierFound.get();
        supplier.setName(request.getName());
        supplier.setNit(request.getNit());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplierRepository.save(supplier);

        response.setMessage("Proveedor actualizado exitosamente");
        response.setData(mapToDTO(supplier));
        return response;
    }

    public HttpGlobalResponse<SupplierResponseDto> deleteSupplier(Long id) {
        HttpGlobalResponse<SupplierResponseDto> response = new HttpGlobalResponse<>();
        Optional<Supplier> supplierFound = supplierRepository.findById(id);

        if (supplierFound.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }

        supplierRepository.deleteById(id);
        response.setMessage("Proveedor eliminado exitosamente");
        return response;
    }

    public MessageResponseDto warehouseEntry(WarehouseEntryRequestDto request) {
        MessageResponseDto response = new MessageResponseDto();

        Optional<Product> productFound = productRepository.findById(request.getProductId());
        if (productFound.isEmpty()) {
            response.setMessage("Producto no encontrado");
            return response;
        }

        Optional<Supplier> supplierFound = supplierRepository.findById(request.getSupplierId());
        if (supplierFound.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }

        Product product = productFound.get();
        int currentStock = product.getStock() != null ? product.getStock() : 0;
        product.setStock(currentStock + request.getQuantity());
        productRepository.save(product);

        response.setMessage("Entrada de almacén registrada. Stock actualizado: " + product.getStock());
        return response;
    }

    private SupplierResponseDto mapToDTO(Supplier supplier) {
        SupplierResponseDto dto = new SupplierResponseDto();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setNit(supplier.getNit());
        dto.setPhone(supplier.getPhone());
        dto.setEmail(supplier.getEmail());
        return dto;
    }
}
