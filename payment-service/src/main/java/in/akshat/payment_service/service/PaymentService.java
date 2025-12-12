package in.akshat.payment_service.service;

import in.akshat.payment_service.dto.PaymentRequest;
import in.akshat.payment_service.dto.PaymentResponse;

public interface PaymentService {
	
	public PaymentResponse createPayment(PaymentRequest req);
	
}
