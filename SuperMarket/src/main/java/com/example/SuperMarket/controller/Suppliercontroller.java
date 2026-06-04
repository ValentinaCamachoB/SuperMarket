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
import com.example.SuperMarket.dto.SupplierRequestDto;
import com.example.SuperMarket.dto.SupplierResponseDto;
import com.example.SuperMarket.dto.WarehouseEntryRequestDto;
import com.example.SuperMarket.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    /**
     * Crea un nuevo proveedor
     */
    @PostMapping("/create")
    public ResponseEntity<MessageResponseDto> createSupplier(@Valid @RequestBody SupplierRequestDto request) {
        try {
            MessageResponseDto response = supplierService.createSupplier(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Obtiene la lista de todos los proveedores
     */
    @GetMapping("/get-suppliers")
    public List<SupplierResponseDto> getSuppliers() {
        return supplierService.getSuppliers();
    }

    /**
     * Obtiene un proveedor por su identificador
     */
    @GetMapping("/get-supplier/{id}")
    public HttpGlobalResponse<SupplierResponseDto> getSupplier(@PathVariable Long id) {
        return supplierService.getSupplier(id);
    }

    /**
     * Actualiza los datos de un proveedor existente
     */
    @PutMapping("/update-supplier/{id}")
    public HttpGlobalResponse<SupplierResponseDto> updateSupplier(@PathVariable Long id,
            @Valid @RequestBody SupplierRequestDto request) {
        return supplierService.updateSupplier(id, request);
    }

    /**
     * Elimina un proveedor por su identificador
     */
    @DeleteMapping("/delete-supplier/{id}")
    public HttpGlobalResponse<SupplierResponseDto> deleteSupplier(@PathVariable Long id) {
        return supplierService.deleteSupplier(id);
    }

    /**
     * Registra una entrada de mercancia al almacen
     */
    @PostMapping("/warehouse-entry")
    public ResponseEntity<MessageResponseDto> warehouseEntry(@Valid @RequestBody WarehouseEntryRequestDto request) {
        try {
            MessageResponseDto response = supplierService.warehouseEntry(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
