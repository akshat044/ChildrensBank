package in.akshat.payment_service.client;
 

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import in.akshat.payment_service.dto.CreditRequest;
import in.akshat.payment_service.dto.DebitRequest;
 

@FeignClient(name = "account-service")
public interface AccountClient {

    
    @PostMapping("/api/accounts/debit")
    void debit(@RequestBody DebitRequest req);

    @PostMapping("/api/accounts/credit")
    void credit(@RequestBody CreditRequest req);
    
    @GetMapping("/api/accounts/check/{accountNo}")
    boolean checkAccountExist(@PathVariable String accountNo);
}
