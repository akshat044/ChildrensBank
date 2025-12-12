package in.akshat.payment_service.repository;
 

import in.akshat.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByClientRequestId(String clientRequestId);
}
