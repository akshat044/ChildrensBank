package in.akshat.credit_card_service.entity;


import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name="credit_applications", indexes = {@Index(columnList = "clientRequestId", unique = true)})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreditApplication {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String applicationId;
  private String clientRequestId;
  private Long customerId;
  private String accountNo;
  private Long requestedLimitInPaise;
  private String status; // PENDING, APPROVED, REJECTED
  private String decisionReason;
  private Instant createdAt = Instant.now();
}