package com.astra.banking_system.repository;

import com.astra.banking_system.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByUserId(Long id);
}
