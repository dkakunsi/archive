package com.dkatalis.banking.repository.inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.dkatalis.banking.model.Balance;
import com.dkatalis.banking.repository.BalanceRepository;

public class InMemoryBalanceRepository implements BalanceRepository {

    private Map<String, Balance> map;

    public InMemoryBalanceRepository() {
        this.map = new HashMap<>();
    }

    @Override
    public Optional<Balance> findOne(String accountName) {
        var accountBalance = this.map.get(accountName);
        return accountBalance == null ? Optional.empty() : Optional.of(accountBalance);
    }

    @Override
    public Balance save(Balance balance) {
        this.map.put(balance.getAccountName(), balance);
        return balance;
    }
}
