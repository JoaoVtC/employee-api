package com.example.employee.DTO;

import java.math.BigDecimal;

import com.example.employee.validation.ValidCpf;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public record EmployeeRequest(
    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 3)
    String name,

    @Email(message = "Use um domínio de email válido. ex: @gmail.com")
    String email,

    @DecimalMin(value = "1412.00", message = "O salário não pode ser menor que 1412")
    BigDecimal salary,

    @ValidCpf
    String cpf,
    
    Long departmentId
) {}
