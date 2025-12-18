package in.akshat.payment_service.controller;

import in.akshat.payment_service.dto.PaymentRequest;
import in.akshat.payment_service.dto.PaymentResponse;
import in.akshat.payment_service.service.PaymentService;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("/api/payments")
@Validated
public class PaymentController {
    private final PaymentService svc;
    public PaymentController(PaymentService svc){ this.svc = svc; }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody PaymentRequest req){
        PaymentResponse resp = svc.createPayment(req);
        return ResponseEntity.ok(resp);
    }
}
