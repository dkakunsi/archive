package com.dkatalis.banking.service.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.dkatalis.banking.Logger;
import com.dkatalis.banking.model.Account;
import com.dkatalis.banking.repository.AccountRepository;
import com.dkatalis.banking.repository.BalanceRepository;
import com.dkatalis.banking.service.BalanceService;
import com.dkatalis.banking.strategy.credit.CreditStrategyFactory;
import com.dkatalis.banking.strategy.credit.CreditStrategyFactory.CreditStrategyType;

import org.junit.Before;
import org.junit.Test;

public class AccountServiceImplTest {
    
    private AccountServiceImpl accountServiceImpl;

    private AccountRepository accountRepository;

    private BalanceService balanceService;

    private BalanceRepository balanceRepository;

    @Before
    public void setup() {
        var creditStrategyFactory = new CreditStrategyFactory(new Logger(), CreditStrategyType.AUTO_TRANSFER);
        this.balanceRepository = mock(BalanceRepository.class);
        this.balanceService = new BalanceServiceImpl(this.balanceRepository, creditStrategyFactory);
        this.accountRepository = mock(AccountRepository.class);
        this.accountServiceImpl = new AccountServiceImpl(this.accountRepository, this.balanceService);

        creditStrategyFactory.setBalanceService(this.balanceService);
    }

    @Test
    public void testFindOrCreate_WhenAccountExists_ReturnTheExisting() {
        var username = "Alice";
        var aliceAccount = Account.of(username);
        doReturn(Optional.of(aliceAccount)).when(this.accountRepository).findOne(eq(username));

        var retrievedAccount = this.accountServiceImpl.findOrCreate(username);
        assertThat(retrievedAccount, is(aliceAccount));
    }

    @Test
    public void testFindOrCreate_WhenAccountNotExists_CreateNewAccount() {
        var username = "Alice";
        doReturn(Optional.empty()).when(this.accountRepository).findOne(eq(username));
        var aliceAccount = Account.of(username);
        doReturn(aliceAccount).when(this.accountRepository).save(any(Account.class));

        var retrievedAccount = this.accountServiceImpl.findOrCreate(username);
        assertThat(retrievedAccount, is(aliceAccount));

        verify(this.accountRepository).save(any(Account.class));
    }
}
