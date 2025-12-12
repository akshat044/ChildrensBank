package in.akshat.credit_card_service.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "credit_cards")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreditCard {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientRequestId;
    private String cardId;
    private Long customerId;
    private String accountNo;
    private String token;
    private String last4;
    private Integer expiryMonth;
    private Integer expiryYear;
    private String status;
    private String sourceRef;
    private Instant createdAt = Instant.now();
}