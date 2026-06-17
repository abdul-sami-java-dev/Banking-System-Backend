package com.astra.banking_system.repository;

import com.astra.banking_system.model.Account;
import com.astra.banking_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

   Optional< User> findByEmail(String email);

   Optional<User> findByUserName(String userName);

   Optional<Account> findByUserId(Long id);

   Optional<Account> findByAccountNumber(String accountNumber);
}
