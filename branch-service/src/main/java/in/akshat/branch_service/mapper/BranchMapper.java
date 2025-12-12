package in.akshat.branch_service.mapper;

import in.akshat.branch_service.dto.BranchCreateDto;
import in.akshat.branch_service.dto.BranchResponse;
import in.akshat.branch_service.dto.BranchUpdateDto;
 
import in.akshat.branch_service.entity.Branch;

public class BranchMapper {

    // Create Entity
    public static Branch toEntity(BranchCreateDto d) {
        Branch b = new Branch();
        
        b.setIfscCode(d.getIfscCode());
        b.setBranchName(d.getBranchName());
        b.setLocation(d.getLocation());
        b.setCashAvailable(d.getCashAvailable());
        b.setWorkingEmployees(d.getWorkingEmployees());
        b.setBranchContactNo(d.getBranchContactNo());
        b.setBranchCity(d.getBranchCity());
        b.setBranchPincode(d.getBranchPincode());
        b.setBranchStatus(d.getBranchStatus());
        return b;
    }
    public static Branch toUpdatedEntity(BranchUpdateDto d) {
        Branch b = new Branch();
        
        b.setIfscCode(d.getIfscCode());
        b.setBranchName(d.getBranchName());
        b.setLocation(d.getLocation());
        b.setCashAvailable(d.getCashAvailable());
        b.setWorkingEmployees(d.getWorkingEmployees());
        b.setBranchContactNo(d.getBranchContactNo());
        b.setBranchCity(d.getBranchCity());
        b.setBranchPincode(d.getBranchPincode());
        b.setBranchStatus(d.getBranchStatus());
        return b;
    }

    // Partial update (PATCH/PUT) → merge non-null fields into existing entity
    public static void merge(Branch b, BranchUpdateDto d) {
        // Typically IFSC is immutable; enable only if your business allows it.
        if (d.getIfscCode() != null) b.setIfscCode(d.getIfscCode());

        if (d.getBranchName() != null) b.setBranchName(d.getBranchName());
        if (d.getLocation() != null) b.setLocation(d.getLocation());
        if (d.getCashAvailable() != null) b.setCashAvailable(d.getCashAvailable());
        if (d.getWorkingEmployees() != null) b.setWorkingEmployees(d.getWorkingEmployees());
        if (d.getBranchContactNo() != null) b.setBranchContactNo(d.getBranchContactNo());
        if (d.getBranchCity() != null) b.setBranchCity(d.getBranchCity());
        if (d.getBranchPincode() != null) b.setBranchPincode(d.getBranchPincode());
        if (d.getBranchStatus() != null) b.setBranchStatus(d.getBranchStatus());
    }

    // Entity → Response
    public static BranchResponse toResponse(Branch b) {
        return BranchResponse.builder()
                .branchId(b.getBranchId())
                .ifscCode(b.getIfscCode())
                .branchName(b.getBranchName())
                .location(b.getLocation())
                .cashAvailable(b.getCashAvailable())
                .workingEmployees(b.getWorkingEmployees())
                .branchContactNo(b.getBranchContactNo())
                .branchCity(b.getBranchCity())
                .branchPincode(b.getBranchPincode())
                .branchStatus(b.getBranchStatus())
                .build();
    }
}
