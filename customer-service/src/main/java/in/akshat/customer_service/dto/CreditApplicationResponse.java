package in.akshat.customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreditApplicationResponse {
    private String applicationId;
    private String status; // PENDING / APPROVED / REJECTED
    private String message;
}