package in.akshat.customer_service.entity;

import in.akshat.customer_service.enums.Gender;
import in.akshat.customer_service.enums.IndianState;
import in.akshat.customer_service.enums.KycStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "customers",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_customers_email", columnNames = "email"),
           @UniqueConstraint(name = "uk_customers_phone_no", columnNames = "phone_no"),
           @UniqueConstraint(name = "uk_customers_aadhaar", columnNames = "aadhaar_no"),
           @UniqueConstraint(name = "uk_customers_pan", columnNames = "pan_no")
       })
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    
    
    // this is we gonna store into seprate account table 
    
  //  private String ifscCode; // now this ifsc code we are getting from branch and as well as account no 
	//private String accountNo;  // **** This after dto conversion 

    @NotBlank @Size(min=2, max=30)
    @Pattern(regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$")
    @Column(name = "first_name", nullable=false, length=30)
    private String firstName;

    @NotBlank @Size(min=2, max=30)
    @Pattern(regexp = "^[A-Za-z]+([\\s.'-][A-Za-z]+)*$")
    @Column(name = "last_name", nullable=false, length=30)
    private String lastName;

    @NotNull @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private Gender gender;

    @NotNull @Past
    @Column(name = "dob", nullable=false)
    private LocalDate dateOfBirth;

    @NotBlank @Email @Size(max=150) @Pattern(regexp="^\\S+$")
    @Column(nullable=false, length=150)
    private String email;

    @NotBlank
    @Pattern(regexp="^(?:\\+91[\\-\\s]?)?[6-9]\\d{9}$")
    @Column(name="phone_no", nullable=false, length=16)
    private String phoneNo;

    @NotBlank
    @Pattern(regexp="^[2-9][0-9]{11}$") // Aadhaar
    @Column(name="aadhaar_no", nullable=false, length=12)
    private String aadhaarNo;

    @NotBlank
    @Pattern(regexp="^[A-Z]{5}[0-9]{4}[A-Z]$") // PAN
    @Column(name="pan_no", nullable=false, length=10)
    private String panNo;

    @NotNull @Enumerated(EnumType.STRING)
    @Column(name="kyc_status", nullable=false, length=20)
    private KycStatus kycStatus;  // this after dto 

   
    @NotBlank @Size(min=5, max=100)
    @Pattern(regexp="^[\\p{L}0-9#.,'\\-/\\s]+$")
    @Column(nullable=false, length=100)
    private String address;

    @NotBlank @Size(min=2, max=50)
    @Pattern(regexp="^[A-Za-z]+(?:[\\s.'-][A-Za-z]+)*$")
    @Column(nullable=false, length=50)
    private String city;

    @NotNull @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=40)
    private IndianState state;

    @NotBlank
    @Pattern(regexp="^[1-9][0-9]{5}$")
    @Column(nullable=false, length=6)
    private String pincode;

    @PrePersist @PreUpdate
    private void normalize(){
        if (email != null) email = email.trim().toLowerCase();
        if (firstName != null) firstName = firstName.trim();
        if (lastName != null) lastName = lastName.trim();
        if (phoneNo != null) phoneNo = phoneNo.trim();
        if (address != null) address = address.trim();
        if (city != null) city = city.trim();
        if (pincode != null) pincode = pincode.trim();
        if (panNo != null) panNo = panNo.trim().toUpperCase();
    }
}
