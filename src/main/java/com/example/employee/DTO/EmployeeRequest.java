package com.example.employee.DTO;

import java.math.BigDecimal;

import com.example.employee.validation.ValidCpf;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record EmployeeRequest(
    @NotBlank(message = "O nome não pode ser vazio")
    String name,

    @Email(message = "Use um domínio de email válido. ex: @gmail.com")
    String email,

    @Positive(message = "Não deixe seu funcionário passando fome")
    BigDecimal salary,

    @ValidCpf
    String cpf,
    
    Long departmentId
) {}
