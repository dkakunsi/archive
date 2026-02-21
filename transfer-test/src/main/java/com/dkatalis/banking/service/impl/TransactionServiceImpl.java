package com.dkatalis.banking.service.impl;

import com.dkatalis.banking.exception.InsufficientBalanceException;
import com.dkatalis.banking.exception.UnsupportedTransferType;
import com.dkatalis.banking.model.Transaction;
import com.dkatalis.banking.repository.TransactionRepository;
import com.dkatalis.banking.service.TransactionService;
import com.dkatalis.banking.strategy.transaction.TransactionStrategyFactory;

public class TransactionServiceImpl implements TransactionService {

    private TransactionStrategyFactory transactionStrategyFactory;

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionStrategyFactory transactionStrategyFactory, TransactionRepository transactionRepository) {
        this.transactionStrategyFactory = transactionStrategyFactory;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction process(Transaction transaction) throws UnsupportedTransferType, InsufficientBalanceException {
        var transferStrategy = this.transactionStrategyFactory.transferStrategy(transaction);
        var updatedTransfer = transferStrategy.process(transaction);
        return this.transactionRepository.save(updatedTransfer);
    }
}
