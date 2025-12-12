package in.akshat.account_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_number_seq")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountNumberSeq {

    @Id
    @Column(name = "branch_id")
    private Long branchId;          // one row per branch

    @Column(name = "last_number", nullable = false)
    private Long lastNumber;        // last issued (we'll return last+1)

    @Version
    private Long version;           // managed by JPA, don't set manually
}
