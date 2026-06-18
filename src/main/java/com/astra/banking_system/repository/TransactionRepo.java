package com.astra.banking_system.repository;

import com.astra.banking_system.model.Account;
import com.astra.banking_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    List<Transaction>findByFromAccountOrToAccount(
            Account fromAccount,
            Account toAccount
    );
}
