package com.example.SuperMarket.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
 
import org.springframework.stereotype.Service;
 
import com.example.SuperMarket.dto.HttpGlobalResponse;
import com.example.SuperMarket.dto.MessageResponseDto;
import com.example.SuperMarket.dto.SaleDetailRequestDto;
import com.example.SuperMarket.dto.SaleDetailResponseDto;
import com.example.SuperMarket.dto.SaleRequestDto;
import com.example.SuperMarket.dto.SaleResponseDto;
import com.example.SuperMarket.entity.Employee;
import com.example.SuperMarket.entity.Product;
import com.example.SuperMarket.entity.Sale;
import com.example.SuperMarket.entity.SaleDetail;
import com.example.SuperMarket.repository.EmployeeRepository;
import com.example.SuperMarket.repository.ProductRepository;
import com.example.SuperMarket.repository.SaleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

    private static final double TAX_RATE = 0.19;

    private final SaleRepository saleRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;

    public MessageResponseDto createSale(SaleRequestDto request) {
        MessageResponseDto response = new MessageResponseDto();

        Optional<Employee> employeeFound = employeeRepository.findById(request.getEmployeeId());
        if (employeeFound.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }

        Map<Long, Product> validatedProducts = new HashMap<>();
        for (SaleDetailRequestDto detail : request.getDetails()) {
            Product product = validatedProducts.get(detail.getProductId());
            if (product == null) {
                Optional<Product> productFound = productRepository.findById(detail.getProductId());
                if (productFound.isEmpty()) {
                    response.setMessage("Producto con ID " + detail.getProductId() + " no encontrado");
                    return response;
                }
                product = productFound.get();
                validatedProducts.put(product.getId(), product);
            }

            if (product.getActive() == null || !product.getActive()) {
                response.setMessage("El producto " + product.getName() + " no está disponible");
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

        Sale sale = new Sale();
        sale.setSaleDate(LocalDate.now());
        sale.setEmployee(employeeFound.get());

        List<SaleDetail> detailList = new ArrayList<>();
        double saleSubtotal = 0.0;

        for (SaleDetailRequestDto detailRequest : request.getDetails()) {
            Product product = validatedProducts.get(detailRequest.getProductId());

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

        double tax = saleSubtotal * TAX_RATE;
        double total = saleSubtotal + tax;

        sale.setSubtotal(saleSubtotal);
        sale.setTax(tax);
        sale.setTotal(total);
        sale.setDetails(detailList);
        saleRepository.save(sale);

        response.setMessage("Venta procesada exitosamente. Total: $" + String.format("%.2f", total));
        return response;
    }

    public List<SaleResponseDto> getSales() {
        List<SaleResponseDto> saleList = new ArrayList<>();
        List<Sale> salesFound = saleRepository.findAll();

        for (Sale sale : salesFound) {
            saleList.add(buildSaleResponseDTO(sale));
        }

        return saleList;
    }

    
    public HttpGlobalResponse<SaleResponseDto> getSale(Long id) {
        HttpGlobalResponse<SaleResponseDto> response = new HttpGlobalResponse<>();
        Optional<Sale> saleFound = saleRepository.findById(id);

        if (saleFound.isEmpty()) {
            response.setMessage("Venta no encontrada");
            return response;
        }

        response.setMessage("Venta encontrada");
        response.setData(buildSaleResponseDTO(saleFound.get()));
        return response;
    }

    private SaleResponseDto buildSaleResponseDTO(Sale sale) {
        SaleResponseDto saleDTO = new SaleResponseDto();
        saleDTO.setId(sale.getId());
        saleDTO.setSaleDate(sale.getSaleDate());
        saleDTO.setSubtotal(sale.getSubtotal());
        saleDTO.setTax(sale.getTax());
        saleDTO.setTotal(sale.getTotal());

        if (sale.getEmployee() != null) {
            saleDTO.setEmployeeName(sale.getEmployee().getName());
        }

        List<SaleDetailResponseDto> detailList = new ArrayList<>();
        if (sale.getDetails() != null) {
            for (SaleDetail detail : sale.getDetails()) {
                SaleDetailResponseDto detailDTO = new SaleDetailResponseDto();
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
