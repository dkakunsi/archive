package com.dkatalis.banking.strategy.credit;

import java.math.BigDecimal;

public interface CreditStrategy {

    void credit(String accountName, BigDecimal amount);

}
