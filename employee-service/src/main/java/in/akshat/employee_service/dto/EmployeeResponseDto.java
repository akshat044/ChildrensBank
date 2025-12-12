package in.akshat.employee_service.dto;

 

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import in.akshat.employee_service.enums.*;

@Getter @Setter @Builder
public class EmployeeResponseDto {
	
	private Long id;
    private Long employeeId;
    private String firstName;
    private String lastName;
    private Designation designation;
    private String department;
    private String email;
    private String phoneNo;
    private Gender gender;
    private LocalDate hireDate;
    private BigDecimal salary;
    private EmploymentStatus employmentStatus;
    private Long branchId;
    private String address;
    private String city;
    private IndianState state;
    private String pincode;
}
