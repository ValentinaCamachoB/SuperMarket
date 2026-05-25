package com.example.SuperMarket.Servicie;

import java.time.LocalDate;
 
import lombok.Data;
 
@Data
public class Employeeresponsedto {
    private Long id;
    private String idNumber;
    private String name;
    private String position;
    private LocalDate hireDate;
    private Double salary;
}
