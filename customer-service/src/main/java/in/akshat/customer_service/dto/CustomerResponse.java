// package: in.akshat.customer_service.dto

package in.akshat.customer_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import in.akshat.customer_service.enums.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    // Cross-service enrichment (Account/Branch MS)
    private String ifscCode;
    private String accountNo;

    private Long customerId;
    private String firstName;
    private String lastName;
    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String email;
    private String phoneNo;
    private String aadhaarNo;
    private String panNo;

    private KycStatus kycStatus;
    private Long branchId;
    private String address;
    private String city;
    private IndianState state;
    private String pincode;
    
  
    private AccountType accountType;
    
    // --- NEW: card results ---
    // When a debit card is issued synchronously by debit-card-service
    private CardInfoResponse debitCard;

    // When a credit application is created; may be PENDING/APPROVED/REJECTED.
    // If APPROVED (instant path) this object can contain card info as well.
    private CreditApplicationResponse creditApplication;
}
