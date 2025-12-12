package in.akshat.employee_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import in.akshat.employee_service.enums.Designation;
import in.akshat.employee_service.enums.EmploymentStatus;
import in.akshat.employee_service.enums.Gender;
import in.akshat.employee_service.enums.IndianState;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "employees",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_employees_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_employees_phone_no", columnNames = "phone_no")
    }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be a positive number")
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    // Names: 2–30 chars, letters with optional spaces/dot/hyphen/apostrophe between parts
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name must be 2–30 characters")
    @Pattern(
        regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$",
        message = "First name may contain only letters, spaces, dot, apostrophe or hyphen"
    )
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 30, message = "Last name must be 2–30 characters")
    @Pattern(
        regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$",
        message = "Last name may contain only letters, spaces, dot, apostrophe or hyphen"
    )
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Designation is required")
    @Column(nullable = false, length = 40)
    private Designation designation;

    @NotBlank(message = "Department is required")
    @Size(min = 2, max = 50, message = "Department must be 2–50 characters")
    @Pattern(
        regexp = "^[A-Za-z0-9&/()]+(?:[\\s.-][A-Za-z0-9&/()]+)*$",
        message = "Department may contain letters, numbers, spaces and & / ( ) . -"
    )
    @Column(nullable = false, length = 50)
    private String department;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must be at most 150 characters")
    @Pattern(regexp = "^\\S+$", message = "Email must not contain spaces")
    @Column(nullable = false, length = 150)
    private String email;

    // Indian mobile: optional +91, starts 6–9, total 10 digits
    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^(?:\\+91[\\-\\s]?)?[6-9]\\d{9}$",
        message = "Phone number must be Indian format (e.g., 98XXXXXXXX or +91 98XXXXXXXX)"
    )
    @Column(name = "phone_no", nullable = false, length = 16)
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender is required")
    @Column(nullable = false, length = 20)
    private Gender gender;

    @NotNull(message = "Hire date is required")
    @PastOrPresent(message = "Hire date cannot be in the future")
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    // Use BigDecimal for money (exact), precision/scale is valid now
    @NotNull(message = "Salary is required")
    @DecimalMin(value = "5000.00", message = "Salary must be ≥ 5000.00")
    @Digits(integer = 12, fraction = 2, message = "Salary can have up to 2 decimal places")
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Employment status is required")
    @Column(name = "employment_status", nullable = false, length = 20)
    private EmploymentStatus employmentStatus;

    @NotNull(message = "Branch ID is required")
    @Positive(message = "Branch ID must be a positive number")
    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 100, message = "Address must be 5–100 characters")
    @Pattern(
        regexp = "^[\\p{L}0-9#.,'\\-/\\s]+$",
        message = "Address contains invalid characters"
    )
    @Column(nullable = false, length = 100)
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be 2–50 characters")
    @Pattern(
        regexp = "^[A-Za-z]+(?:[\\s.'-][A-Za-z]+)*$",
        message = "City may contain only letters, spaces, dot, apostrophe or hyphen"
    )
    @Column(nullable = false, length = 50)
    private String city;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "State is required")
    @Column(nullable = false, length = 40)
    private IndianState state;

    // Indian PIN code: 6 digits, cannot start with 0
    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Pincode must be a valid 6-digit Indian PIN")
    @Column(nullable = false, length = 6)
    private String pincode;

    // --- Normalize text before save/update (optional but very handy) ---
    @PrePersist
    @PreUpdate
    private void normalize() {
        if (email != null) email = email.trim().toLowerCase();
        if (firstName != null) firstName = firstName.trim();
        if (lastName != null) lastName = lastName.trim();
        if (department != null) department = department.trim();
        if (phoneNo != null) phoneNo = phoneNo.trim();
        if (address != null) address = address.trim();
        if (city != null) city = city.trim();
        if (pincode != null) pincode = pincode.trim();
    }
}
