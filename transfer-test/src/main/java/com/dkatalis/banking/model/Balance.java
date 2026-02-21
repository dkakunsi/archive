package com.dkatalis.banking.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Balance {

    private String accountName;

    private BigDecimal amount;

    private Balance(String accountName, BigDecimal amount) {
        this.accountName = accountName;
        this.amount = amount;
    }

    public static Balance of(String accountName) {
        return new Balance(accountName, BigDecimal.valueOf(0));
    }

    public static Balance of(String accountName, BigDecimal amount ) {
        return new Balance(accountName, amount);
    }

    public void credit(BigDecimal creditingAmount) {
        this.amount = this.amount.add(creditingAmount);
    }

    public void debit(BigDecimal withdrawingAmount) {
        this.amount = this.amount.subtract(withdrawingAmount);
    }
}
