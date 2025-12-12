package in.akshat.branch_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "branch-service")  // must match spring.application.name in Branch Service
public interface BranchClient {

	 @GetMapping("/api/branches/{branchId}/exists")
	    boolean checkBranchExists(@PathVariable Long branchId);
    
    
}

