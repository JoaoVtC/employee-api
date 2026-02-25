package com.example.employee.service;
import com.example.employee.handler.exception.EmployeeNotFoundException;
import com.example.employee.handler.exception.DuplicateEmailException;
import com.example.employee.handler.exception.EmployeeNotFoundException;


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

    public EmployeeResponse save(EmployeeRequest employeeRequest){

        if (employeeRepository.existsByEmail(employeeRequest.email())) {
            throw new DuplicateEmailException(employeeRequest.email());
        }

        Department department = departmentRepository.findById(employeeRequest.departmentId()).get();

        Employee employee = employeeRepository.save(employeeMapper.toEntity(employeeRequest, department));

        return new EmployeeResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.getSalary(), employee.getCpf(), employee.getDepartment().getName(), employee.getCreatedAt());
    }

    public List<EmployeeResponse> findAll(){
        return employeeRepository.findAll().stream().map(employee ->{
            return new EmployeeResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.getSalary(), employee.getCpf(), employee.getDepartment().getName(), employee.getCreatedAt());
    }).toList();
    }

    public EmployeeResponse findById(Long id){
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));
        return new EmployeeResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.getSalary(), employee.getCpf(), employee.getDepartment().getName(), employee.getCreatedAt());
    }

    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest){
        Employee employeeFinded = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));

        // Verifica se o novo email já existe em outro funcionário
        if (employeeRepository.existsByEmail(employeeRequest.email()) &&
            !employeeFinded.getEmail().equals(employeeRequest.email())) {
            throw new DuplicateEmailException(employeeRequest.email());
        }

        employeeFinded.setName(employeeRequest.name());
        employeeFinded.setEmail(employeeRequest.email());
        employeeFinded.setSalary(employeeRequest.salary());
        employeeFinded.setCpf(employeeRequest.cpf());

        Department department = departmentRepository.findById(employeeRequest.departmentId()).get();
        employeeFinded.setDepartment(department);

        Employee updatedEmployee = employeeRepository.save(employeeFinded);
        return new EmployeeResponse(
            updatedEmployee.getId(),
            updatedEmployee.getName(),
            updatedEmployee.getEmail(),
            updatedEmployee.getSalary(),
            updatedEmployee.getCpf(),
            updatedEmployee.getDepartment().getName(),
            updatedEmployee.getCreatedAt()
        );
    }

    public void deleteById(Long id){
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }
}


