package com.dkatalis.banking.service;

import java.util.Optional;

import com.dkatalis.banking.model.Account;

public interface AccountService {
    
    Account createAccount(String username);

    Account findOrCreate(String username);

    Optional<Account> findOne(String username);
}
