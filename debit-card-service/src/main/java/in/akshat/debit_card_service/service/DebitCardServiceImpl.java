package in.akshat.debit_card_service.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.akshat.debit_card_service.dto.CardInfoResponse;
import in.akshat.debit_card_service.dto.CardIssueRequest;
import in.akshat.debit_card_service.entity.DebitCard;
import in.akshat.debit_card_service.issuer.DebitMockIssuer;
import in.akshat.debit_card_service.mapper.DebitCardMapper;
import in.akshat.debit_card_service.repository.DebitCardRepository;
import jakarta.transaction.Transactional;

 
@Service
public class DebitCardServiceImpl implements DebitCardService {
	
	   private final DebitCardRepository repo;
	    private final DebitMockIssuer issuer;
	    
	    @Autowired
	    private DebitCardMapper mapper;

	    public DebitCardServiceImpl(DebitCardRepository repo, DebitMockIssuer issuer) {
	        this.repo = repo;
	        this.issuer = issuer;
	    }
	    @Transactional
	    public CardInfoResponse issue(CardIssueRequest req) {
	        Optional<DebitCard> ex = repo.findByClientRequestId(req.getClientRequestId());
	        if (ex.isPresent()) return mapper.toResp(ex.get());

	        
	      DebitCard dc =   mapper.toEntity(req);
	          repo.save(dc);

	        DebitMockIssuer.IssuerResponse ir = issuer.issue(req.getClientRequestId()); // VISA , MASTER CARD, RUPAY

	        dc.setToken(ir.token());
	        dc.setLast4(ir.last4());
	        dc.setExpiryMonth(ir.expMonth());
	        dc.setExpiryYear(ir.expYear());
	        dc.setStatus(ir.status());
	        dc.setSourceRef(ir.issuerRef());
	        dc.setCardId("debit-" + UUID.randomUUID());
	        repo.save(dc);

	        return mapper.toResp(dc);
	    }

	    

}
