package in.akshat.account_service.repository;

import in.akshat.account_service.entity.AccountNumberSeq;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface AccountNumberSeqRepository extends JpaRepository<AccountNumberSeq, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from AccountNumberSeq s where s.branchId = :branchId")
    Optional<AccountNumberSeq> findForUpdate(@Param("branchId") Long branchId);
}
