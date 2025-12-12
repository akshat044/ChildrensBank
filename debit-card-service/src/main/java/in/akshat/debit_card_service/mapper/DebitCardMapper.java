package in.akshat.debit_card_service.mapper;

import org.springframework.stereotype.Component;

import in.akshat.debit_card_service.dto.CardInfoResponse;
import in.akshat.debit_card_service.dto.CardIssueRequest;
import in.akshat.debit_card_service.entity.DebitCard;

@Component
public class DebitCardMapper {
	
    public  DebitCard toEntity(CardIssueRequest req) {
    	DebitCard dc = new DebitCard();
    	
    		dc.setClientRequestId(req.getClientRequestId());
	        dc.setCustomerId(req.getCustomerId());
	        dc.setAccountNo(req.getAccountNo());
	        dc.setStatus("REQUESTED");
	        
	        return dc;
    }

    
//    public  void merge(DebitCard c, DebitCardUpdateDto d) { // for update
//         
//    }

  
    public CardInfoResponse toResp(DebitCard dc) {
        CardInfoResponse r = new CardInfoResponse();
        r.setCardId(dc.getCardId());
        r.setToken(dc.getToken());
        r.setLast4(dc.getLast4());
        r.setExpiryMonth(dc.getExpiryMonth());
        r.setExpiryYear(dc.getExpiryYear());
        r.setStatus(dc.getStatus());
        return r;
    }

}
