package com.astra.banking_system.service;

import com.astra.banking_system.dto.TransactionHistoryResponse;
import com.astra.banking_system.dto.TransactionRequest;
import com.astra.banking_system.dto.TransferRequest;
import com.astra.banking_system.enums.TransactionType;
import com.astra.banking_system.model.Account;
import com.astra.banking_system.model.Transaction;
import com.astra.banking_system.model.User;
import com.astra.banking_system.repository.AccountRepo;
import com.astra.banking_system.repository.TransactionRepo;
import com.astra.banking_system.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    public void deposit(TransactionRequest request){
        Account account = accountRepo.findByAccountNumber(request.getAccountNumber()).orElseThrow(()->
                new RuntimeException("Account Number Not Found"));
        BigDecimal currentBalance = account.getBalance() == null
                ? BigDecimal.ZERO
                : account.getBalance();
        account.setBalance(currentBalance.add(request.getAmount()));
        accountRepo.save(account);
        Transaction tx = new Transaction();
        tx.setFromAccount(null);
        tx.setToAccount(account);
        tx.setTransactionDate(LocalDateTime.now());
        tx.setTransactionType(TransactionType.TRANSACTION_TYPE_DEPOSIT);
        tx.setAmount(request.getAmount());
        transactionRepo.save(tx);
    }

    @Transactional
    public void withdraw(TransactionRequest request){
        Account account = accountRepo.findByAccountNumber(request.getAccountNumber()).orElseThrow(()->
                new RuntimeException("Account Number Not Found"));

        BigDecimal currentBalance = account.getBalance() == null
                ? BigDecimal.ZERO
                : account.getBalance();

        if (currentBalance.compareTo(request.getAmount())<0){
            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(currentBalance.subtract(request.getAmount()));
        accountRepo.save(account);
        Transaction tx = new Transaction();
        tx.setFromAccount(account);
        tx.setToAccount(null);
        tx.setTransactionDate(LocalDateTime.now());
        tx.setTransactionType(TransactionType.TRANSACTION_TYPE_WITHDRAW);
        tx.setAmount(request.getAmount());
        transactionRepo.save(tx);
    }

    @Transactional
    public void transfer(TransferRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow(()->
                new RuntimeException("Email Not Found"));

        Account fromAccount = accountRepo.findByUser_Id(user.getId()).orElseThrow(
                ()-> new RuntimeException("Sender Account Not Found")
        );
        Account toAccount = accountRepo.findByAccountNumber(request.getToAccount()).orElseThrow(
                ()->new RuntimeException("Receiver Account Not Found ")
        );

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

    public List<TransactionHistoryResponse> transactionHistory(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow(()->
                new RuntimeException("Email Not Found"));
        Account account = accountRepo.findByUser_Id(user.getId()).orElseThrow(
                ()->new RuntimeException("Account Not Found")
        );

        List<Transaction> transaction = transactionRepo.findByFromAccountOrToAccount(
                account,
                account
        );
        return transaction.stream()
                .map(transaction1 -> new TransactionHistoryResponse(
                        transaction1.getFromAccount().getAccountNumber(),
                        transaction1.getToAccount().getAccountNumber(),
                        transaction1.getAmount(),
                        transaction1.getTransactionType(),
                        transaction1.getTransactionDate()
                ))
                .toList();
    }

}
