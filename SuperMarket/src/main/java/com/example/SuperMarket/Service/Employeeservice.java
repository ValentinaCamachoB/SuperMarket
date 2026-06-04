package com.example.SuperMarket.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.stereotype.Service;
 
import com.example.SuperMarket.dto.EmployeeRequestDto;
import com.example.SuperMarket.dto.EmployeeResponseDto;
import com.example.SuperMarket.dto.HttpGlobalResponse;
import com.example.SuperMarket.dto.MessageResponseDto;
import com.example.SuperMarket.entity.Employee;
import com.example.SuperMarket.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public MessageResponseDto createEmployee(EmployeeRequestDto request) {
        MessageResponseDto response = new MessageResponseDto();

        Optional<Employee> employeeWithSameId = employeeRepository.findByIdNumber(request.getIdNumber());
        if (employeeWithSameId.isPresent()) {
            response.setMessage("Ya existe un empleado con ese número de identificación.");
            return response;
        }

        String position = request.getPosition().toUpperCase();
        if (!position.equals("ADMINISTRATOR") && !position.equals("CASHIER") && !position.equals("AUXILIARY")) {
            response.setMessage("Posición no válida. Solo se permite: ADMINISTRATOR, CASHIER, AUXILIARY");
            return response;
        }

        Employee employee = new Employee();
        employee.setIdNumber(request.getIdNumber());
        employee.setName(request.getName());
        employee.setPosition(position);
        employee.setHireDate(request.getHireDate());
        employee.setSalary(request.getSalary());
        employeeRepository.save(employee);

        response.setMessage("Empleado creado exitosamente");
        return response;
    }

    public List<EmployeeResponseDto> getEmployees() {
        List<EmployeeResponseDto> employeeList = new ArrayList<>();
        List<Employee> employeesFound = employeeRepository.findAll();

        for (Employee employee : employeesFound) {
            employeeList.add(mapToDTO(employee));
        }

        return employeeList;
    }

    public HttpGlobalResponse<EmployeeResponseDto> getEmployee(Long id) {
        HttpGlobalResponse<EmployeeResponseDto> response = new HttpGlobalResponse<>();
        Optional<Employee> employeeFound = employeeRepository.findById(id);

        if (employeeFound.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }

        response.setMessage("Empleado encontrado");
        response.setData(mapToDTO(employeeFound.get()));
        return response;
    }

    public HttpGlobalResponse<EmployeeResponseDto> updateEmployee(Long id, EmployeeRequestDto request) {
        HttpGlobalResponse<EmployeeResponseDto> response = new HttpGlobalResponse<>();
        Optional<Employee> employeeFound = employeeRepository.findById(id);

        if (employeeFound.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }

        String position = request.getPosition().toUpperCase();
        if (!position.equals("ADMINISTRATOR") && !position.equals("CASHIER") && !position.equals("AUXILIARY")) {
            response.setMessage("Posición no válida. Solo se permite: ADMINISTRATOR, CASHIER, AUXILIARY");
            return response;
        }

        Optional<Employee> employeeWithSameId = employeeRepository.findByIdNumber(request.getIdNumber());
        if (employeeWithSameId.isPresent() && !employeeWithSameId.get().getId().equals(id)) {
            response.setMessage("Otro empleado ya usa ese número de identificación.");
            return response;
        }

        Employee employee = employeeFound.get();
        employee.setIdNumber(request.getIdNumber());
        employee.setName(request.getName());
        employee.setPosition(position);
        employee.setHireDate(request.getHireDate());
        employee.setSalary(request.getSalary());
        employeeRepository.save(employee);

        response.setMessage("Empleado actualizado exitosamente");
        response.setData(mapToDTO(employee));
        return response;
    }

    public HttpGlobalResponse<EmployeeResponseDto> deleteEmployee(Long id) {
        HttpGlobalResponse<EmployeeResponseDto> response = new HttpGlobalResponse<>();
        Optional<Employee> employeeFound = employeeRepository.findById(id);

        if (employeeFound.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }

        employeeRepository.deleteById(id);
        response.setMessage("Empleado eliminado exitosamente");
        return response;
    }

    public List<EmployeeResponseDto> getEmployeesByPosition(String position) {
        List<EmployeeResponseDto> employeeList = new ArrayList<>();
        List<Employee> employeesFound = employeeRepository.findByPosition(position.toUpperCase());

        for (Employee employee : employeesFound) {
            employeeList.add(mapToDTO(employee));
        }

        return employeeList;
    }

    public List<EmployeeResponseDto> getEmployeesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<EmployeeResponseDto> employeeList = new ArrayList<>();
        List<Employee> employeesFound = employeeRepository.findByHireDateBetween(startDate, endDate);

        for (Employee employee : employeesFound) {
            employeeList.add(mapToDTO(employee));
        }

        return employeeList;
    }

    private EmployeeResponseDto mapToDTO(Employee employee) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(employee.getId());
        dto.setIdNumber(employee.getIdNumber());
        dto.setName(employee.getName());
        dto.setPosition(employee.getPosition());
        dto.setHireDate(employee.getHireDate());
        dto.setSalary(employee.getSalary());
        return dto;
    }
}
