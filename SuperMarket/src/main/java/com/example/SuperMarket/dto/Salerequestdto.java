package com.example.SuperMarket.dto;

import java.util.List;
 
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Data
public class Salerequestdto {
 
    @NotNull(message = "Se requiere el ID del empleado")
    private Long employeeId;
 
    @NotEmpty(message = "La venta debe incluir al menos un producto")
    @Valid
    private List<Saledetailrequestdto> details;
}
