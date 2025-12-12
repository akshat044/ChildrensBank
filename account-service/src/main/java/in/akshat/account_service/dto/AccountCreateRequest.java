package in.akshat.account_service.dto;
 

import in.akshat.account_service.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountCreateRequest {
    @NotNull private Long customerId;
    @NotNull private Long branchId;
   
    @NotNull
    private AccountType accountType;
}
