package com.dkatalis.banking.service.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import com.dkatalis.banking.Logger;
import com.dkatalis.banking.exception.InsufficientBalanceException;
import com.dkatalis.banking.model.Account;
import com.dkatalis.banking.model.Balance;
import com.dkatalis.banking.model.Debt;
import com.dkatalis.banking.repository.BalanceRepository;
import com.dkatalis.banking.repository.DebtRepository;
import com.dkatalis.banking.repository.TransactionRepository;
import com.dkatalis.banking.repository.inmemory.InMemoryBalanceRepository;
import com.dkatalis.banking.repository.inmemory.InMemoryDebtRepository;
import com.dkatalis.banking.repository.inmemory.InMemoryTransactionRepository;
import com.dkatalis.banking.strategy.credit.CreditStrategyFactory;
import com.dkatalis.banking.strategy.credit.CreditStrategyFactory.CreditStrategyType;
import com.dkatalis.banking.strategy.transaction.TransactionStrategyFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BalanceServiceImplTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private BalanceServiceImpl balanceService;

    private BalanceRepository balanceRepository;

    private DebtServiceImpl debtService;

    private DebtRepository debtRepository;

    private TransactionServiceImpl transactionService;

    private TransactionRepository transactionRepository;

    @Before
    public void setup() {
        var logger = new Logger();
        var creditStrategyFactory = new CreditStrategyFactory(logger, CreditStrategyType.AUTO_TRANSFER);
        var transactionStrategyFactory = new TransactionStrategyFactory(logger);

        this.balanceRepository = new InMemoryBalanceRepository();
        this.balanceService = new BalanceServiceImpl(this.balanceRepository, creditStrategyFactory);
        creditStrategyFactory.setBalanceService(this.balanceService);
        transactionStrategyFactory.setBalanceService(this.balanceService);

        this.debtRepository = new InMemoryDebtRepository();
        this.debtService = new DebtServiceImpl(this.debtRepository);
        creditStrategyFactory.setDebtService(this.debtService);
        transactionStrategyFactory.setDebtService(this.debtService);

        this.transactionRepository = new InMemoryTransactionRepository();
        this.transactionService = new TransactionServiceImpl(transactionStrategyFactory, this.transactionRepository);
        creditStrategyFactory.setTransactionService(this.transactionService);
        transactionStrategyFactory.setTransactionRepository(this.transactionRepository);
    }

    @Test
    public void testCreateBalance_ShouldSuccess() {
        var username = "Alice";
        var aliceAccount = Account.of(username);
        var aliceBalance = Balance.of(username);
        this.balanceRepository.save(aliceBalance);

        var account = this.balanceService.createBalance(aliceAccount);
        assertNotNull(account);

        var optionalBalance = this.balanceRepository.findOne(username);
        assertThat(optionalBalance.isPresent(), is(true));
        assertThat(optionalBalance.get().getAmount(), is(BigDecimal.ZERO));
    }

    @Test
    public void testCredit_WhenBalanceIsExists_ShouldAddTheAmount() {
        var username = "Alice";
        var amount = BigDecimal.valueOf(100);
        var aliceBalance = Balance.of(username, amount);
        this.balanceRepository.save(aliceBalance);

        var additionalAmount = BigDecimal.valueOf(20);
        this.balanceService.credit(username, additionalAmount);

        var optionalAliceBalance = this.balanceRepository.findOne(username);
        assertThat(optionalAliceBalance.isPresent(), is(true));
        assertThat(optionalAliceBalance.get().getAmount(), is(BigDecimal.valueOf(120)));
    }

    @Test
    public void testCredit_WhenBalanceIsNotExists_ShouldCreateNewWithCreditingAmountAsTheAmount() {
        var username = "Alice";
        var amount = BigDecimal.valueOf(100);
        this.balanceService.credit(username, amount);

        var optionalAliceBalance = this.balanceRepository.findOne(username);
        assertThat(optionalAliceBalance.isPresent(), is(true));
        assertThat(optionalAliceBalance.get().getAmount(), is(amount));
    }

    @Test
    public void testDebit_WhenBalanceIsExists_ShouldSubstractTheAmount() throws InsufficientBalanceException {
        var username = "Alice";
        var amount = BigDecimal.valueOf(100);
        var aliceBalance = Balance.of(username, amount);
        this.balanceRepository.save(aliceBalance);

        var additionalAmount = BigDecimal.valueOf(20);
        this.balanceService.debit(username, additionalAmount);

        var optionalAliceBalance = this.balanceRepository.findOne(username);
        assertThat(optionalAliceBalance.isPresent(), is(true));
        assertThat(optionalAliceBalance.get().getAmount(), is(BigDecimal.valueOf(80)));
    }

    @Test
    public void testDebit_WhenBalanceIsNotExists_ShouldCreateNewAndThrowAmountNotEnoughException()
            throws InsufficientBalanceException {
        expectedException.expect(InsufficientBalanceException.class);
        expectedException.expectMessage("Your balance is not sufficient.");

        var username = "Alice";
        var amount = BigDecimal.valueOf(20);
        this.balanceService.debit(username, amount);
    }
    @Test
    public void testCredit_WhenDebtExists_ShouldCreateAutoTransfer() {
        var alice = "Alice";
        var aliceBalance = Balance.of(alice, BigDecimal.valueOf(0));
        this.balanceRepository.save(aliceBalance);

        var bob = "Bob";
        var bobBalance = Balance.of(bob, BigDecimal.valueOf(50));
        this.balanceRepository.save(bobBalance);

        var aliceDebtToBob = Debt.of(bob, alice, BigDecimal.valueOf(30));
        this.debtRepository.save(aliceDebtToBob);

        this.balanceService.credit(alice, BigDecimal.valueOf(20));

        aliceBalance = this.balanceRepository.findOne(alice).get();
        assertThat(aliceBalance.getAmount(), is(BigDecimal.valueOf(0)));
        bobBalance = this.balanceRepository.findOne(bob).get();
        assertThat(bobBalance.getAmount(), is(BigDecimal.valueOf(70)));
        aliceDebtToBob = this.debtRepository.findOne(alice, bob).get();
        assertThat(aliceDebtToBob.getAmount(), is(BigDecimal.valueOf(10)));
    }

}
