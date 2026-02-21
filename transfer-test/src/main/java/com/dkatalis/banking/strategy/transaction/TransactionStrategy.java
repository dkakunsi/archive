package com.dkatalis.banking.strategy.transaction;

import com.dkatalis.banking.exception.InsufficientBalanceException;
import com.dkatalis.banking.model.Transaction;

public interface TransactionStrategy {

    Transaction process(Transaction transaction) throws InsufficientBalanceException;
    
}
