package in.akshat.credit_card_service.service;

import in.akshat.credit_card_service.dto.CreditApplicationRequest;
import in.akshat.credit_card_service.dto.CreditApplicationResponse;

public interface CreditApplicationService {
	 public CreditApplicationResponse apply(CreditApplicationRequest req);
	 public void processApplication(String applicationId);
}
