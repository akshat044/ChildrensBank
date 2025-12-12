package in.akshat.account_service.repository;
 

import in.akshat.account_service.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
Optional<AccountTransaction> findByTxnId(String txnId);
}