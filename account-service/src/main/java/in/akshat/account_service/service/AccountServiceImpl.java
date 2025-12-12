package in.akshat.account_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.akshat.account_service.client.BranchClient;
import in.akshat.account_service.dto.AccountCreateRequest;
import in.akshat.account_service.dto.AccountCreditRequest;
import in.akshat.account_service.dto.AccountDebitRequest;
import in.akshat.account_service.dto.AccountResponse;
import in.akshat.account_service.dto.BranchResponse;
import in.akshat.account_service.entity.Account;
import in.akshat.account_service.entity.AccountTransaction;
import in.akshat.account_service.exception.AccountNotFoundException;
import in.akshat.account_service.exception.DuplicateTransactionException;
import in.akshat.account_service.exception.InsufficientFundsException;
import in.akshat.account_service.repository.AccountRepository;
import in.akshat.account_service.repository.AccountTransactionRepository;

@Service

public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final BranchClient branchClient;
	private final AccountNumberGenerator generator;
	private final AccountTransactionRepository txRepo;

	public AccountServiceImpl(AccountRepository accountRepository, BranchClient branchClient,
			AccountNumberGenerator generator, AccountTransactionRepository txRepo) {
		this.accountRepository = accountRepository;
		this.branchClient = branchClient;
		this.generator = generator;
		this.txRepo = txRepo;
	}

	@Override
	@Transactional
	public AccountResponse openAccount(AccountCreateRequest req) {
		BranchResponse branch = branchClient.getBranchById(req.getBranchId());
		if (branch == null) {
			throw new IllegalStateException("Branch not found: " + req.getBranchId());
		}

		String accountNo = generator.nextForBranch(req.getBranchId());

		Account a = new Account();
		a.setCustomerId(req.getCustomerId());
		a.setBranchId(req.getBranchId());
		a.setAccountType(req.getAccountType());
		a.setIfscCode(branch.getIfscCode());
		a.setAccountNo(accountNo);
		a.setBalanceInPaise(0L);

		Account saved = accountRepository.save(a);

		return AccountResponse.builder().accountId(saved.getId()).accountNo(saved.getAccountNo())
				.customerId(saved.getCustomerId()).branchId(saved.getBranchId()).ifscCode(saved.getIfscCode())
				.accountType(saved.getAccountType()).build();
	}

	@Override
	public List<AccountResponse> getAllAccountsOfCustomers(Long customerId) {
		List<Account> accounts = accountRepository.findByCustomerId(customerId);
		List<AccountResponse> accountResponseDto = new ArrayList<>();
		for (Account a : accounts) {
			AccountResponse b = AccountResponse.builder().accountId(a.getId()).accountNo(a.getAccountNo())
					.customerId(a.getCustomerId()).branchId(a.getBranchId()).ifscCode(a.getIfscCode())
					.accountType(a.getAccountType()).build();
			accountResponseDto.add(b);
		}
		return accountResponseDto;
	}
	@Override
	@Transactional
	public void debit(AccountDebitRequest req) {
	Optional<AccountTransaction> existingTx = txRepo.findByTxnId(req.getTxnId());
	if (existingTx.isPresent()) {
	AccountTransaction tx = existingTx.get();
	if ("SUCCESS".equals(tx.getStatus())) return;
	if ("FAILED".equals(tx.getStatus())) throw new DuplicateTransactionException("Transaction previously failed: " + req.getTxnId());
	}


	Account account = accountRepository.findByAccountNoForUpdate(req.getAccountNo())
	.orElseThrow(() -> new AccountNotFoundException("Account not found: " + req.getAccountNo()));


	if (account.getBalanceInPaise() == null || account.getBalanceInPaise() < req.getAmountInPaise()) {
	AccountTransaction failed = new AccountTransaction();
	failed.setTxnId(req.getTxnId());
	failed.setAccountNo(req.getAccountNo());
	failed.setAmountInPaise(req.getAmountInPaise());
	failed.setType("DEBIT");
	failed.setStatus("FAILED");
	failed.setReason("Insufficient funds");
	txRepo.save(failed);
	throw new InsufficientFundsException("Insufficient funds for account: " + req.getAccountNo());
	}
	account.setBalanceInPaise(account.getBalanceInPaise() - req.getAmountInPaise());
	accountRepository.save(account); // note one customer can have multiple acount , one customer have one account, but in our code
                                      // many customer have same account is not possible(even though it is possible in real world)

	AccountTransaction tx = new AccountTransaction();
	tx.setTxnId(req.getTxnId());
	tx.setAccountNo(req.getAccountNo());
	tx.setAmountInPaise(req.getAmountInPaise());
	tx.setType("DEBIT");
	tx.setStatus("SUCCESS");
	txRepo.save(tx);
	
	}
	@Override
	@Transactional
	public void credit(AccountCreditRequest req) {
	Optional<AccountTransaction> existingTx = txRepo.findByTxnId(req.getTxnId());
	if (existingTx.isPresent()) {
	AccountTransaction tx = existingTx.get();
	if ("SUCCESS".equals(tx.getStatus())) return;
	if ("FAILED".equals(tx.getStatus())) throw new DuplicateTransactionException("Transaction previously failed: " + req.getTxnId());
	}


	Account account = accountRepository.findByAccountNoForUpdate(req.getAccountNo())
	.orElseThrow(() -> new AccountNotFoundException("Account not found: " + req.getAccountNo()));


	account.setBalanceInPaise((account.getBalanceInPaise() == null ? 0L : account.getBalanceInPaise()) + req.getAmountInPaise());
	accountRepository.save(account);


	AccountTransaction tx = new AccountTransaction();
	tx.setTxnId(req.getTxnId());
	tx.setAccountNo(req.getAccountNo());
	tx.setAmountInPaise(req.getAmountInPaise());
	tx.setType("CREDIT");
	tx.setStatus("SUCCESS");
	txRepo.save(tx);
	}

	@Override
	public boolean accountExist(String accountNo) {
		 
	return 	accountRepository.existsByAccountNo(accountNo);
	}

}
