package com.dkatalis.banking.strategy.transaction;

import com.dkatalis.banking.Logger;
import com.dkatalis.banking.exception.UnsupportedTransferType;
import com.dkatalis.banking.model.Transaction;
import com.dkatalis.banking.repository.TransactionRepository;
import com.dkatalis.banking.service.BalanceService;
import com.dkatalis.banking.service.DebtService;

public final class TransactionStrategyFactory {

    private Logger logger;

    private TransactionRepository transactionRepository;

    private BalanceService balanceService;

    private DebtService debtService;

    public TransactionStrategyFactory(Logger logger) {
        this.logger = logger;
    }

    public TransactionRepository getTransactionRepository() {
        return this.transactionRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    public void setDebtService(DebtService debtService) {
        this.debtService = debtService;
    }

    public TransactionStrategy transferStrategy(Transaction transaction) throws UnsupportedTransferType {
        switch (transaction.getTransactionType()) {
            case TRANSFER:
                return new InternalTransferStrategy(this.balanceService, this.debtService);
            case DEPOSIT:
                return new DepositStrategy(getTransactionRepository(), this.balanceService);
            case WITHDRAW:
                return new WithdrawStrategy(getTransactionRepository(), this.balanceService);
            case AUTO_TRANSFER:
                return new AutoTransferStrategy(this.logger, new InternalTransferStrategy(this.balanceService, this.debtService));
            default:
                throw new UnsupportedTransferType(
                        String.format("Transfer type is not supported: %s", transaction.getTransactionType()));
        }
    }
}
