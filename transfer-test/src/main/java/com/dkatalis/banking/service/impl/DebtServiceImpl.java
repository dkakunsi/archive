package com.dkatalis.banking.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dkatalis.banking.model.Debt;
import com.dkatalis.banking.repository.DebtRepository;
import com.dkatalis.banking.service.DebtService;

public class DebtServiceImpl implements DebtService {

    private DebtRepository debtRepository;

    public DebtServiceImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    @Override
    public Optional<Debt> findDebt(String debtor) {
        var optionalDebts = this.debtRepository.findByDebtor(debtor);
        if (optionalDebts.isEmpty()) {
            return Optional.empty();
        }
        return selectFirstUnpaid(optionalDebts.get());
    }

    private Optional<Debt> selectFirstUnpaid(Collection<Debt> debts) {
        return debts.stream().filter(debt -> !debt.isPaid()).findFirst();
    }

    @Override
    public Debt createDebt(String creditor, String debtor, BigDecimal amountOfDebt) {
        var creatingDebt = Debt.of(creditor, debtor, amountOfDebt);
        this.debtRepository.save(creatingDebt);
        return creatingDebt;
    }

    @Override
    public void payDebt(String debtor, String creditor, BigDecimal paymentAmount) {
        var optionalDebt = this.debtRepository.findOne(debtor, creditor);
        if (optionalDebt.isEmpty()) {
            return;
        }
        var debt = optionalDebt.get();
        if (debt.isPaid()) {
            return;
        }
        debt.pay(paymentAmount);
        this.debtRepository.save(debt);
    }

    @Override
    public Optional<List<Debt>> findDebts(String accountName) {
        var optionalDebts = this.debtRepository.findByDebtor(accountName);
        return optionalDebts.isEmpty() ? Optional.empty() : filterUnpaid(optionalDebts.get());
    }

    private Optional<List<Debt>> filterUnpaid(Collection<Debt> collection) {
        var unpaidDebts = collection.stream().filter(debt -> !debt.isPaid()).collect(Collectors.toList());
        return unpaidDebts == null ? Optional.empty() : Optional.of(unpaidDebts);
    }

    @Override
    public Optional<List<Debt>> findOwedDebts(String accountName) {
        var optionalDebts = this.debtRepository.findByCreditor(accountName);
        return optionalDebts.isEmpty() ? Optional.empty() : filterUnpaid(optionalDebts.get());
    }
}
