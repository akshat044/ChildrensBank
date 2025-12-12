package in.akshat.account_service.dto;

 

import in.akshat.account_service.enums.AccountType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class AccountResponse {
    private Long accountId;
    private String accountNo;
    private Long customerId;
    private Long branchId;
    private String ifscCode;

    private AccountType accountType;
}
