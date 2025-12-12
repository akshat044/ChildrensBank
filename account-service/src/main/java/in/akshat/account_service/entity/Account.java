package in.akshat.account_service.entity;

 
import in.akshat.account_service.enums.AccountType;

//account-service
 
 
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts",
       uniqueConstraints = @UniqueConstraint(name = "uk_accounts_account_no", columnNames = "account_no"))
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // business identifier (generated here)
    @Column(name = "account_no", nullable = false, length = 20)
    private String accountNo;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    // copied from Branch service at creation time (single source of truth)
    @Column(name = "ifsc_code", nullable = false, length = 11)
    private String ifscCode;
    
    @NotNull
    private AccountType accountType;
    
    @Column(name = "balance_in_paise", nullable = false)
    private Long balanceInPaise = 0L;
}

