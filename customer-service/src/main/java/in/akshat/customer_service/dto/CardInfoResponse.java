package in.akshat.customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CardInfoResponse {
    private String cardId;
    private String token;
    private String last4;
    private Integer expiryMonth;
    private Integer expiryYear;
    private String status;
}