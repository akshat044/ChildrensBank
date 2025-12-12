 package in.akshat.admin_service.service;

 

import in.akshat.admin_service.dto.BranchDto;
import in.akshat.admin_service.dto.EmployeeCreateDto;

public interface AdminService {

    BranchDto createBranch(BranchDto branchDto);

    EmployeeCreateDto createEmployee(EmployeeCreateDto employeeDto);
    
   
}
