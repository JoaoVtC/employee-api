
package com.example.employee.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Import do validador
import com.example.employee.validation.CpfValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})  // Onde pode ser usada
@Retention(RetentionPolicy.RUNTIME)                   // Disponível em runtime
@Documented                                            // Aparece no Javadoc
@Constraint(validatedBy = CpfValidator.class)          // LIGA à classe validator
public @interface ValidCpf {
    String message() default "CPF inválido";           // Mensagem de erro padrão
    Class<?>[] groups() default {};                    // Grupos de validação
    Class<? extends Payload>[] payload() default {};   // Metadados (severidade)
}