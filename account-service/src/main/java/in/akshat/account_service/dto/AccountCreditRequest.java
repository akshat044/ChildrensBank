package in.akshat.account_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountCreditRequest {
@NotBlank
private String accountNo;


@NotNull @Min(1)
private Long amountInPaise;


@NotBlank
private String txnId;


public AccountCreditRequest() {}
public AccountCreditRequest(String accountNo, Long amountInPaise, String txnId){
this.accountNo = accountNo; this.amountInPaise = amountInPaise; this.txnId = txnId;
}


public String getAccountNo() { return accountNo; }
public void setAccountNo(String accountNo) { this.accountNo = accountNo; }
public Long getAmountInPaise() { return amountInPaise; }
public void setAmountInPaise(Long amountInPaise) { this.amountInPaise = amountInPaise; }
public String getTxnId() { return txnId; }
public void setTxnId(String txnId) { this.txnId = txnId; }
}