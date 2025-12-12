	package in.akshat.account_service.client;
	
 
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	
	 
	
	import in.akshat.account_service.dto.BranchResponse;
	import org.springframework.cloud.openfeign.FeignClient;
	 
  @FeignClient(name = "branch-service")
  public interface BranchClient {
	
	    @GetMapping("/api/branches/{id}")
	    BranchResponse getBranchById(@PathVariable Long id);
  }
	
