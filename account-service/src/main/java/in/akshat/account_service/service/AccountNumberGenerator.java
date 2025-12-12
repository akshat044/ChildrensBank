package in.akshat.account_service.service;

import in.akshat.account_service.entity.AccountNumberSeq;
import in.akshat.account_service.repository.AccountNumberSeqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountNumberGenerator {

    private final AccountNumberSeqRepository repo;

    @Transactional
    public String nextForBranch(Long branchId) {
        AccountNumberSeq row = repo.findForUpdate(branchId)
            .orElseGet(() -> createRowIfMissing(branchId));

        long next = row.getLastNumber() + 1;
        row.setLastNumber(next);
        // managed entity; dirty checking will update it on commit
        // (optional) repo.saveAndFlush(row);

        String branchPart = String.format("%04d", branchId % 10000);
        String counter    = String.format("%08d", next);
        return branchPart + counter;
    }

    private AccountNumberSeq createRowIfMissing(Long branchId) {
        try {
            AccountNumberSeq r = new AccountNumberSeq();
            r.setBranchId(branchId);
            r.setLastNumber(0L);
            return repo.saveAndFlush(r);
        } catch (DataIntegrityViolationException e) {
            // Another thread created it first; re-read with lock
            return repo.findForUpdate(branchId).orElseThrow();
        }
    }
}
