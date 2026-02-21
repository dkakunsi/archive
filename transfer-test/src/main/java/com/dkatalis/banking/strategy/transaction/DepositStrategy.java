package com.dkatalis.banking.strategy.transaction;

import com.dkatalis.banking.model.Transaction;
import com.dkatalis.banking.repository.TransactionRepository;
import com.dkatalis.banking.service.BalanceService;

public class DepositStrategy implements TransactionStrategy {

    private TransactionRepository transactionRepository;

    private BalanceService balanceService;

    public DepositStrategy(TransactionRepository transactionRepository, BalanceService balanceService) {
        this.transactionRepository = transactionRepository;
        this.balanceService = balanceService;
    }

    @Override
    public Transaction process(Transaction transaction) {
        this.balanceService.credit(transaction.getAccountName(), transaction.getAmount());
        return this.transactionRepository.save(transaction);
    }
}
