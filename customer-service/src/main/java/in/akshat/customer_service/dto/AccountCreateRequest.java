package in.akshat.customer_service.dto;
 

import in.akshat.customer_service.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
 
import lombok.NoArgsConstructor;
 

 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {
    @NotNull private Long customerId;
    @NotNull private Long branchId;
    @NotNull private AccountType accountType;
}
