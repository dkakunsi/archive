package com.dkatalis.banking.repository.inmemory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dkatalis.banking.model.Debt;
import com.dkatalis.banking.repository.DebtRepository;

public class InMemoryDebtRepository implements DebtRepository {

    // Key 1 = Debtor, Key 2 = Creditor
    private Map<String, Map<String, Debt>> map;

    public InMemoryDebtRepository() {
        this.map = new HashMap<>();
    }

    @Override
    public Debt save(Debt debt) {
        var debtorDebts = this.map.get(debt.getDebtor());
        if (debtorDebts == null) {
            debtorDebts = new HashMap<>();
        }
        debtorDebts.put(debt.getCreditor(), debt);
        this.map.put(debt.getDebtor(), debtorDebts);

        return debt;
    }

    @Override
    public Optional<Debt> findOne(String debtorAccountName, String crediturAccountName) {
        var debtorDebts = this.map.get(debtorAccountName);
        if (debtorDebts == null) {
            return Optional.empty();
        }

        var debt = debtorDebts.get(crediturAccountName);
        if (debt == null) {
            return Optional.empty();
        }

        return Optional.of(debt);
    }

    @Override
    public Optional<Collection<Debt>> findByDebtor(String debtor) {
        var debts = this.map.get(debtor);
        return debts == null ? Optional.empty() : Optional.of(debts.values());
    }

    @Override
    public Optional<List<Debt>> findByCreditor(String creditor) {
        var debts = this.map.values().stream().map(value -> value.get(creditor)).filter(value -> value != null).collect(Collectors.toList());
        return (debts == null || debts.isEmpty()) ? Optional.empty() : Optional.of(debts);
    }
}
