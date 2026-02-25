package com.example.employee.mapper;

import com.example.employee.DTO.EmployeeRequest;
import com.example.employee.DTO.EmployeeResponse;
import com.example.employee.model.Department;
import com.example.employee.model.Employee;

public class EmployeeMapper {
    public Employee toEntity(EmployeeRequest employeeRequest, Department department){
        Employee employee = new Employee();
        employee.setName(employeeRequest.name());
        employee.setCpf(employeeRequest.cpf());
        employee.setEmail(employeeRequest.email());
        employee.setSalary(employeeRequest.salary());
        employee.setDepartment(department);

        return employee;



    }

    public EmployeeResponse toResponse(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.getSalary(), employee.getCpf(), employee.getDepartment().getName(), employee.getCreatedAt());

        return employeeResponse;
    }
}
