package in.akshat.admin_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import in.akshat.admin_service.enums.employee_enums.Designation;
import in.akshat.admin_service.enums.employee_enums.EmploymentStatus;
import in.akshat.admin_service.enums.employee_enums.Gender;
import in.akshat.admin_service.enums.employee_enums.IndianState;
 

@Getter @Setter
public class EmployeeCreateDto {

    @NotNull @Positive
    private Long employeeId; // business id (immutable after create)

    @NotBlank @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$")
    private String firstName;

    @NotBlank @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$")
    private String lastName;

    @NotNull private Designation designation;

    @NotBlank @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9&/()]+(?:[\\s.-][A-Za-z0-9&/()]+)*$")
    private String department;

    @NotBlank @Email @Size(max = 150) @Pattern(regexp = "^\\S+$")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?:\\+91[\\-\\s]?)?[6-9]\\d{9}$")
    private String phoneNo;

    @NotNull private Gender gender;

    @NotNull @PastOrPresent
    private LocalDate hireDate;

    @NotNull @DecimalMin("5000.00") @Digits(integer = 12, fraction = 2)
    private BigDecimal salary;

    @NotNull private EmploymentStatus employmentStatus;

    @NotNull @Positive
    private Long branchId;

    @NotBlank @Size(min = 5, max = 100)
    @Pattern(regexp = "^[\\p{L}0-9#.,'\\-/\\s]+$")
    private String address;

    @NotBlank @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z]+(?:[\\s.'-][A-Za-z]+)*$")
    private String city;

    @NotNull private IndianState state;

    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{5}$")
    private String pincode;
}
