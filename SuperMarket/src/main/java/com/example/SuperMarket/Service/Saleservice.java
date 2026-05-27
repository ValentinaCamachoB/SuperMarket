package com.example.SuperMarket.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.stereotype.Service;
 
import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;
import com.example.SuperMarket.dto.Saledetailrequestdto;
import com.example.SuperMarket.dto.Saledetailresponsedto;
import com.example.SuperMarket.dto.Salerequestdto;
import com.example.SuperMarket.dto.Saleresponsedto;
import com.example.SuperMarket.entity.Employee;
import com.example.SuperMarket.entity.Product;
import com.example.SuperMarket.entity.Sale;
import com.example.SuperMarket.entity.SaleDetail;
import com.example.SuperMarket.repository.Employeerepository;
import com.example.SuperMarket.repository.Productrepository;
import com.example.SuperMarket.repository.Salerepository;
 
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class Saleservice {
 
    private final Salerepository saleRepository;
    private final Employeerepository employeeRepository;
    private final Productrepository productRepository;
    

    /**
     * Crea una nueva venta, Valida que exista un empleado, que haya productos en la venta y que todos tengan stock suficiente antes de procesarla.
     */
    public Messageresponsedto createSale(Salerequestdto request) {
        Messageresponsedto response = new Messageresponsedto();
 
        if (request.getEmployeeId() == null) {
            response.setMessage("Se requiere un empleado para procesar la venta");
            return response;
        }
 
        Optional<Employee> employeeFound = employeeRepository.findById(request.getEmployeeId());
        if (employeeFound.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }
 
        if (request.getDetails() == null || request.getDetails().isEmpty()) {
            response.setMessage("La venta debe tener al menos un producto");
            return response;
        }
 
         /**
         * Regla de negocio 2: Validar el stock de TODOS los productos antes de procesar la venta
         */
        for (Saledetailrequestdto detail : request.getDetails()) {
            Optional<Product> productFound = productRepository.findById(detail.getProductId());
 
            if (productFound.isEmpty()) {
                response.setMessage("Producto con ID " + detail.getProductId() + " no encontrado");
                return response;
            }
 
            Product product = productFound.get();
 
            if (product.getActive() == null || !product.getActive()) {
                response.setMessage("El producto " + product.getName() + " no está disponibl");
                return response;
            }
 
            int availableStock = product.getStock() != null ? product.getStock() : 0;
            if (availableStock < detail.getQuantity()) {
                response.setMessage("Stock insuficiente para el producto: " + product.getName()
                        + ". Disponible: " + availableStock
                        + ". Solicitado: " + detail.getQuantity());
                return response;
            }
        }
 
         /**
         * Crear encabezado de la venta
         */
        Sale sale = new Sale();
        sale.setSaleDate(LocalDate.now());
        sale.setEmployee(employeeFound.get());
 
        List<SaleDetail> detailList = new ArrayList<>();
        double saleSubtotal = 0.0;
 
         /**
         * Procesar cada detalle de la venta, Regla de negocio 1: descontar stock automáticamente
         */
        for (Saledetailrequestdto detailRequest : request.getDetails()) {
            Product product = productRepository.findById(detailRequest.getProductId()).get();
 
            product.setStock(product.getStock() - detailRequest.getQuantity());
            productRepository.save(product);
 
            SaleDetail detail = new SaleDetail();
            detail.setProduct(product);
            detail.setQuantity(detailRequest.getQuantity());
            detail.setUnitPrice(product.getPrice());
            double lineSubtotal = product.getPrice() * detailRequest.getQuantity();
            detail.setSubtotal(lineSubtotal);
            detail.setSale(sale);
 
            detailList.add(detail);
            saleSubtotal += lineSubtotal;
        }
 
        /**
         * Regla de negocio 3:Calcular subtotal, IVA (19%) y total automáticamente
         */
        double tax = saleSubtotal * 0.19;
        double total = saleSubtotal + tax;
 
        sale.setSubtotal(saleSubtotal);
        sale.setTax(tax);
        sale.setTotal(total);
        sale.setDetails(detailList);
        saleRepository.save(sale);
 
        response.setMessage("Venta procesada exitosamente. Total: $" + String.format("%.2f", total));
        return response;
    }
    
    /**
     * Obtiene la lista de todas las ventas.
     */
    public List<Saleresponsedto> getSales() {
        List<Saleresponsedto> saleList = new ArrayList<>();
        List<Sale> salesFound = saleRepository.findAll();
 
        for (Sale sale : salesFound) {
            Saleresponsedto saleDTO = buildSaleResponseDTO(sale);
            saleList.add(saleDTO);
        }
 
        return saleList;
    }
    
    /**
     * Obtiene una venta por su identificador.
     */
    public Httpglobalresponse<Saleresponsedto> getSale(Long id) {
        Httpglobalresponse<Saleresponsedto> response = new Httpglobalresponse<>();
        Optional<Sale> saleFound = saleRepository.findById(id);
 
        if (saleFound.isEmpty()) {
            response.setMessage("Venta no encontrada");
            return response;
        }
 
        Saleresponsedto saleDTO = buildSaleResponseDTO(saleFound.get());
        response.setMessage("Venta encontrada");
        response.setData(saleDTO);
        return response;
    }
 
     /**
     * Método auxiliar privado para construir el DTO de respuesta y evitar repetir lógica de mapeo.
     */
    private Saleresponsedto buildSaleResponseDTO(Sale sale) {
        Saleresponsedto saleDTO = new Saleresponsedto();
        saleDTO.setId(sale.getId());
        saleDTO.setSaleDate(sale.getSaleDate());
        saleDTO.setSubtotal(sale.getSubtotal());
        saleDTO.setTax(sale.getTax());
        saleDTO.setTotal(sale.getTotal());
 
        if (sale.getEmployee() != null) {
            saleDTO.setEmployeeName(sale.getEmployee().getName());
        }
 
        List<Saledetailresponsedto> detailList = new ArrayList<>();
        if (sale.getDetails() != null) {
            for (SaleDetail detail : sale.getDetails()) {
                Saledetailresponsedto detailDTO = new Saledetailresponsedto();
                detailDTO.setId(detail.getId());
                detailDTO.setQuantity(detail.getQuantity());
                detailDTO.setUnitPrice(detail.getUnitPrice());
                detailDTO.setSubtotal(detail.getSubtotal());
                if (detail.getProduct() != null) {
                    detailDTO.setProductName(detail.getProduct().getName());
                }
                detailList.add(detailDTO);
            }
        }
        saleDTO.setDetails(detailList);
        return saleDTO;
    }
}
