package com.astra.banking_system.service;

import com.astra.banking_system.dto.AccountCreationRequest;
import com.astra.banking_system.enums.Status;
import com.astra.banking_system.model.Account;
import com.astra.banking_system.model.User;
import com.astra.banking_system.repository.AccountRepo;
import com.astra.banking_system.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class AccountService {

    private final AccountRepo accountRepo;

    private final UserRepo userRepo;

    public AccountService(AccountRepo accountRepo, UserRepo userRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    public Account createAccount(AccountCreationRequest accountCreationRequest){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("Email Not Registered"));

        Account account = new Account();
        account.setUser(user);
        account.setCreatedAt(LocalDate.now());
        account.setAccountType(accountCreationRequest.getAccountType());
        account.setAccountNumber(genAccountNumber(user));
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(Status.STATUS_PENDING);
        return accountRepo.save(account);
    }

    private String genAccountNumber(User user) {
        Long count = accountRepo.count();

        return String.format("BANK%05d", count + 1);
    }


}
