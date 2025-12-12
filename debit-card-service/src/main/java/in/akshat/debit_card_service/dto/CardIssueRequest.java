package in.akshat.debit_card_service.dto;

 

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CardIssueRequest {
 private String clientRequestId;
 private Long customerId;
 private String accountNo;
 private String cardType;
 private boolean virtualOnly = true;
}

