package in.akshat.branch_service.dto;
 

import java.math.BigDecimal;
import java.time.LocalDate;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BranchResponse {
    Long branchId;
    String ifscCode;
    String branchName;
    String location;
    BigDecimal cashAvailable;
    Integer workingEmployees;
    String branchContactNo;
    String branchCity;
    String branchPincode;
    String branchStatus;
}
