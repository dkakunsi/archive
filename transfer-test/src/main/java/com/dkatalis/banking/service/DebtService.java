package com.dkatalis.banking.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.dkatalis.banking.model.Debt;

public interface DebtService {

    Optional<Debt> findDebt(String accountName);

    Debt createDebt(String creditor, String debtor, BigDecimal amountOfDebt);

    void payDebt(String debtor, String creditor, BigDecimal creditAmount);

    Optional<List<Debt>> findDebts(String accountName);

    Optional<List<Debt>> findOwedDebts(String accountName);
    
}
