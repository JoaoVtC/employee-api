package com.example.employee.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<ValidCpf, String> {

    @Override
    public void initialize(ValidCpf annotation) {
        // Pode ler parâmetros da anotação aqui (se houver)
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isBlank()) return false;

        // Remove pontuação (aceita "123.456.789-09" e "12345678909")
        String digits = cpf.replaceAll("\\D", "");

        // Deve ter exatamente 11 dígitos
        if (digits.length() != 11) return false;

        // Não pode ser todos iguais (111.111.111-11, 222.222.222-22, etc.)
        if (digits.chars().distinct().count() == 1) return false;

        // Cálculo do PRIMEIRO dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(digits.charAt(i)) * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) firstDigit = 0;
        if (Character.getNumericValue(digits.charAt(9)) != firstDigit) return false;

        // Cálculo do SEGUNDO dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(digits.charAt(i)) * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) secondDigit = 0;

        return Character.getNumericValue(digits.charAt(10)) == secondDigit;
    }
}