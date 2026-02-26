package com.example.employee.controller;

import com.example.employee.DTO.EmployeeRequest;
import com.example.employee.DTO.EmployeeResponse;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller básico — retorna a Entity diretamente.
 * 
 * ⚠️ PROBLEMAS ATUAIS:
 * - Retorna Entity JPA diretamente (sem DTO)
 * - Sem validação de entrada
 * - Sem tratamento de erros centralizado
 * - Sem regras de negócio (salário mínimo, email único)
 * - Estrutura de pacotes simples (sem hexagonal)
 * 
 * ============================================
 * TODO 1: Criar EmployeeRequest e EmployeeResponse (DTOs) para entrada e saída da API
 *   - EmployeeRequest: name, email, salary, cpf, departmentId
 *   - EmployeeResponse: id, name, email, salary, cpf, departmentName, createdAt
 * 
 * TODO 2: Criar EmployeeMapper com métodos toEntity() e toResponse()
 *   - toEntity(EmployeeRequest, Department) → Employee
 *   - toResponse(Employee) → EmployeeResponse
 * 
 * TODO 3: Implementar EmployeeService com regras de negócio:
 *   - Salário não pode ser menor que 1412.00
 *   - Email deve ser único (verificar antes de salvar)
 *   - Nome deve ter pelo menos 3 caracteres
 * 
 * TODO 4: Criar GlobalExceptionHandler com @ControllerAdvice
 *   - Tratar MethodArgumentNotValidException (erros de @Valid)
 *   - Tratar EmployeeNotFoundException (404)
 *   - Tratar DuplicateEmailException (409)
 *   - Retornar respostas no formato Problem Details (RFC 7807)
 * 
 * TODO 5: Adicionar Bean Validation nos DTOs:
 *   - @NotBlank no nome, @Email no email, @Positive no salário
 * 
 * TODO 6: Criar custom validator @ValidCpf que valida formato e dígitos
 * 
 * TODO 7: Refatorar pacotes para estrutura hexagonal:
 *   - Mover regras de negócio para domain/
 *   - Controller e DTOs para adapter/in/web/
 *   - Repository e Entity JPA para adapter/out/persistence/
 * ============================================
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ❌ Retorna Entity diretamente — substituir por DTO (TODO 1, 2, 3)
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    // ❌ Retorna Entity diretamente — substituir por DTO (TODO 1, 2, 3)
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    // ❌ Recebe Entity como request — substituir por DTO com @Valid (TODO 1, 5)
    // ❌ Sem regras de negócio — mover para Service (TODO 3)
    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@RequestBody @Valid EmployeeRequest employee) {
        EmployeeResponse saved = employeeService.save(employee);
        return ResponseEntity.status(201).body(saved);
    }

    // ❌ Recebe Entity como request — substituir por DTO com @Valid
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id, @RequestBody @Valid EmployeeRequest employee) {
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
