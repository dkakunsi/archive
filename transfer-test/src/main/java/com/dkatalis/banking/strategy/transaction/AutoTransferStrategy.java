package com.dkatalis.banking.strategy.transaction;

import com.dkatalis.banking.Logger;
import com.dkatalis.banking.model.Transaction;
import com.dkatalis.banking.model.TransferTransaction;

public class AutoTransferStrategy implements TransactionStrategy {

    private Logger logger;

    private InternalTransferStrategy internalTransferStrategy;

    public AutoTransferStrategy(Logger logger, InternalTransferStrategy internalTransferStrategy) {
        this.logger = logger;
        this.internalTransferStrategy = internalTransferStrategy;
    }

    @Override
    public Transaction process(Transaction transaction) {
        var updatedTransferTransaction = this.internalTransferStrategy.process(transaction);
        this.logger.add(String.format("Transferred $%s to %s", updatedTransferTransaction.getAmount(),
                ((TransferTransaction) transaction).getReceivingAccount()));
        return updatedTransferTransaction;
    }
}
