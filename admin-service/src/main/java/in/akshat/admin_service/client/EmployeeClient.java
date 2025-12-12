package in.akshat.admin_service.client;

 

import in.akshat.admin_service.dto.EmployeeCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @PostMapping("/api/employees")
    EmployeeCreateDto createEmployee(@RequestBody EmployeeCreateDto employeeDto);
    
    @GetMapping("/api/employees/employee_Exist")
     boolean checkEmailExists(String email);
}
