package in.akshat.customer_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
 

import in.akshat.customer_service.dto.CreditApplicationRequest;
import in.akshat.customer_service.dto.CreditApplicationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "credit-card-service" )
public interface CreditCardClient {
	
    @PostMapping("/api/credit-card/issue")
    CreditApplicationResponse apply(@RequestBody CreditApplicationRequest req);
    // also for actual credit card creation 
    // create end point and map to actual credit card creation 
}
