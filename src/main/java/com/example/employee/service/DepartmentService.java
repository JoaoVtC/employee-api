package com.example.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.model.Department;
import com.example.employee.repository.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    public DepartmentRepository departmentRepository;

    public Department findById(Long id){
        return departmentRepository.findById(id).get();
    }
    
}
