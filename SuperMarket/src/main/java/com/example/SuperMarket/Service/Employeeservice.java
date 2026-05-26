package com.example.SuperMarket.Servicie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.stereotype.Service;
 
import com.example.SuperMarket.dto.Employeerequestdto;
import com.example.SuperMarket.dto.Employeeresponsedto;
import com.example.SuperMarket.dto.Httpglobalresponse;
import com.example.SuperMarket.dto.Messageresponsedto;
import com.example.SuperMarket.entity.Employee;
import com.example.SuperMarket.repository.Employeerepository;
 
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class Employeeservice {
 
    private final Employeerepository employeeRepository;
    
    /**
     * Crea un nuevo empleado,Valida que no exista un empleado con el mismo número de identificación y verifica que la posición sea una de las permitidas.
     */
    public Messageresponsedto createEmployee(Employeerequestdto request) {
        Messageresponsedto response = new Messageresponsedto();
 

        Optional<Employee> employeeWithSameId = employeeRepository.findByIdNumber(request.getIdNumber());
        if (employeeWithSameId.isPresent()) {
            response.setMessage("Ya existe un empleado con ese número de identificación.");
            return response;
        }
 
    /**
     * Regla de negocio 1: validar posición manualmente
     */
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
  
     /**
     * Obtiene la lista de todos los empleados.
     */
    public List<Employeeresponsedto> getEmployees() {
        List<Employeeresponsedto> employeeList = new ArrayList<>();
        List<Employee> employeesFound = employeeRepository.findAll();
 
        for (Employee employee : employeesFound) {
            Employeeresponsedto employeeDTO = new Employeeresponsedto();
            employeeDTO.setId(employee.getId());
            employeeDTO.setIdNumber(employee.getIdNumber());
            employeeDTO.setName(employee.getName());
            employeeDTO.setPosition(employee.getPosition());
            employeeDTO.setHireDate(employee.getHireDate());
            employeeDTO.setSalary(employee.getSalary());
            employeeList.add(employeeDTO);
        }
 
        return employeeList;
    }
 
     /**
     * Obtiene un empleado por su identificador.
     */
    public Httpglobalresponse<Employeeresponsedto> getEmployee(Long id) {
        Httpglobalresponse<Employeeresponsedto> response = new Httpglobalresponse<>();
        Optional<Employee> employeeFound = employeeRepository.findById(id);
 
        if (employeeFound.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }
 
        Employee employee = employeeFound.get();
 
        Employeeresponsedto employeeDTO = new Employeeresponsedto();
        employeeDTO.setId(employee.getId());
        employeeDTO.setIdNumber(employee.getIdNumber());
        employeeDTO.setName(employee.getName());
        employeeDTO.setPosition(employee.getPosition());
        employeeDTO.setHireDate(employee.getHireDate());
        employeeDTO.setSalary(employee.getSalary());
 
        response.setMessage("Empleado encontrado");
        response.setData(employeeDTO);
        return response;
    }

    /**
     * Actualiza los datos de un empleado existente
     */
    public Httpglobalresponse<Employeeresponsedto> updateEmployee(Long id, Employeerequestdto request) {
        Httpglobalresponse<Employeeresponsedto> response = new Httpglobalresponse<>();
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
 
        Employee employee = employeeFound.get();
        employee.setName(request.getName());
        employee.setPosition(position);
        employee.setHireDate(request.getHireDate());
        employee.setSalary(request.getSalary());
        employeeRepository.save(employee);
 
        Employeeresponsedto employeeDTO = new Employeeresponsedto();
        employeeDTO.setId(employee.getId());
        employeeDTO.setIdNumber(employee.getIdNumber());
        employeeDTO.setName(employee.getName());
        employeeDTO.setPosition(employee.getPosition());
        employeeDTO.setHireDate(employee.getHireDate());
        employeeDTO.setSalary(employee.getSalary());
 
        response.setMessage("Empleado actualizado exitosamente");
        response.setData(employeeDTO);
        return response;
    }

     /**
     *  Elimina un empleado por su identificador
     */
    public Httpglobalresponse<Employeeresponsedto> deleteEmployee(Long id) {
        Httpglobalresponse<Employeeresponsedto> response = new Httpglobalresponse<>();
        Optional<Employee> employeeFound = employeeRepository.findById(id);
 
        if (employeeFound.isEmpty()) {
            response.setMessage("Empleado no encontrado");
            return response;
        }
 
        employeeRepository.deleteById(id);
        response.setMessage("Empleado eliminado exitosamente");
        return response;
    }
 
    /**
     * Obtiene empleados filtrados por posición, Regla de negocio 2: filtrar por posición
     */
    public List<Employeeresponsedto> getEmployeesByPosition(String position) {
        List<Employeeresponsedto> employeeList = new ArrayList<>();
        List<Employee> employeesFound = employeeRepository.findByPosition(position.toUpperCase());
 
        for (Employee employee : employeesFound) {
            Employeeresponsedto employeeDTO = new Employeeresponsedto();
            employeeDTO.setId(employee.getId());
            employeeDTO.setIdNumber(employee.getIdNumber());
            employeeDTO.setName(employee.getName());
            employeeDTO.setPosition(employee.getPosition());
            employeeDTO.setHireDate(employee.getHireDate());
            employeeDTO.setSalary(employee.getSalary());
            employeeList.add(employeeDTO);
        }
 
        return employeeList;
    }
 
    /**
     * Obtiene empleados contratados dentro de un rango de fechas, Regla de negocio 2: filtrar por rango de fecha de contratación
     */
    public List<Employeeresponsedto> getEmployeesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Employeeresponsedto> employeeList = new ArrayList<>();
        List<Employee> employeesFound = employeeRepository.findByHireDateBetween(startDate, endDate);
 
        for (Employee employee : employeesFound) {
            Employeeresponsedto employeeDTO = new Employeeresponsedto();
            employeeDTO.setId(employee.getId());
            employeeDTO.setIdNumber(employee.getIdNumber());
            employeeDTO.setName(employee.getName());
            employeeDTO.setPosition(employee.getPosition());
            employeeDTO.setHireDate(employee.getHireDate());
            employeeDTO.setSalary(employee.getSalary());
            employeeList.add(employeeDTO);
        }
 
        return employeeList;
    }
}

