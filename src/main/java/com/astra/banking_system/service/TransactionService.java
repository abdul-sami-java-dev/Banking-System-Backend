package com.astra.banking_system.service;

import com.astra.banking_system.dto.TransactionRequest;
import com.astra.banking_system.enums.TransactionType;
import com.astra.banking_system.model.Account;
import com.astra.banking_system.model.Transaction;
import com.astra.banking_system.repository.AccountRepo;
import com.astra.banking_system.repository.TransactionRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final AccountRepo accountRepo;
    private final TransactionRepo transactionRepo;

    public TransactionService(AccountRepo accountRepo, TransactionRepo transactionRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    public void deposit(TransactionRequest request){
        Account account = accountRepo.findByAccountNumber(request.getAccountNumber()).orElseThrow(()->
                new RuntimeException("Account Number Not Found"));
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepo.save(account);
        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setTransactionDate(LocalDateTime.now());
        tx.setTransactionType(TransactionType.TRANSACTION_TYPE_DEPOSIT);
        tx.setAmount(request.getAmount());
        transactionRepo.save(tx);
    }

    public void withdraw(TransactionRequest request){
        Account account = accountRepo.findByAccountNumber(request.getAccountNumber()).orElseThrow(()->
                new RuntimeException("Account Number Not Found"));
        if (account.getBalance().compareTo(request.getAmount())<0){
            throw new RuntimeException("Insufficient Balance");
        }
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepo.save(account);
        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setTransactionDate(LocalDateTime.now());
        tx.setTransactionType(TransactionType.TRANSACTION_TYPE_DEPOSIT);
        tx.setAmount(request.getAmount());
        transactionRepo.save(tx);
    }

}
