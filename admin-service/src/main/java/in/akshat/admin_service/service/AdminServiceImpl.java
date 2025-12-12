package in.akshat.admin_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.akshat.admin_service.client.BranchClient;
import in.akshat.admin_service.client.EmployeeClient;
import in.akshat.admin_service.dto.BranchDto;
import in.akshat.admin_service.dto.EmployeeCreateDto;
 

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BranchClient branchClient;

    @Autowired
    private EmployeeClient employeeClient;
    
    // need to check first whether ifsc code ,branch name , branch manager is already not exist for that
    @Override
    public BranchDto createBranch(BranchDto branchDto) {  
        return branchClient.createBranch(branchDto);  // here exception can be propogated so handle it through global exception 
    }

    @Override
    public EmployeeCreateDto createEmployee(EmployeeCreateDto employeeDto) {
    	return  employeeClient.createEmployee(employeeDto); // note validation is being done over the employee microservices 
    	 
    }

	 

	 

	 

	 

	 
}
