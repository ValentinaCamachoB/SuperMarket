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

import com.example.SuperMarket.Service.Supplierservice;
import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;
import com.example.SuperMarket.dto.Supplierrequestdto;
import com.example.SuperMarket.dto.Supplierresponsedto;
import com.example.SuperMarket.dto.Warehouseentryrequestdto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class Suppliercontroller {

    private final Supplierservice supplierService;

    // FIX: recibe Supplierrequestdto (no Salerequestdto)
    @PostMapping("/create")
    public ResponseEntity<Messageresponsedto> createSupplier(@Valid @RequestBody Supplierrequestdto request) {
        try {
            Messageresponsedto response = supplierService.createSupplier(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // FIX: retorna List<Supplierresponsedto> (response, no request)
    @GetMapping("/get-suppliers")
    public List<Supplierresponsedto> getSuppliers() {
        return supplierService.getSuppliers();
    }

    // FIX: retorna Httpglobalresponse<Supplierresponsedto> (response, no request)
    @GetMapping("/get-supplier/{id}")
    public Httpglobalresponse<Supplierresponsedto> getSupplier(@PathVariable Long id) {
        return supplierService.getSupplier(id);
    }

    // FIX: retorna Httpglobalresponse<Supplierresponsedto> (response, no request)
    @PutMapping("/update-supplier/{id}")
    public Httpglobalresponse<Supplierresponsedto> updateSupplier(@PathVariable Long id,
            @Valid @RequestBody Supplierrequestdto request) {
        return supplierService.updateSupplier(id, request);
    }

    // FIX: retorna Httpglobalresponse<Supplierresponsedto> (response, no request)
    @DeleteMapping("/delete-supplier/{id}")
    public Httpglobalresponse<Supplierresponsedto> deleteSupplier(@PathVariable Long id) {
        return supplierService.deleteSupplier(id);
    }

    @PostMapping("/warehouse-entry")
    public ResponseEntity<Messageresponsedto> warehouseEntry(@Valid @RequestBody Warehouseentryrequestdto request) {
        try {
            Messageresponsedto response = supplierService.warehouseEntry(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}