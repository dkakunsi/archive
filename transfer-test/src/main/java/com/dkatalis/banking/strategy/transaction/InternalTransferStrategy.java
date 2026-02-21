package com.dkatalis.banking.strategy.transaction;

import com.dkatalis.banking.exception.InsufficientBalanceException;
import com.dkatalis.banking.model.Transaction;
import com.dkatalis.banking.model.TransferTransaction;
import com.dkatalis.banking.service.BalanceService;
import com.dkatalis.banking.service.DebtService;

public class InternalTransferStrategy implements TransactionStrategy {

    private BalanceService balanceService;

    private DebtService debtService;

    InternalTransferStrategy(BalanceService balanceService, DebtService debtService) {
        this.balanceService = balanceService;
        this.debtService = debtService;
    }

    @Override
    public Transaction process(Transaction transaction) {
        if (!(transaction instanceof TransferTransaction)) {
            throw new IllegalArgumentException("Cannot process transaction.");
        }

        var transferTransaction = (TransferTransaction) transaction;
        try {
            this.balanceService.debit(transferTransaction.getSendingAccount(), transferTransaction.getAmount());
            this.balanceService.credit(transferTransaction.getReceivingAccount(), transferTransaction.getAmount());
            return transferTransaction;
        } catch (InsufficientBalanceException ex) {
            return withDebtTransfer(transferTransaction);
        }
    }

    private TransferTransaction withDebtTransfer(TransferTransaction transferTransaction) {
        var availableBalance = this.balanceService.getBalance(transferTransaction.getSendingAccount());
        var availableBalanceAmount = availableBalance.getAmount();
        try {
            this.balanceService.debit(transferTransaction.getSendingAccount(), availableBalance.getAmount());
        } catch (InsufficientBalanceException ex) {
            // This will never happen. The amount of debit always the available amount.
        }

        var amountOfDebt = transferTransaction.getAmount().subtract(availableBalanceAmount);
        this.debtService.createDebt(transferTransaction.getReceivingAccount(), transferTransaction.getSendingAccount(), amountOfDebt);
        this.balanceService.credit(transferTransaction.getReceivingAccount(), availableBalanceAmount);
        return TransferTransaction.of(transferTransaction.getSendingAccount(), transferTransaction.getReceivingAccount(), availableBalanceAmount, transferTransaction.getTransactionType());
    }
}
