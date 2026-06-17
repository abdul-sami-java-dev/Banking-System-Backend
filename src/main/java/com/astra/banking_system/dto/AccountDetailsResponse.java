package com.astra.banking_system.dto;

import com.astra.banking_system.enums.Status;
import com.astra.banking_system.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class AccountDetailsResponse {
    private String accountNumber;
    private BigDecimal balance;
    private Type accountType;
    private Status status;
    private LocalDate createdAt;

    public AccountDetailsResponse(String accountNumber,BigDecimal balance, Type accountType, Status status, LocalDate createdAt) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.status = status;
        this.createdAt = createdAt;
    }



    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Type getAccountType() {
        return accountType;
    }

    public void setAccountType(Type accountType) {
        this.accountType = accountType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
