package in.akshat.account_service.controller;


import in.akshat.account_service.dto.*;
import in.akshat.account_service.service.AccountService;
import jakarta.validation.Valid;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RefreshScope
@RestController	
@RequestMapping("/api/accounts")
public class AccountController {


private final AccountService accountService;
public AccountController(AccountService accountService) { this.accountService = accountService; }


@PostMapping
public ResponseEntity<AccountResponse> open(@Valid @RequestBody AccountCreateRequest req) {
return ResponseEntity.status(201).body(accountService.openAccount(req));
}


@GetMapping("/{customerId}")
public ResponseEntity<List<AccountResponse>> getAllAccountsOfCustomers(@PathVariable Long customerId){
return new ResponseEntity<>(accountService.getAllAccountsOfCustomers(customerId), HttpStatus.OK);
}


@PostMapping("/debit")
public ResponseEntity<SimpleResponse> debit(@Valid @RequestBody AccountDebitRequest req) {
accountService.debit(req);
return ResponseEntity.ok(new SimpleResponse("OK", "Debited"));
}


@PostMapping("/credit")
public ResponseEntity<SimpleResponse> credit(@Valid @RequestBody AccountCreditRequest req) {
accountService.credit(req);
return ResponseEntity.ok(new SimpleResponse("OK", "Credited"));
}

@GetMapping("/check/{accountNo}")
public boolean checkAccountExist(@PathVariable String accountNo) {
	return accountService.accountExist(accountNo);
}
}