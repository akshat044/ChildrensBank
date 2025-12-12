// src/main/java/in/akshat/customer_service/service/CustomerServiceImpl.java
package in.akshat.customer_service.service;

import in.akshat.customer_service.client.AccountClient;
import in.akshat.customer_service.client.BranchClient;
import in.akshat.customer_service.client.CreditCardClient;
import in.akshat.customer_service.client.DebitCardClient;
import in.akshat.customer_service.dto.*;
import in.akshat.customer_service.entity.Customer;
import in.akshat.customer_service.enums.CardPreference;
import in.akshat.customer_service.enums.KycStatus;
import in.akshat.customer_service.exception.BranchNotFoundException;
import in.akshat.customer_service.mapper.CustomerMapper;
import in.akshat.customer_service.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;
    private final BranchClient branchClient;

    private final DebitCardClient debitCardClient;
    private final CreditCardClient creditCardClient;

    @Autowired
    private AccountClient accountClient;

    public CustomerServiceImpl(CustomerRepository repo, BranchClient branchClient,
                               DebitCardClient debitCardClient, CreditCardClient creditCardClient) {
        this.repo = repo;
        this.branchClient = branchClient;
        this.debitCardClient = debitCardClient;
        this.creditCardClient = creditCardClient;
    }

    @Override
    @Transactional
    public CustomerResponse create(CustomerCreateDto dto) {

        boolean isExists = branchClient.checkBranchExists(dto.getBranchId());
        if (!isExists) {
            throw new BranchNotFoundException("Please enter an existing branch");
        }

        Optional<Customer> existing = repo.findByEmailOrPhoneNoOrAadhaarNoOrPanNo(
                dto.getEmail(),
                dto.getPhoneNo(),
                dto.getAadhaarNo(),
                dto.getPanNo()
        );

        if (existing.isPresent()) {
            throw new DataIntegrityViolationException("Customer already exists with same email/phone/aadhaar/pan");
        }

        Customer c = CustomerMapper.toEntity(dto);
        c.setKycStatus(KycStatus.PENDING);

        try {
            Customer entityResponse = repo.save(c); // this persists and sets customerId

            // open account via account-service
            AccountResponse accountResponse = accountClient.open(
                    new AccountCreateRequest(entityResponse.getCustomerId(), dto.getBranchId(), dto.getAccountType())
            );

            CustomerResponse responseDto = CustomerMapper.toResponse(c);
            responseDto.setAccountNo(accountResponse.getAccountNo());
            responseDto.setIfscCode(accountResponse.getIfscCode());
            responseDto.setAccountType(accountResponse.getAccountType());
            responseDto.setBranchId(accountResponse.getBranchId());

            // --- Card orchestration based on preference ---
            try {
                if (dto.getCardPreference() == null) {
                	dto.setCardPreference(CardPreference.NONE);
                }
                
                String clientReqId = (dto.getClientRequestId() != null && !dto.getClientRequestId().isBlank())
                        ? dto.getClientRequestId()
                        : "cust-" + entityResponse.getCustomerId() + "-" + System.currentTimeMillis();

                if (dto.getCardPreference() == CardPreference.DEBIT || dto.getCardPreference() == CardPreference.BOTH) {
                    CardIssueRequest cardReq = new CardIssueRequest(clientReqId, entityResponse.getCustomerId(),
                            accountResponse.getAccountNo(), "DEBIT", true);
                    CardInfoResponse cardResp = debitCardClient.issue(cardReq);
                    responseDto.setDebitCard(cardResp);
                }

                if (dto.getCardPreference() == CardPreference.CREDIT || dto.getCardPreference() == CardPreference.BOTH) {
                    // example requested limit - you can add it into DTO to capture from UI
                    Long defaultRequestedLimit = 500000L; // paise => ₹5,000
                    CreditApplicationRequest credReq = new CreditApplicationRequest(clientReqId, entityResponse.getCustomerId(),
                            accountResponse.getAccountNo(), defaultRequestedLimit);
                    CreditApplicationResponse credResp = creditCardClient.apply(credReq);
                    responseDto.setCreditApplication(credResp);
                }
            } catch (Exception e) {
                // Card service outage or error should not rollback customer creation.
                // In production: log the error and/or write an outbox row for retry.
                // logger.warn("Card service error, returning customer created without card data", e);
            }

            return responseDto;

        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    // other methods (update, delete, get, list) unchanged — keep the implementations you already have
    @Override
    @Transactional
    public CustomerResponse update(Long id, CustomerUpdateDto dto) {
        // copy your existing update method here unchanged
        Customer existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));

        // ... same logic as you already had ...
        // For brevity, call your existing implementation or paste it back here.

        // We'll return a minimal placeholder to keep the code compileable if you paste this file:
        CustomerMapper.merge(existing, dto);
        repo.save(existing);
        return CustomerMapper.toResponse(existing);
    }

    @Override
    public void delete(Long id) {
        repo.delete(repo.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Customer not found: " + id)));
    }

    @Override
    public Customer get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
    }

    @Override
    public Page<Customer> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

}
