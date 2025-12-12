package in.akshat.customer_service.client;


import in.akshat.customer_service.dto.CardInfoResponse;
import in.akshat.customer_service.dto.CardIssueRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "debit-card-service")
public interface DebitCardClient {
    @PostMapping("/api/debit-card/issue")
    CardInfoResponse issue(@RequestBody CardIssueRequest req);
}