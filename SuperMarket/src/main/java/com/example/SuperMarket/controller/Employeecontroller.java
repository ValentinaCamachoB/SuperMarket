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

import com.example.SuperMarket.dto.EmployeeRequestDto;
import com.example.SuperMarket.dto.EmployeeResponseDto;
import com.example.SuperMarket.dto.HttpGlobalResponse;
import com.example.SuperMarket.dto.MessageResponseDto;
import com.example.SuperMarket.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Crea un nuevo empleado
     */
    @PostMapping("/create")
    public ResponseEntity<MessageResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto request) {
        try {
            MessageResponseDto response = employeeService.createEmployee(request);
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
    public List<EmployeeResponseDto> getEmployees() {
        return employeeService.getEmployees();
    }

    /**
     * Obtiene un empleado por su identificador
     */
    @GetMapping("/get-employee/{id}")
    public HttpGlobalResponse<EmployeeResponseDto> getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    /**
     * Actualiza los datos de un empleado existente
     */
    @PutMapping("/update-employee/{id}")
    public HttpGlobalResponse<EmployeeResponseDto> updateEmployee(@PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto request) {
        return employeeService.updateEmployee(id, request);
    }

    /**
     * Elimina un empleado por su identificador
     */
    @DeleteMapping("/delete-employee/{id}")
    public HttpGlobalResponse<EmployeeResponseDto> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    /**
     * Empleados filtrados por posicion
     */
    @GetMapping("/by-position")
    public List<EmployeeResponseDto> getEmployeesByPosition(@RequestParam String position) {
        return employeeService.getEmployeesByPosition(position);
    }

    /**
     * Empleados contratados dentro de un rango de fechas
     */
    @GetMapping("/by-date")
    public List<EmployeeResponseDto> getEmployeesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return employeeService.getEmployeesByDateRange(startDate, endDate);
    }
}
