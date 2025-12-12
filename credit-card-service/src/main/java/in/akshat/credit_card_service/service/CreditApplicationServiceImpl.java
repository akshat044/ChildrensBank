package in.akshat.credit_card_service.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import in.akshat.credit_card_service.dto.CreditApplicationRequest;
import in.akshat.credit_card_service.dto.CreditApplicationResponse;
import in.akshat.credit_card_service.entity.CreditApplication;
import in.akshat.credit_card_service.issuer.CreditMockIssuer;
import in.akshat.credit_card_service.repository.CreditApplicationRepository;
import in.akshat.credit_card_service.repository.CreditCardRepository;
import jakarta.transaction.Transactional;

@Service
public class CreditApplicationServiceImpl implements CreditApplicationService {
	
	private final CreditApplicationRepository repo;
    private final CreditCardRepository cardRepo;
    private final CreditMockIssuer issuer;

    public CreditApplicationServiceImpl(CreditApplicationRepository repo, CreditCardRepository cardRepo, CreditMockIssuer issuer) {
        this.repo = repo;
        this.cardRepo = cardRepo;
        this.issuer = issuer;
    }
	
    @Transactional
    public CreditApplicationResponse apply(CreditApplicationRequest req) {
        Optional<CreditApplication> ex = repo.findByClientRequestId(req.getClientRequestId());
        if (ex.isPresent()) {
            CreditApplication app = ex.get();
            return new CreditApplicationResponse(app.getApplicationId(), app.getStatus(), "duplicate");
        }
        CreditApplication app = new CreditApplication();
        app.setClientRequestId(req.getClientRequestId());
        app.setCustomerId(req.getCustomerId());
        app.setAccountNo(req.getAccountNo());
        app.setRequestedLimitInPaise(req.getRequestedLimitInPaise());
        app.setStatus("PENDING");
        app.setApplicationId("APP-" + UUID.randomUUID().toString());
        app = repo.save(app);

        // In production: publish event to queue or call underwriting service
        return new CreditApplicationResponse(app.getApplicationId(), app.getStatus(), "Application created");
    }

    // called by worker to process application (mock underwriting)
    @Transactional
    public void processApplication(String applicationId) {
        CreditApplication app = repo.findByApplicationId(applicationId).orElseThrow();
        // simple mock underwriting: approve if requested limit <= 10000 INR
        if (app.getRequestedLimitInPaise() <= 10000L * 100L) {
            app.setStatus("APPROVED");
            // issue card (call internal issuer) and create CreditCard entity
        } else {
            app.setStatus("REJECTED");
            app.setDecisionReason("Requested limit too high");
        }
        repo.save(app);
    }
    
    // *** add crdit creation code here *** 
    	
}
