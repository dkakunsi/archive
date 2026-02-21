package com.dkatalis.banking.service;

import com.dkatalis.banking.exception.InsufficientBalanceException;
import com.dkatalis.banking.exception.UnsupportedTransferType;
import com.dkatalis.banking.model.Transaction;

public interface TransactionService {

    Transaction process(Transaction transaction) throws UnsupportedTransferType, InsufficientBalanceException;

}
