package in.akshat.customer_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.akshat.customer_service.dto.BranchResponse;

 

@FeignClient(name = "branch-service")  // must match spring.application.name in Branch Service
public interface BranchClient {

	 @GetMapping("/api/branches/{branchId}/exists")
	    boolean checkBranchExists(@PathVariable Long branchId);
    
	 	@GetMapping("/{id}")
	    public BranchResponse getBranchById(@PathVariable Long id);
	      
	    
}

