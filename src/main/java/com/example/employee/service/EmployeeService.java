package com.example.employee.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.DTO.EmployeeRequest;
import com.example.employee.DTO.EmployeeResponse;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentRepository departmentRepository;

    public final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Autowired

    public void save(EmployeeRequest employeeRequest){
        if(employeeRequest.salary().compareTo(BigDecimal.valueOf(1412.0)) < 0){
            return;
        }
        if(employeeRepository.existsByEmail(employeeRequest.email())){
            return;
        }
        if(employeeRequest.name().length() < 3){
            return;
        }

        Department department = departmentRepository.findById(employeeRequest.departmentId()).get();
        employeeRepository.save(employeeMapper.toEntity(employeeRequest, department));
    }

    public List<EmployeeResponse> findAll(){
        return employeeRepository.findAll().stream().map(employee ->{
            return new EmployeeResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.getSalary(), employee.getCpf(), employee.getDepartment().getName(), employee.getCreatedAt());
    }).toList();
    }

    public EmployeeResponse findById(Long id){
        Employee employee = employeeRepository.findById(id).get();
        return new EmployeeResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.getSalary(), employee.getCpf(), employee.getDepartment().getName(), employee.getCreatedAt());
    }
}


