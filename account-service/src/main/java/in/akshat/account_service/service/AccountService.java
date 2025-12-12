package in.akshat.account_service.service;
 

import java.util.List;

import in.akshat.account_service.dto.AccountCreateRequest;
import in.akshat.account_service.dto.AccountCreditRequest;
import in.akshat.account_service.dto.AccountDebitRequest;
import in.akshat.account_service.dto.AccountResponse;

public interface AccountService {

    AccountResponse openAccount(AccountCreateRequest request);
    
    List<AccountResponse> getAllAccountsOfCustomers(Long customerId);
    
 // new for payment integration
    void debit(AccountDebitRequest req);
    void credit(AccountCreditRequest req);
    boolean accountExist(String accountNo);
}
