package in.akshat.payment_service.service;

import in.akshat.payment_service.client.AccountClient;
import in.akshat.payment_service.dto.CreditRequest;
import in.akshat.payment_service.dto.DebitRequest;
import in.akshat.payment_service.dto.PaymentRequest;
import in.akshat.payment_service.dto.PaymentResponse;
import in.akshat.payment_service.entity.Payment;
import in.akshat.payment_service.enums.PaymentStatus;
import in.akshat.payment_service.exception.BadRequestException;
import in.akshat.payment_service.repository.PaymentRepository;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repo;
    private final AccountClient accountClient;

    public PaymentServiceImpl(PaymentRepository repo, AccountClient accountClient){
        this.repo = repo;
        this.accountClient = accountClient;
    }

    @Transactional
    public PaymentResponse createPayment(PaymentRequest req) {
        // idempotency
        Optional<Payment> existing = repo.findByClientRequestId(req.clientRequestId);
        if (existing.isPresent()) {
            Payment p = existing.get();
            return new PaymentResponse(p.getId(), p.getStatus().name(), "Idempotent replay");
        }

        // Business validations
        if (req.amountInPaise == null || req.amountInPaise <= 0) {
            throw new BadRequestException("amountInPaise must be greater than zero");
        }
        if (req.fromAccount.equals(req.toAccount)) {
            throw new BadRequestException("fromAccount and toAccount cannot be the same");
        }

        // optional: check accounts exist (account-service should expose endpoint)
        try {
            // If accountClient has an exists endpoint, uncomment and use:
             boolean fromExists = accountClient.checkAccountExist(req.fromAccount);
             boolean toExists   = accountClient.checkAccountExist(req.toAccount);
             if (!fromExists) throw new BadRequestException("fromAccount not found");
             if (!toExists) throw new BadRequestException("toAccount not found");
        } catch (Exception ex) {
            // If the account-service call fails, decide whether to fail fast or proceed.
            // For now, fail fast with clear error:
            throw new BadRequestException("Unable to verify accounts: " + ex.getMessage());
        }

        // persist initial payment
        Payment p = new Payment();
        p.setClientRequestId(req.clientRequestId);
        
        p.setFromAccount(req.fromAccount);
        p.setToAccount(req.toAccount);
        p.setAmountInPaise(req.amountInPaise);
        p.setCurrency(req.currency == null ? "INR" : req.currency);
        p.setStatus(PaymentStatus.INITIATED);
        p = repo.save(p);

        String txnId = "PAY-" + p.getId() + "-" + UUID.randomUUID().toString(); // don't confuse with client request id 

        try {
            accountClient.debit(new DebitRequest(req.fromAccount, req.amountInPaise, txnId));
            accountClient.credit(new CreditRequest(req.toAccount, req.amountInPaise, txnId));
            p.setStatus(PaymentStatus.SUCCESS);
            p.setExternalReference(txnId);
            repo.save(p);
            return new PaymentResponse(p.getId(), p.getStatus().name(), "Transfer successful");
        } catch (Exception ex) {
            p.setStatus(PaymentStatus.FAILED);
            p.setFailureReason(ex.getMessage());
            repo.save(p);
            return new PaymentResponse(p.getId(), p.getStatus().name(), "Transfer failed: " + ex.getMessage());
        }
    }
}
