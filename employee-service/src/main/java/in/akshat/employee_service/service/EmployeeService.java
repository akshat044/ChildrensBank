package in.akshat.employee_service.service;

import java.util.List;
import in.akshat.employee_service.entity.Employee;
import in.akshat.employee_service.dto.EmployeeUpdateDto;

public interface EmployeeService {
    
     Employee saveEmployee(Employee employee);
    
     Employee updateEmployee(Long employeeId, EmployeeUpdateDto empDto);
    
    
    
    List<Employee> getAllEmployees();
    
      Employee getEmployeeById(Long id);
      
    void deleteEmployeeById(Long id);
    
     boolean checkEmployeeExistsById(Long id);
     boolean checkEmailExists(String email);
}
