package com.dkatalis.banking.strategy.credit;

import com.dkatalis.banking.Logger;
import com.dkatalis.banking.service.BalanceService;
import com.dkatalis.banking.service.DebtService;
import com.dkatalis.banking.service.TransactionService;

public final class CreditStrategyFactory {

    public static enum CreditStrategyType {
        DEFAULT, AUTO_TRANSFER;
    }

    private Logger logger;

    private CreditStrategyType creditStrategyType;

    private BalanceService balanceService;

    private DebtService debtService;

    private TransactionService transactionService;

    public CreditStrategyFactory(Logger logger, CreditStrategyType creditStrategyType) {
        this.logger = logger;
        this.creditStrategyType = creditStrategyType;
    }

    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    public void setDebtService(DebtService debtService) {
        this.debtService = debtService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public CreditStrategy defaultCreditStrategy() {
        return new DefaultCreditStrategy(this.balanceService);
    }

    public CreditStrategy autoTransfeCreditStrategy() {
        return new AutoTransferCreditStrategy(this.logger, this.debtService, this.balanceService, this.transactionService);
    }

    public CreditStrategy creditStrategy() {
        switch (this.creditStrategyType) {
            case AUTO_TRANSFER:
                return autoTransfeCreditStrategy();
            default:
                return defaultCreditStrategy();
        }
    }
}
