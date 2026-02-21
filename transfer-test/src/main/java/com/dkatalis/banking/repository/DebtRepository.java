package com.dkatalis.banking.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.dkatalis.banking.model.Debt;

public interface DebtRepository extends CrudRepository<Debt, String> {

    Optional<Debt> findOne(String debtorAccountName, String crediturAccountName);

    Optional<Collection<Debt>> findByDebtor(String debtor);

    Optional<List<Debt>> findByCreditor(String accountName);
    
}
