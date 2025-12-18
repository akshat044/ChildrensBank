package in.akshat.admin_service.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import in.akshat.admin_service.dto.BranchDto;
import in.akshat.admin_service.dto.EmployeeCreateDto;
import in.akshat.admin_service.service.AdminService;

import jakarta.validation.Valid;

@RefreshScope
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
  
 
    
    @PostMapping("/branches")
    public ResponseEntity<BranchDto> createBranch(@Valid @RequestBody BranchDto branchDto) {
        return ResponseEntity.ok(adminService.createBranch(branchDto));
    }

     
    @PostMapping("/employees")
    public ResponseEntity<EmployeeCreateDto> createEmployee(@Valid @RequestBody EmployeeCreateDto employeeDto) {
        return ResponseEntity.ok(adminService.createEmployee(employeeDto));
    }
}
