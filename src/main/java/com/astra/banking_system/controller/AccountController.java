package com.astra.banking_system.controller;

import com.astra.banking_system.dto.AccountCreationRequest;
import com.astra.banking_system.dto.AccountDetailsResponse;
import com.astra.banking_system.dto.BalanceResponse;
import com.astra.banking_system.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String>createAccount(@RequestBody AccountCreationRequest request){
        accountService.createAccount(request);
        return ResponseEntity.ok("Account Created");
    }

    @GetMapping("/viewBalance")
    public ResponseEntity<BalanceResponse> viewBalance(){
        return ResponseEntity.ok(accountService.viewBalance());
    }

    @GetMapping("/viewAccount")
    public ResponseEntity<AccountDetailsResponse> viewAccount(){
        return ResponseEntity.ok(accountService.getAccountDetails());
    }


}
