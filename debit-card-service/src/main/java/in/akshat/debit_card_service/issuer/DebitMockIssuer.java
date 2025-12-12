package in.akshat.debit_card_service.issuer;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class DebitMockIssuer {

    public IssuerResponse issue(String clientRequestId) {
        String token = "debit_tok_" + UUID.randomUUID().toString().replace("-", "").substring(0,16);
        String last4 = String.valueOf((int)(1000 + Math.random()*9000));
        return new IssuerResponse(token, last4, 12, 2030, "ACTIVE", "iss-" + UUID.randomUUID());
    }

    public static record IssuerResponse(String token, String last4, int expMonth, int expYear, String status, String issuerRef) {}
}
