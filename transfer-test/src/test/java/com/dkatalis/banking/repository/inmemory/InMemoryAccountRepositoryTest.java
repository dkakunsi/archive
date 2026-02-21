package com.dkatalis.banking.repository.inmemory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.dkatalis.banking.model.Account;

import org.junit.Before;
import org.junit.Test;

public class InMemoryAccountRepositoryTest {
    
    private InMemoryAccountRepository accountRepository;

    @Before
    public void setup() {
        this.accountRepository = new InMemoryAccountRepository();
    }

    @Test
    public void testSave_ShouldSuccess() {
        var aliceAccount = Account.of("Alice");
        var account = this.accountRepository.save(aliceAccount);

        assertThat(account.getAccountName(), is(account.getAccountName()));
    }

    @Test
    public void testFindOne_WhenAccountExists() {
        this.accountRepository.save(Account.of("Alice"));
        var optionalAccount = this.accountRepository.findOne("Alice");
        assertThat(optionalAccount.isPresent(), is(true));
    }

    @Test
    public void testFindOne_WhenAccountNotExists() {
        var optionalAccount = this.accountRepository.findOne("Alice");
        assertThat(optionalAccount.isPresent(), is(false));
    }
}
