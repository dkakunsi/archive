package com.dkatalis.banking.service.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.dkatalis.banking.model.Account;
import com.dkatalis.banking.model.Session;
import com.dkatalis.banking.repository.AccountRepository;
import com.dkatalis.banking.repository.SessionRepository;
import com.dkatalis.banking.service.AccountService;
import com.dkatalis.banking.service.BalanceService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class SessionServiceImplTest {

    private SessionServiceImpl accountServiceImpl;

    private SessionRepository sessionRepository;

    private AccountService accountService;

    private AccountRepository accountRepository;

    private BalanceService balanceService;

    @Before
    public void setup() {
        this.balanceService = mock(BalanceService.class);
        this.accountRepository = mock(AccountRepository.class);
        this.accountService = new AccountServiceImpl(this.accountRepository, this.balanceService);
        this.sessionRepository = mock(SessionRepository.class);
        this.accountServiceImpl = new SessionServiceImpl(this.sessionRepository, this.accountService);
    }

    @Test
    public void testLogin_WhenAccountIsExists_CreateSessionAndReturnContext() {
        var username = "Alice";
        var aliceAccount = Account.of(username);
        doReturn(Optional.of(aliceAccount)).when(this.accountRepository).findOne(eq(username));

        var aliceSession = Session.of(aliceAccount);
        doReturn(aliceSession).when(this.sessionRepository).save(any(Session.class));

        var context = this.accountServiceImpl.login(username);
        assertThat(context.getAccountName(), is(username));

        verify(this.sessionRepository).save(any(Session.class));
    }

    @Test
    public void testLogin_WhenAccountIsNotExists_CreateAccountAndCreateSessionAndReturnContext() {
        var username = "Alice";
        doReturn(Optional.empty()).when(this.accountRepository).findOne(eq(username));
        var aliceAccount = Account.of(username);
        doReturn(aliceAccount).when(this.accountRepository).save(any(Account.class));

        var aliceSession = Session.of(aliceAccount);
        doReturn(aliceSession).when(this.sessionRepository).save(any(Session.class));

        var context = this.accountServiceImpl.login(username);
        assertThat(context.getAccountName(), is(username));

        verify(this.sessionRepository).save(any(Session.class));
        verify(this.accountRepository).save(any(Account.class));
    }

    @Test
    public void testLogout_WhenActiveSessionExists() {
        var username = "Alice";
        var aliceAccount = Account.of(username);
        var aliceSession = Session.of(aliceAccount);
        assertNull(aliceSession.getCloseAt());

        doReturn(Optional.of(aliceSession)).when(this.sessionRepository).getActiveSession(username);

        this.accountServiceImpl.logout(username);

        var sessionCaptor = ArgumentCaptor.forClass(Session.class);
        verify(this.sessionRepository).save(sessionCaptor.capture());

        var updatingSession = sessionCaptor.getValue();
        assertThat(updatingSession.getAccountName(), is(username));
        assertNotNull(updatingSession.getCloseAt());
    }

    @Test
    public void testLogout_WhenNoActiveSession() {
        var username = "Alice";
        doReturn(Optional.empty()).when(this.sessionRepository).getActiveSession(username);

        this.accountServiceImpl.logout(username);
    }
}
