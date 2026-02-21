package com.dkatalis.banking.service;

import com.dkatalis.banking.Context;

public interface SessionService {

    Context login(String username);

    void logout(String accountName);
    
}
