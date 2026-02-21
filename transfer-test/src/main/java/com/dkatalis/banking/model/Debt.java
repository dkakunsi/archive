package com.dkatalis.banking.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Debt {

    private String creditor;

    private String debtor;

    private BigDecimal amount;

    private boolean paid;

    private Debt(String creditor, String debtor, BigDecimal amount) {
        this.creditor = creditor;
        this.debtor = debtor;
        this.amount = amount;
        this.paid = false;
    }

    public static Debt of(String creditor, String debtor, BigDecimal amount) {
        return new Debt(creditor, debtor, amount);
    }

    public void pay(BigDecimal paymentAmount) {
        this.amount = this.amount.subtract(paymentAmount);
        if (this.amount.compareTo(BigDecimal.ZERO) <= 0) {
            this.paid = true;
        }
    }
}
