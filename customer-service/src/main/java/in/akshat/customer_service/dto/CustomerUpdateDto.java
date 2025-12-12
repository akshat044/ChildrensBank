// package: in.akshat.customer_service.dto
// PATCH-style update: all fields optional.
// Aadhaar & PAN are intentionally omitted (treat as immutable).
// If you ever need to change them, create a dedicated, verified flow.

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
public class CustomerUpdateDto {

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$")
    private String firstName;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$")
    private String lastName;

    private Gender gender;

    @Past
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Email
    @Size(max = 150)
    @Pattern(regexp = "^\\S+$")
    private String email;

    @Pattern(regexp = "^(?:\\+91[\\-\\s]?)?[6-9]\\d{9}$")
    private String phoneNo;

    // Aadhaar & PAN intentionally not updatable here

    private KycStatus kycStatus;

    @Positive
    private Long branchId;

    @Size(min = 5, max = 100)
    @Pattern(regexp = "^[\\p{L}0-9#.,'\\-/\\s]+$")
    private String address;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z]+(?:[\\s.'-][A-Za-z]+)*$")
    private String city;

    private IndianState state;

    @Pattern(regexp = "^[1-9][0-9]{5}$")
    private String pincode;
}
