package com.dkatalis.banking.strategy.credit;

import java.math.BigDecimal;

import com.dkatalis.banking.repository.BalanceRepository;
import com.dkatalis.banking.service.BalanceService;

public class DefaultCreditStrategy implements CreditStrategy {

    private BalanceService balanceService;

    private BalanceRepository balanceRepository;

    public DefaultCreditStrategy(BalanceService balanceService) {
        this.balanceService = balanceService;
        this.balanceRepository = balanceService.getBalanceRepository();
    }

    @Override
    public void credit(String accountName, BigDecimal amount) {
        var accountBalance = this.balanceService.getBalance(accountName);
        accountBalance.credit(amount);
        this.balanceRepository.save(accountBalance);
    }
}
