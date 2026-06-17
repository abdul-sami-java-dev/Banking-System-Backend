package com.astra.banking_system.dto;

import com.astra.banking_system.enums.Type;

import java.math.BigDecimal;

public class AccountCreationRequest {

    private Type accountType;


    public Type getAccountType() {
        return accountType;
    }

    public void setAccountType(Type accountType) {
        this.accountType = accountType;
    }
}
