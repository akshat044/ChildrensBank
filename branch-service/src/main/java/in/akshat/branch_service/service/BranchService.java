package in.akshat.branch_service.service;

import java.util.List;
import java.util.Optional;

import in.akshat.branch_service.dto.BranchCreateDto;
import in.akshat.branch_service.dto.BranchResponse;
import in.akshat.branch_service.dto.BranchUpdateDto;
import in.akshat.branch_service.entity.Branch;

 

 

public interface BranchService {

    // CRUD operations
    Branch createBranch(Branch branch);

    Branch updateBranch(Long branchId, BranchUpdateDto branch);

    void deleteBranch(Long branchId);

    BranchResponse getBranchById(Long branchId);

    List<Branch> getAllBranches();

 
     
    
   
    
    boolean checkBranchExistsById(Long branchId); // this is for microservices
 
   
   
}
