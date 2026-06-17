package com.astra.banking_system.service;

import com.astra.banking_system.dto.TransactionRequest;
import com.astra.banking_system.dto.TransferRequest;
import com.astra.banking_system.enums.TransactionType;
import com.astra.banking_system.model.Account;
import com.astra.banking_system.model.Transaction;
import com.astra.banking_system.model.User;
import com.astra.banking_system.repository.AccountRepo;
import com.astra.banking_system.repository.TransactionRepo;
import com.astra.banking_system.repository.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final UserRepo userRepo;
    private final AccountRepo accountRepo;
    private final TransactionRepo transactionRepo;

    public TransactionService(UserRepo userRepo, AccountRepo accountRepo, TransactionRepo transactionRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    public void deposit(TransactionRequest request){
        Account account = accountRepo.findByAccountNumber(request.getAccountNumber()).orElseThrow(()->
                new RuntimeException("Account Number Not Found"));
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepo.save(account);
        Transaction tx = new Transaction();
        tx.setFromAccount(null);
        tx.setToAccount(account);
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
        tx.setFromAccount(account);
        tx.setToAccount(null);
        tx.setTransactionDate(LocalDateTime.now());
        tx.setTransactionType(TransactionType.TRANSACTION_TYPE_WITHDRAW);
        tx.setAmount(request.getAmount());
        transactionRepo.save(tx);
    }
    public void transfer(TransferRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow(()->
                new RuntimeException("Email Not Found"));

        Account fromAccount = userRepo.findByUserId(user.getId());
        Account toAccount = userRepo.findByAccountNumber(request.getToAccount());

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setTransactionType(TransactionType.TRANSACTION_TYPE_TRANSFER);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepo.save(transaction);
    }

}
