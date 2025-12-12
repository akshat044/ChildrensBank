package in.akshat.branch_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "branches",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_branch_ifsc", columnNames = "ifsc_code"),
        @UniqueConstraint(name = "uk_branch_pincode", columnNames = "branch_pincode") // <-- updated
    }
)
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    // IFSC: 4 letters bank code + 0 + 6 digits (e.g. SBIN0123456)
    @NotBlank(message = "IFSC code is required")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC format (Eg: SBIN0XXXXXX)")
    @Column(name = "ifsc_code", nullable = false, unique = true, length = 11)
    private String ifscCode;

    @NotBlank(message = "Branch name is required")
    @Size(min = 2, max = 50, message = "Branch name must be between 2 to 50 characters")
    @Pattern(regexp = "^[A-Za-z]+(?:[\\s.'-][A-Za-z]+)*$", message = "Branch name may contain only letters and basic punctuation and it cannot be empty")
    private String branchName;

    @NotBlank(message = "Location is required")
    @Size(min = 3, max = 80, message = "Location must be 3–80 characters")
    @Pattern(regexp = "^[\\p{L}0-9#.,'\\-/\\s]+$", message = "Location contains invalid characters")
    private String location;

    // Use BigDecimal for money
    @NotNull(message = "Cash available is required")
    @DecimalMin(value = "100000.00", message = "Branch must open with at least ₹1,00,000 capital amount")
    @Digits(integer = 14, fraction = 2, message = "Cash can have up to 2 decimals")
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal cashAvailable;

    @NotNull(message = "Working employees count is required")
    @Min(value = 1, message = "Working employees must be at least 1")
    private Integer workingEmployees;

    // Indian phone pattern
    @NotBlank(message = "Branch contact no is required")
    @Pattern(
        regexp = "^(?:\\+91[\\-\\s]?)?[6-9]\\d{9}$",
        message = "Branch contact number must be Indian format (e.g. 98XXXXXXXX or +91 98XXXXXXXX)"
    )
    private String branchContactNo;

    @NotBlank(message = "Branch city is required")
    @Size(min = 2, max = 50, message = "City must be 2–50 characters")
    @Pattern(regexp = "^[A-Za-z]+(?:[\\s.'-][A-Za-z]+)*$", message = "City may contain only letters and basic punctuation")
    private String branchCity;

    @NotBlank(message = "Branch pincode is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Branch pincode must be valid 6-digit Indian PIN")
    @Column(name = "branch_pincode", nullable = false, length = 6)
    private String branchPincode;

    // Consider enum later: ACTIVE, CLOSED, TEMPORARY, MAINTENANCE
    @NotBlank(message = "Branch status is required")
    @Pattern(regexp = "^(ACTIVE|CLOSED|TEMPORARY|MAINTENANCE)$", message = "Branch status must be ACTIVE / CLOSED / TEMPORARY / MAINTENANCE")
    private String branchStatus;
}
