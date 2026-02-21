package com.dkatalis.banking.service.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import com.dkatalis.banking.model.Debt;
import com.dkatalis.banking.repository.DebtRepository;
import com.dkatalis.banking.repository.inmemory.InMemoryDebtRepository;

import org.junit.Before;
import org.junit.Test;

public class DebtServiceImplTest {

    private DebtServiceImpl debtService;

    private DebtRepository debtRepository;

    @Before
    public void setup() {
        this.debtRepository = new InMemoryDebtRepository();
        this.debtService = new DebtServiceImpl(this.debtRepository);
    }

    @Test
    public void testPayDebt_WhenAllPaid_SetStatusToPaid() {
        var creditor = "Bob";
        var debtor = "Alice";
        var amount = BigDecimal.valueOf(30);
        var debt = Debt.of(creditor, debtor, amount);

        this.debtRepository.save(debt);
        this.debtService.payDebt(debtor, creditor, BigDecimal.valueOf(30));

        assertThat(debt.isPaid(), is(true));
        assertThat(debt.getAmount(), is(BigDecimal.valueOf(0)));
    }

    @Test
    public void testPayDebt_WhenPartiallyPaid_SetStatusToUnpaid() {
        var creditor = "Bob";
        var debtor = "Alice";
        var amount = BigDecimal.valueOf(30);
        var debt = Debt.of(creditor, debtor, amount);

        this.debtRepository.save(debt);
        this.debtService.payDebt(debtor, creditor, BigDecimal.valueOf(10));

        assertThat(debt.isPaid(), is(false));
        assertThat(debt.getAmount(), is(BigDecimal.valueOf(20)));
    }
}
