package in.akshat.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
 @NoArgsConstructor
 @AllArgsConstructor
 public class DebitRequest {
	
    public String accountNo;
    public Long amountInPaise;
    public String txnId;
    
}