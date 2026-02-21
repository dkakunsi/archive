package com.dkatalis.banking.service;

import java.math.BigDecimal;

import com.dkatalis.banking.exception.InsufficientBalanceException;
import com.dkatalis.banking.model.Account;
import com.dkatalis.banking.model.Balance;
import com.dkatalis.banking.repository.BalanceRepository;

public interface BalanceService {

    Balance createBalance(Account account);

    void credit(String username, BigDecimal amount);

    void debit(String accountName, BigDecimal amount) throws InsufficientBalanceException;

    BalanceRepository getBalanceRepository();

    Balance getBalance(String accountName);
    
}
