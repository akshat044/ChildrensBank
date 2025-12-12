package in.akshat.payment_service.dto;

import jakarta.validation.constraints.*;

public class PaymentRequest {

    @NotBlank(message = "clientRequestId is required")
    public String clientRequestId;

    @NotBlank(message = "fromAccount is required")
    @Pattern(regexp = "^[A-Z0-9]{6,20}$", message = "fromAccount must be 6-20 chars, uppercase letters or digits")
    public String fromAccount;

    @NotBlank(message = "toAccount is required")
    @Pattern(regexp = "^[A-Z0-9]{6,20}$", message = "toAccount must be 6-20 chars, uppercase letters or digits")
    public String toAccount;

    @NotNull(message = "amountInPaise is required")
    @Min(value = 1, message = "amountInPaise must be at least 1")
    public Long amountInPaise;

//    // optional
//    public Long customerId;

    @Pattern(regexp = "^[A-Z]{3}$", message = "currency must be 3 uppercase letters like INR")
    public String currency = "INR";

    // default constructor for Jackson
    public PaymentRequest() {}

    // getters/setters if needed (or use public fields as above)
}
