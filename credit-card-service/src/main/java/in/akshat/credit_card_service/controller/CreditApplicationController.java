package in.akshat.credit_card_service.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.akshat.credit_card_service.dto.CreditApplicationRequest;
import in.akshat.credit_card_service.dto.CreditApplicationResponse;
import in.akshat.credit_card_service.service.CreditApplicationService;

@RefreshScope
@RestController
@RequestMapping("/api/credit-card")
public class CreditApplicationController {
    private final CreditApplicationService svc;
    public CreditApplicationController(CreditApplicationService svc){ 
    	this.svc = svc; 
    	}

    @PostMapping("/issue")
    public ResponseEntity<CreditApplicationResponse> apply(@RequestBody CreditApplicationRequest req){
        return ResponseEntity.accepted().body(svc.apply(req));
    }
}