package in.akshat.debit_card_service.repository;

 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.akshat.debit_card_service.entity.DebitCard;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
    Optional<DebitCard> findByClientRequestId(String clientRequestId);
}