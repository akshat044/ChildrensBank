// src/main/java/in/akshat/customer_service/dto/CustomerCreateDto.java
package in.akshat.customer_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import in.akshat.customer_service.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateDto {

    private String firstName;

    @NotBlank
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$")
    private String lastName;

    @NotNull
    private Gender gender;

    @NotNull
    @Past
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank
    @Email
    @Size(max = 150)
    @Pattern(regexp = "^\\S+$")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?:\\+91[\\-\\s]?)?[6-9]\\d{9}$")
    private String phoneNo;

    @NotBlank
    @Pattern(regexp = "^[2-9][0-9]{11}$")
    private String aadhaarNo;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$")
    private String panNo;

    @NotNull
    @Positive
    private Long branchId;

    @NotBlank
    @Size(min = 5, max = 100)
    @Pattern(regexp = "^[\\p{L}0-9#.,'\\-/\\s]+$")
    private String address;

    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z]+(?:[\\s.'-][A-Za-z]+)*$")
    private String city;

    @NotNull
    private IndianState state;

    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{5}$")
    private String pincode;

    @NotNull
    private AccountType accountType;

    // NEW: user's choice for card preference (NONE, DEBIT, CREDIT, BOTH)
    @NotNull
    private CardPreference cardPreference = CardPreference.NONE;

    // NEW OPTIONAL: idempotency / correlation key sent by frontend (can be null)
    private String clientRequestId;

}
