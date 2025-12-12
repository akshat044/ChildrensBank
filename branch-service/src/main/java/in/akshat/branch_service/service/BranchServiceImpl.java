package in.akshat.branch_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.akshat.branch_service.dto.BranchResponse;
import in.akshat.branch_service.dto.BranchUpdateDto;
import in.akshat.branch_service.entity.Branch;
import in.akshat.branch_service.exception.BranchAlreadyExistsException;
import in.akshat.branch_service.exception.NoSuchBranchExistsException;
import in.akshat.branch_service.mapper.BranchMapper;
import in.akshat.branch_service.repository.BranchRepository;

 

@Service
public class BranchServiceImpl implements BranchService {
	
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public Branch createBranch(Branch branch) { // ***** Remember for original branch enity we are not passing id, but in dto yes we are passing
    	                                                               // branch id 
    
    	boolean  isPresent = branchRepository.existsByBranchNameAndIfscCode(branch.getBranchName(),branch.getIfscCode());
    	if((isPresent)) {
    		// throw exception 
    		throw new BranchAlreadyExistsException("please make a different branch");
    	}
    	return branchRepository.save(branch);
    }

    @Override
    public Branch updateBranch(Long branchId, BranchUpdateDto branchUpdateDto) {
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new  NoSuchBranchExistsException("Branch not found with ID: " + branchId));
        
       
  
        if (branchUpdateDto.getCashAvailable() != null) {
        	existingBranch.setCashAvailable(branchUpdateDto.getCashAvailable());
        }

        if (branchUpdateDto.getWorkingEmployees() != null) {
        	existingBranch.setWorkingEmployees(branchUpdateDto.getWorkingEmployees());
        }
 
        if (branchUpdateDto.getBranchContactNo() != null) {
        	existingBranch.setBranchContactNo(branchUpdateDto.getBranchContactNo());
        }

        if (branchUpdateDto.getBranchCity() != null) {
        	existingBranch.setBranchCity(branchUpdateDto.getBranchCity());
        }

        if (branchUpdateDto.getBranchPincode() != null) {
        	existingBranch.setBranchPincode(branchUpdateDto.getBranchPincode());;
        }

        if (branchUpdateDto.getBranchStatus() != null) {
        	existingBranch.setBranchStatus(branchUpdateDto.getBranchStatus());
        }

        return branchRepository.save(existingBranch);
    }

    @Override
    public void deleteBranch(Long branchId) {
    	if(!checkBranchExistsById(branchId)) {
    	throw new NoSuchBranchExistsException("Please enter branch to delte that exist ");
    	}
        branchRepository.deleteById(branchId);
    }

    @Override
    public BranchResponse getBranchById(Long branchId) {
         Branch a = branchRepository.findById(branchId).orElse(null);
         
         if(a == null) {
        	 throw new NoSuchBranchExistsException("Please enter branch to delte that exist ");
         }
        return  BranchMapper.toResponse(a);
         
    }

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
	public boolean checkBranchExistsById(Long branchId) {
		 Optional<Branch> b = branchRepository.findById(branchId);
		
		 if(b.isEmpty()) {
			 return false;
		 }
		 return true;
	}
 
 
}
