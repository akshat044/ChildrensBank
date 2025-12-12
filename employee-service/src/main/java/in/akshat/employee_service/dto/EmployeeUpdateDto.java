package in.akshat.employee_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import in.akshat.employee_service.enums.*;

@Getter @Setter
public class EmployeeUpdateDto {

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$")
    private String firstName;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$")
    private String lastName;

    private Designation designation;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9&/()]+(?:[\\s.-][A-Za-z0-9&/()]+)*$")
    private String department;

    @Email @Size(max = 150) @Pattern(regexp = "^\\S+$")
    private String email;

    @Pattern(regexp = "^(?:\\+91[\\-\\s]?)?[6-9]\\d{9}$")
    private String phoneNo;

    private Gender gender;

    @PastOrPresent
    private LocalDate hireDate;

    @DecimalMin("5000.00") @Digits(integer = 12, fraction = 2)
    private BigDecimal salary;

    private EmploymentStatus employmentStatus;

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
