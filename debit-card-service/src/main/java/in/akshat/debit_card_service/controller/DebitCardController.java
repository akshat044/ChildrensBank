package in.akshat.debit_card_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.akshat.debit_card_service.dto.CardInfoResponse;
import in.akshat.debit_card_service.dto.CardIssueRequest;
import in.akshat.debit_card_service.service.DebitCardService;

@RestController
@RequestMapping("/api/debit-card")
public class DebitCardController {
		
		@Autowired
		private DebitCardService debitService;
		
		@PostMapping("/issue")
	   public  CardInfoResponse issue(@RequestBody CardIssueRequest req) {
		return  debitService.issue(req);
	}
}
