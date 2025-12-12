package in.akshat.account_service.repository;


import java.util.List;
import java.util.Optional;


import in.akshat.account_service.entity.Account;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;


public interface AccountRepository extends JpaRepository<Account, Long> {
List<Account> findByCustomerId(Long customerId);
Optional<Account> findByAccountNo(String accountNo);


@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select a from Account a where a.accountNo = :accountNo")
Optional<Account> findByAccountNoForUpdate(@Param("accountNo") String accountNo);

boolean existsByAccountNo(String accountNo);
}