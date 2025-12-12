package in.akshat.customer_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
 

import in.akshat.customer_service.dto.AccountCreateRequest;
import in.akshat.customer_service.dto.AccountResponse;


@FeignClient(name = "account-service") 
public interface AccountClient {
	
	 @PostMapping("/api/accounts")
	    public AccountResponse open(@RequestBody AccountCreateRequest req);
	     
	 @GetMapping("/api/accounts/{customerId}")
	 public ResponseEntity<List<AccountResponse>> getAllAccountsOfCustomers(@PathVariable Long customerId);
	 
	 
}
