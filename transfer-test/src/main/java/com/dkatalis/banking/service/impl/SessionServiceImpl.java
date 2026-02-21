package com.dkatalis.banking.service.impl;

import com.dkatalis.banking.Context;
import com.dkatalis.banking.model.Session;
import com.dkatalis.banking.repository.SessionRepository;
import com.dkatalis.banking.service.AccountService;
import com.dkatalis.banking.service.SessionService;

public class SessionServiceImpl implements SessionService {

    private SessionRepository sessionRepository;

    private AccountService accountService;

    public SessionServiceImpl(SessionRepository sessionRepository, AccountService accountService) {
        this.sessionRepository = sessionRepository;
        this.accountService = accountService;
    }

    @Override
    public Context login(String username) {
        var account = this.accountService.findOrCreate(username);
        var session = this.sessionRepository.save(Session.of(account));
        return Context.of(session);
    }

    @Override
    public void logout(String username) {
        var optionalActiveSession = this.sessionRepository.getActiveSession(username);
        if (optionalActiveSession.isEmpty()) {
            return;
        }

        var activeSession = optionalActiveSession.get();
        activeSession.close();

        this.sessionRepository.save(activeSession);
    }
}
