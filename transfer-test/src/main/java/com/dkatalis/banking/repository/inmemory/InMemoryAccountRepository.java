package com.dkatalis.banking.repository.inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.dkatalis.banking.model.Account;
import com.dkatalis.banking.repository.AccountRepository;

public class InMemoryAccountRepository implements AccountRepository {

    private Map<String, Account> map;

    public InMemoryAccountRepository() {
        this.map = new HashMap<>();
    }

    @Override
    public Account save(Account account) {
        this.map.put(account.getAccountName(), account);
        return account;
    }

    @Override
    public Optional<Account> findOne(String accountName) {
        var existingAccount = this.map.get(accountName);
        return existingAccount == null ? Optional.empty() : Optional.of(existingAccount);
    }
}
