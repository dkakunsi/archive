package com.dkatalis.banking.service.impl;

import java.math.BigDecimal;

import com.dkatalis.banking.exception.InsufficientBalanceException;
import com.dkatalis.banking.model.Account;
import com.dkatalis.banking.model.Balance;
import com.dkatalis.banking.repository.BalanceRepository;
import com.dkatalis.banking.service.BalanceService;
import com.dkatalis.banking.strategy.credit.CreditStrategyFactory;

public class BalanceServiceImpl implements BalanceService {

    private CreditStrategyFactory creditStrategyFactory;

    private BalanceRepository balanceRepository;

    public BalanceServiceImpl(BalanceRepository balanceRepository, CreditStrategyFactory creditStrategyFactory) {
        this.creditStrategyFactory = creditStrategyFactory;
        this.balanceRepository = balanceRepository;
    }

    @Override
    public BalanceRepository getBalanceRepository() {
        return this.balanceRepository;
    }

    @Override
    public Balance createBalance(Account account) {
        var creatingBalance = Balance.of(account.getAccountName());
        return this.balanceRepository.save(creatingBalance);
    }

    @Override
    public void credit(String accountName, BigDecimal amount) {
        var creditStrategy = this.creditStrategyFactory.creditStrategy();
        creditStrategy.credit(accountName, amount);
    }

    @Override
    public void debit(String accountName, BigDecimal amount) throws InsufficientBalanceException {
        var accountBalance = getBalance(accountName);
        if (isBalanceSufficient(accountBalance, amount)) {
            throw new InsufficientBalanceException("Your balance is not sufficient.");
        }
        accountBalance.debit(amount);
        this.balanceRepository.save(accountBalance);
    }

    @Override
    public Balance getBalance(String accountName) {
        var optionalAccountBalance = this.balanceRepository.findOne(accountName);
        var accountBalance = Balance.of(accountName);
        if (optionalAccountBalance.isPresent()) {
            accountBalance = optionalAccountBalance.get();
        }
        return accountBalance;
    }

    private boolean isBalanceSufficient(Balance balance, BigDecimal debitAmount) {
        return balance.getAmount().compareTo(debitAmount) < 0;
    }
}
