package in.akshat.admin_service.client;

 

import in.akshat.admin_service.dto.BranchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "branch-service")
public interface BranchClient {

    @PostMapping("/api/branches")
    BranchDto createBranch(@RequestBody BranchDto branchDto);
    
 
}

