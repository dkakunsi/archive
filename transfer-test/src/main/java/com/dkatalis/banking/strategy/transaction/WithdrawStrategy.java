package com.dkatalis.banking.strategy.transaction;

import com.dkatalis.banking.exception.InsufficientBalanceException;
import com.dkatalis.banking.model.Transaction;
import com.dkatalis.banking.repository.TransactionRepository;
import com.dkatalis.banking.service.BalanceService;

public class WithdrawStrategy implements TransactionStrategy {

    private TransactionRepository transactionRepository;

    private BalanceService balanceService;

    public WithdrawStrategy(TransactionRepository transactionRepository, BalanceService balanceService) {
        this.transactionRepository = transactionRepository;
        this.balanceService = balanceService;
    }

    @Override
    public Transaction process(Transaction transaction) throws InsufficientBalanceException {
        this.balanceService.debit(transaction.getAccountName(), transaction.getAmount());
        return this.transactionRepository.save(transaction);
    }
}
