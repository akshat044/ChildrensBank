package in.akshat.debit_card_service.service;

import in.akshat.debit_card_service.dto.CardInfoResponse;
import in.akshat.debit_card_service.dto.CardIssueRequest;
import in.akshat.debit_card_service.entity.DebitCard;

public interface DebitCardService {
	 public CardInfoResponse issue(CardIssueRequest req);
	 
}
