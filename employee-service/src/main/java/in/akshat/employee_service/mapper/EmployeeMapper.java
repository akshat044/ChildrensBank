package in.akshat.employee_service.mapper;

import in.akshat.employee_service.dto.EmployeeCreateDto;
import in.akshat.employee_service.dto.EmployeeUpdateDto;
import in.akshat.employee_service.dto.EmployeeResponseDto;
import in.akshat.employee_service.entity.Employee;

public class EmployeeMapper {

    // Create → Entity
    public static Employee toEntity(EmployeeCreateDto d) {
        Employee e = new Employee();
        // DB PK 'Id' is generated
        e.setEmployeeId(d.getEmployeeId()); // set once at creation

        e.setFirstName(d.getFirstName());
        e.setLastName(d.getLastName());
        e.setDesignation(d.getDesignation());
        e.setDepartment(d.getDepartment());
        e.setEmail(d.getEmail());
        e.setPhoneNo(d.getPhoneNo());
        e.setGender(d.getGender());
        e.setHireDate(d.getHireDate());
        e.setSalary(d.getSalary());
        e.setEmploymentStatus(d.getEmploymentStatus());
        e.setBranchId(d.getBranchId());
        e.setAddress(d.getAddress());
        e.setCity(d.getCity());
        e.setState(d.getState());
        e.setPincode(d.getPincode());
        return e;
    }

    // Update DTO → NEW Entity (avoid changing employeeId)
    public static Employee toUpdatedEntity(EmployeeUpdateDto d) {
        Employee e = new Employee();
        // e.setId(...); // only if you manually manage DB PK
        // DO NOT set employeeId here — keep existing one from DB

        e.setFirstName(d.getFirstName());
        e.setLastName(d.getLastName());
        e.setDesignation(d.getDesignation());
        e.setDepartment(d.getDepartment());
        e.setEmail(d.getEmail());
        e.setPhoneNo(d.getPhoneNo());
        e.setGender(d.getGender());
        e.setHireDate(d.getHireDate());
        e.setSalary(d.getSalary());
        e.setEmploymentStatus(d.getEmploymentStatus());
        e.setBranchId(d.getBranchId());
        e.setAddress(d.getAddress());
        e.setCity(d.getCity());
        e.setState(d.getState());
        e.setPincode(d.getPincode());
        return e;
    }

    // Partial merge (PATCH/PUT) → never touch employeeId
    public static void merge(Employee e, EmployeeUpdateDto d) {
        // employeeId is intentionally immutable
        if (d.getFirstName() != null) e.setFirstName(d.getFirstName());
        if (d.getLastName() != null) e.setLastName(d.getLastName());
        if (d.getDesignation() != null) e.setDesignation(d.getDesignation());
        if (d.getDepartment() != null) e.setDepartment(d.getDepartment());
        if (d.getEmail() != null) e.setEmail(d.getEmail());
        if (d.getPhoneNo() != null) e.setPhoneNo(d.getPhoneNo());
        if (d.getGender() != null) e.setGender(d.getGender());
        if (d.getHireDate() != null) e.setHireDate(d.getHireDate());
        if (d.getSalary() != null) e.setSalary(d.getSalary());
        if (d.getEmploymentStatus() != null) e.setEmploymentStatus(d.getEmploymentStatus());
        if (d.getBranchId() != null) e.setBranchId(d.getBranchId());
        if (d.getAddress() != null) e.setAddress(d.getAddress());
        if (d.getCity() != null) e.setCity(d.getCity());
        if (d.getState() != null) e.setState(d.getState());
        if (d.getPincode() != null) e.setPincode(d.getPincode());
    }

    // Entity → Response DTO
    public static EmployeeResponseDto toResponse(Employee e) {
        return EmployeeResponseDto.builder()
                .id(e.getId())                 // DB PK
                .employeeId(e.getEmployeeId()) // business id (read-only)
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .designation(e.getDesignation())
                .department(e.getDepartment())
                .email(e.getEmail())
                .phoneNo(e.getPhoneNo())
                .gender(e.getGender())
                .hireDate(e.getHireDate())
                .salary(e.getSalary())
                .employmentStatus(e.getEmploymentStatus())
                .branchId(e.getBranchId())
                .address(e.getAddress())
                .city(e.getCity())
                .state(e.getState())
                .pincode(e.getPincode())
                .build();
    }
}
