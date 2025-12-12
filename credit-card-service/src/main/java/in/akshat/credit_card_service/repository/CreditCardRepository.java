package in.akshat.credit_card_service.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;

import in.akshat.credit_card_service.entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
