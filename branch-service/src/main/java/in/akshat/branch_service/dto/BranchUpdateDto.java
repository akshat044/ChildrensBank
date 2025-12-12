package in.akshat.branch_service.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BranchUpdateDto {

    // Allow nulls for partial updates; apply same patterns when present

    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$")
    private String ifscCode; // consider making immutable if business requires

    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z]+(?:[\\s.'-][A-Za-z]+)*$")
    private String branchName;

    @Size(min = 3, max = 80)
    @Pattern(regexp = "^[\\p{L}0-9#.,'\\-/\\s]+$")
    private String location;

    @DecimalMin("100000.00")
    @Digits(integer = 14, fraction = 2)
    private BigDecimal cashAvailable;

    @Min(1)
    private Integer workingEmployees;

    @Pattern(regexp = "^(?:\\+91[\\-\\s]?)?[6-9]\\d{9}$")
    private String branchContactNo;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z]+(?:[\\s.'-][A-Za-z]+)*$")
    private String branchCity;

    @Pattern(regexp = "^[1-9][0-9]{5}$")
    private String branchPincode;

    @Pattern(regexp = "^(ACTIVE|CLOSED|TEMPORARY|MAINTENANCE)$")
    private String branchStatus;

    // If you pass id in body, include it; but prefer path variable for update.
    private Long branchId;
}
