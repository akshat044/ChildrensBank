package in.akshat.payment_service.entity;
 

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import in.akshat.payment_service.enums.PaymentStatus;

@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_payment_client_request", columnList = "clientRequestId", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String clientRequestId;

    private Long customerId;
    private String fromAccount;
    private String toAccount;
    private Long amountInPaise;
    private String currency = "INR";

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String externalReference;
    private String failureReason;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    // getters & setters
}

