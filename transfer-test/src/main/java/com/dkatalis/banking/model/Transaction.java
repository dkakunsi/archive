package com.dkatalis.banking.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class Transaction {

    protected String uuid;

    protected String accountName;

    protected TransactionType transactionType;

    protected BigDecimal amount;

    protected Transaction(String accountName, TransactionType transactionType, BigDecimal amount) {
        this.uuid = UUID.randomUUID().toString();
        this.accountName = accountName;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public static enum TransactionType {
        DEPOSIT, WITHDRAW, TRANSFER, AUTO_TRANSFER;
    }

    public static Transaction of(String accountName, BigDecimal amount, TransactionType transactionType) {
        return new Transaction(accountName, transactionType, amount);
    }
    
}
