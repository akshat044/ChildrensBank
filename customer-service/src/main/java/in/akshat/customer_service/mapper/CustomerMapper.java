package in.akshat.customer_service.mapper;

import in.akshat.customer_service.dto.CustomerCreateDto;
import in.akshat.customer_service.dto.CustomerResponse;
import in.akshat.customer_service.dto.CustomerUpdateDto;
import in.akshat.customer_service.entity.Customer;
import in.akshat.customer_service.enums.AccountType;
import in.akshat.customer_service.enums.KycStatus;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static Customer toEntity(CustomerCreateDto d) {
        Customer c = new Customer();
        c.setFirstName(d.getFirstName());
        c.setLastName(d.getLastName());
        c.setGender(d.getGender());
        c.setDateOfBirth(d.getDateOfBirth());
        c.setEmail(d.getEmail());
        c.setPhoneNo(d.getPhoneNo());
        c.setAadhaarNo(d.getAadhaarNo());
        c.setPanNo(d.getPanNo());
       // c.setKycStatus(KycStatus.PENDING); // default on create
       // c.setBranchId(d.getBranchId()); this we are doing as entity has no branch id , but acccount has
        c.setAddress(d.getAddress());
        c.setCity(d.getCity());
        c.setState(d.getState());
        c.setPincode(d.getPincode());
        return c;
    }

    /**
     * PATCH-style merge: only updates non-null fields.
     * Aadhaar & PAN are intentionally NOT updatable here.
     */
    public static void merge(Customer c, CustomerUpdateDto d) {
        if (d.getFirstName() != null)  c.setFirstName(d.getFirstName());
        if (d.getLastName() != null)   c.setLastName(d.getLastName());
        if (d.getGender() != null)     c.setGender(d.getGender());
        if (d.getDateOfBirth() != null) c.setDateOfBirth(d.getDateOfBirth());

        if (d.getEmail() != null)      c.setEmail(d.getEmail());
        if (d.getPhoneNo() != null)    c.setPhoneNo(d.getPhoneNo());
        // aadhaarNo and panNo intentionally immutable in this flow

  //      if (d.getKycStatus() != null)  c.setKycStatus(d.getKycStatus());
  //      if (d.getBranchId() != null)   c.setBranchId(d.getBranchId());
        if (d.getAddress() != null)    c.setAddress(d.getAddress());
        if (d.getCity() != null)       c.setCity(d.getCity());
        if (d.getState() != null)      c.setState(d.getState());
        if (d.getPincode() != null)    c.setPincode(d.getPincode());
    }

    /** Basic response without cross-service enrichment. */
    public static CustomerResponse toResponse(Customer c) {
        return CustomerResponse.builder()
                .ifscCode(null)
                .accountNo(null)
                .customerId(c.getCustomerId())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .gender(c.getGender())
                .dateOfBirth(c.getDateOfBirth())
                .email(c.getEmail())
                .phoneNo(c.getPhoneNo())
                .aadhaarNo(c.getAadhaarNo())
                .panNo(c.getPanNo())
                .kycStatus(c.getKycStatus())
             //   .branchId(c.getBranchId())
                .address(c.getAddress())
                .city(c.getCity())
                .state(c.getState())
                .pincode(c.getPincode())
               
                .build();
    }

    /** Response with IFSC & Account enrichment (from Branch/Account services). */
    public static CustomerResponse toResponse(Customer c, String ifscCode, String accountNo, AccountType accountType) {
        return CustomerResponse.builder()
                .ifscCode(ifscCode)
                .accountNo(accountNo)
                .accountType(accountType)
                .customerId(c.getCustomerId())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .gender(c.getGender())
                .dateOfBirth(c.getDateOfBirth())
                .email(c.getEmail())
                .phoneNo(c.getPhoneNo())
                .aadhaarNo(c.getAadhaarNo())
                .panNo(c.getPanNo())
                .kycStatus(c.getKycStatus())
              //  .branchId(c.getBranchId())
                .address(c.getAddress())
                .city(c.getCity())
                .state(c.getState())
                .pincode(c.getPincode())
                .build();
    }
}
