package com.dkatalis.banking.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferTransaction extends Transaction {

    private String receivingAccount;

    private TransferTransaction(String sendingAccount, String receivingAccount, TransactionType transactionType,
            BigDecimal amount) {
        super(sendingAccount, transactionType, amount);
        this.receivingAccount = receivingAccount;
    }

    public String getSendingAccount() {
        return getAccountName();
    }

    public static TransferTransaction transfer(String sendingAccount, String receivingAccount, BigDecimal amount) {
        return new TransferTransaction(sendingAccount, receivingAccount, TransactionType.TRANSFER, amount);
    }

    public static TransferTransaction autoTransfer(String sendingAccount, String receivingAccount, BigDecimal amount) {
        return new TransferTransaction(sendingAccount, receivingAccount, TransactionType.AUTO_TRANSFER, amount);
    }

    public static TransferTransaction of(String sendingAccount, String receivingAccount, BigDecimal amount, TransactionType transactionType) {
        return new TransferTransaction(sendingAccount, receivingAccount, transactionType, amount);
    }
}
