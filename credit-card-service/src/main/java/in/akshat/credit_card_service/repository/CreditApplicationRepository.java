package in.akshat.credit_card_service.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;

import in.akshat.credit_card_service.entity.CreditApplication;

import java.util.List;
import java.util.Optional;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {
    Optional<CreditApplication> findByClientRequestId(String clientRequestId);
    Optional<CreditApplication> findByApplicationId(String applicationId);
    List<CreditApplication> findByStatus(String status);
}