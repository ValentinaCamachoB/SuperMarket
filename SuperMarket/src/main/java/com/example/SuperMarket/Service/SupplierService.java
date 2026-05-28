package com.example.SuperMarket.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.stereotype.Service;
 
import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;
import com.example.SuperMarket.dto.Supplierrequestdto;
import com.example.SuperMarket.dto.Supplierresponsedto;
import com.example.SuperMarket.dto.Warehouseentryrequestdto;
import com.example.SuperMarket.entity.Product;
import com.example.SuperMarket.entity.Supplier;
import com.example.SuperMarket.repository.Productrepository;
import com.example.SuperMarket.repository.Supplierrepository;
 
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class Supplierservice {
 
    private final Supplierrepository supplierRepository;
    private final Productrepository productRepository;
 
    public Messageresponsedto createSupplier(Supplierrequestdto request) {
        Messageresponsedto response = new Messageresponsedto();
 
        // Regla de negocio 2: El NIT es obligatorio y debe ser único.
        if (request.getNit() == null || request.getNit().isEmpty()) {
            response.setMessage("Se requiere NIT del proveedor");
            return response;
        }
 
        Optional<Supplier> supplierWithSameNit = supplierRepository.findByNit(request.getNit());
        if (supplierWithSameNit.isPresent()) {
            response.setMessage("Ya existe un proveedor con esa NIT.");
            return response;
        }
 
        if (request.getName() == null || request.getName().isEmpty()) {
            response.setMessage("El nombre del proveedor es obligatorio.");
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
 
    public List<Supplierresponsedto> getSuppliers() {
        List<Supplierresponsedto> supplierList = new ArrayList<>();
        List<Supplier> suppliersFound = supplierRepository.findAll();
 
        for (Supplier supplier : suppliersFound) {
           Supplierresponsedto supplierDTO = new Supplierresponsedto();
            supplierDTO.setId(supplier.getId());
            supplierDTO.setName(supplier.getName());
            supplierDTO.setNit(supplier.getNit());
            supplierDTO.setPhone(supplier.getPhone());
            supplierDTO.setEmail(supplier.getEmail());
            supplierList.add(supplierDTO);
        }
 
        return supplierList;
    }
 
    public Httpglobalresponse<Supplierresponsedto> getSupplier(Long id) {
        Httpglobalresponse<Supplierresponsedto> response = new Httpglobalresponse<>();
        Optional<Supplier> supplierFound = supplierRepository.findById(id);
 
        if (supplierFound.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }
 
        Supplier supplier = supplierFound.get();
 
        Supplierresponsedto supplierDTO = new Supplierresponsedto();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setName(supplier.getName());
        supplierDTO.setNit(supplier.getNit());
        supplierDTO.setPhone(supplier.getPhone());
        supplierDTO.setEmail(supplier.getEmail());
 
        response.setMessage("Provedor encontrado");
        response.setData(supplierDTO);
        return response;
    }
 
    public Httpglobalresponse<Supplierresponsedto> updateSupplier(Long id, Supplierrequestdto request) {
        Httpglobalresponse<Supplierresponsedto> response = new Httpglobalresponse<>();
        Optional<Supplier> supplierFound = supplierRepository.findById(id);
 
        if (supplierFound.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }
 
        // Validar que NIT no sea utilizado por otro proveedor
        if (request.getNit() != null) {
            Optional<Supplier> supplierWithSameNit = supplierRepository.findByNit(request.getNit());
            if (supplierWithSameNit.isPresent() && !supplierWithSameNit.get().getId().equals(id)) {
                response.setMessage("Otro proveedor ya utiliza ese NIT.");
                return response;
            }
        }
 
        Supplier supplier = supplierFound.get();
        supplier.setName(request.getName());
        supplier.setNit(request.getNit());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplierRepository.save(supplier);
 
        Supplierresponsedto supplierDTO = new Supplierresponsedto();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setName(supplier.getName());
        supplierDTO.setNit(supplier.getNit());
        supplierDTO.setPhone(supplier.getPhone());
        supplierDTO.setEmail(supplier.getEmail());
 
        response.setMessage("Proveedor actualizado exitosamente");
        response.setData(supplierDTO);
        return response;
    }
 
    public Httpglobalresponse<Supplierresponsedto> deleteSupplier(Long id) {
        Httpglobalresponse<Supplierresponsedto> response = new Httpglobalresponse<>();
        Optional<Supplier> supplierFound = supplierRepository.findById(id);
 
        if (supplierFound.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }
 
        supplierRepository.deleteById(id);
        response.setMessage("Proveedor eliminado exitosamente");
        return response;
    }
   // Entrada de almacén registrada. Stock actualizado:
    public Messageresponsedto warehouseEntry(Warehouseentryrequestdto request) {
        Messageresponsedto response = new Messageresponsedto();
 
        Optional<Product> productFound = productRepository.findById(request.getProductId());
        if (productFound.isEmpty()) {
            response.setMessage("Producto no encotrado");
            return response;
        }
 
        Optional<Supplier> supplierFound = supplierRepository.findById(request.getSupplierId());
        if (supplierFound.isEmpty()) {
            response.setMessage("Proveedor no encontrado");
            return response;
        }
 
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            response.setMessage("La cantidad debe ser mayor que 0");
            return response;
        }
 
        // Agregar unidades al stock
        Product product = productFound.get();
        int currentStock = product.getStock() != null ? product.getStock() : 0;
        product.setStock(currentStock + request.getQuantity());
        productRepository.save(product);
 
        response.setMessage("Entrada de almacén registrada. Stock actualizado: " + product.getStock());
        return response;
    }
}
