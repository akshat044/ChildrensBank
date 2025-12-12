package in.akshat.credit_card_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreditApplicationResponse {
    private String applicationId;
    private String status;
    private String message;
}
