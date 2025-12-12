package in.akshat.branch_service.repository;

 

import org.springframework.data.jpa.repository.JpaRepository;

import in.akshat.branch_service.entity.Branch;

 

public interface BranchRepository extends JpaRepository<Branch,Long>{

	boolean existsByBranchNameAndIfscCode(String branchName, String ifscCode);

 
	 String findIfscCodeByBranchId(Long branchId);
 

}
