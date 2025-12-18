package in.akshat.employee_service.controller;

import jakarta.validation.Valid;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.akshat.employee_service.dto.EmployeeCreateDto;
import in.akshat.employee_service.dto.EmployeeResponseDto;
import in.akshat.employee_service.dto.EmployeeUpdateDto;
import in.akshat.employee_service.entity.Employee;
import in.akshat.employee_service.mapper.EmployeeMapper;
import in.akshat.employee_service.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RefreshScope
@RestController
@RequestMapping("/api/employees") 
public class EmployeeController {

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) { 
    	this.employeeService = employeeService; 
    	}

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeCreateDto dto) {
        Employee toSave = EmployeeMapper.toEntity(dto);               // DTO -> Entity
        Employee saved  = employeeService.saveEmployee(toSave);       // Service
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EmployeeMapper.toResponse(saved));              // Entity -> Response
    }

    // We update by DB PK {id}. employeeId is immutable and not in UpdateDto.
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateDto dto) {
        Employee updated = employeeService.updateEmployee(id, dto);
        return ResponseEntity.ok(EmployeeMapper.toResponse(updated));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAll() {
        List<EmployeeResponseDto> list = employeeService.getAllEmployees()
                .stream().map(EmployeeMapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getById(@PathVariable Long id) {
        Employee emp = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(EmployeeMapper.toResponse(emp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee deleted successfully!");
    }

    @GetMapping("/status")
    public String status() { return "Employee Service is up and registered with Eureka!"; }
}
