package com.example.SuperMarket.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperMarket.Service.Employeeservice;
import com.example.SuperMarket.dto.Employeerequestdto;
import com.example.SuperMarket.dto.Employeeresponsedto;
import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class Employeecontroller {

    private final Employeeservice employeeService;
    
    /**
     * Crea un nuevo empledo
     */
    @PostMapping("/create")
    public ResponseEntity<Messageresponsedto> createEmployee(@Valid @RequestBody Employeerequestdto request) {
        try {
            Messageresponsedto response = employeeService.createEmployee(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    /**
     * Obtiene la lista de todos los empleados
     */
    @GetMapping("/get-employees")
    public List<Employeeresponsedto> getEmployees() {
        return employeeService.getEmployees();
    }
    
    /**
     * Obtiene un empleado por su identificador
     */
    @GetMapping("/get-employee/{id}")
    public Httpglobalresponse<Employeeresponsedto> getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }
    
    /**
     * Actualiza los datos de un empleado existente
     */
    @PutMapping("/update-employee/{id}")
    public Httpglobalresponse<Employeeresponsedto> updateEmployee(@PathVariable Long id,
            @Valid @RequestBody Employeerequestdto request) {
        return employeeService.updateEmployee(id, request);
    }

    /**
     * Elimina un empleado por su identificador
     */
    @DeleteMapping("/delete-employee/{id}")
    public Httpglobalresponse<Employeeresponsedto> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    /**
     * Obtiene empleados filtrados por posición,  Regla de negocio: filtrar por cargo/posición
     */
    @GetMapping("/by-position")
    public List<Employeeresponsedto> getEmployeesByPosition(@RequestParam String position) {
        return employeeService.getEmployeesByPosition(position);
    }

    /**
     * Obtiene empleados contratados dentro de un rango de fechas, Regla de negocio: filtrar por rango de fecha de contratación
     */
    @GetMapping("/by-date")
    public List<Employeeresponsedto> getEmployeesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return employeeService.getEmployeesByDateRange(startDate, endDate);
    }
}

