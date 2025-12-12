package in.akshat.account_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_transactions",
uniqueConstraints = @UniqueConstraint(name = "uk_account_tx_txn_id", columnNames = "txn_id"))
public class AccountTransaction {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Column(name = "txn_id", nullable = false, length = 100)
private String txnId;


@Column(name = "account_no", nullable = false, length = 20)
private String accountNo;


@Column(name = "amount_in_paise", nullable = false)
private Long amountInPaise;


@Column(name = "type", nullable = false, length = 10)
private String type; // "DEBIT" or "CREDIT"


@Column(name = "status", nullable = false, length = 10)
private String status; // "SUCCESS" / "FAILED"


@Column(name = "reason")
private String reason;


@Column(name = "created_at", nullable = false)
private Instant createdAt = Instant.now();

 
}