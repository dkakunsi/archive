package com.dkatalis.banking.service.impl;

import java.util.Optional;

import com.dkatalis.banking.model.Account;
import com.dkatalis.banking.repository.AccountRepository;
import com.dkatalis.banking.service.AccountService;
import com.dkatalis.banking.service.BalanceService;

public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private BalanceService balanceService;

    public AccountServiceImpl(AccountRepository accountRepository, BalanceService balanceService) {
        this.accountRepository = accountRepository;
        this.balanceService = balanceService;
    }

    @Override
    public Account createAccount(String username) {
        var creatingAccount = Account.of(username);
        var account = this.accountRepository.save(creatingAccount);

        this.balanceService.createBalance(account);

        return account;
    }

    @Override
    public Account findOrCreate(String username) {
        var existingAccount = this.accountRepository.findOne(username);
        return existingAccount.isPresent() ? existingAccount.get() : createAccount(username);
    }

    @Override
    public Optional<Account> findOne(String username) {
        return this.accountRepository.findOne(username);
    }
}
