package in.akshat.customer_service.dto;

 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreditApplicationRequest {
 private String clientRequestId;
 private Long customerId;
 private String accountNo;
 private Long requestedLimitInPaise;
}
