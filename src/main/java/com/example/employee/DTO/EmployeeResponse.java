package com.example.employee.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EmployeeResponse(
    Long id,
    String name,
    String email,
    BigDecimal salary,
    String cpf,
    String departmentName,
    LocalDateTime createdAt
) {}
