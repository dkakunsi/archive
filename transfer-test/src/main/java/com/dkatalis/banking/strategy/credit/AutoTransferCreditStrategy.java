package com.dkatalis.banking.strategy.credit;

import java.math.BigDecimal;

import com.dkatalis.banking.Logger;
import com.dkatalis.banking.model.TransferTransaction;
import com.dkatalis.banking.repository.BalanceRepository;
import com.dkatalis.banking.service.BalanceService;
import com.dkatalis.banking.service.DebtService;
import com.dkatalis.banking.service.TransactionService;

public class AutoTransferCreditStrategy implements CreditStrategy {

    private Logger logger;

    private TransactionService transactionService;

    private DebtService debtService;

    private BalanceService balanceService;

    private BalanceRepository balanceRepository;

    AutoTransferCreditStrategy(Logger logger, DebtService debtService, BalanceService balanceService, TransactionService transactionService) {
        this.logger = logger;
        this.transactionService = transactionService;
        this.debtService = debtService;
        this.balanceService = balanceService;
        this.balanceRepository = balanceService.getBalanceRepository();
    }

    @Override
    public void credit(String accountName, BigDecimal creditAmount) {
        var accountBalance = this.balanceService.getBalance(accountName);
        accountBalance.credit(creditAmount);
        var updatedBalance = this.balanceRepository.save(accountBalance);

        var optionalDebt = this.debtService.findDebt(accountName);
        optionalDebt.ifPresent(debt -> createAutoTransfer(debt.getDebtor(), debt.getCreditor(), updatedBalance.getAmount(), debt.getAmount()));
    }

    private void createAutoTransfer(String debtor, String creditor, BigDecimal balanceAmount, BigDecimal debtAmount) {
        try {
            if (isBalanceLessThanDebt(balanceAmount, debtAmount)) {
                this.transactionService.process(TransferTransaction.autoTransfer(debtor, creditor, balanceAmount));
                this.debtService.payDebt(debtor, creditor, balanceAmount);
            } else {
                this.transactionService.process(TransferTransaction.autoTransfer(debtor, creditor, debtAmount));
                this.debtService.payDebt(debtor, creditor, debtAmount);
            }
        } catch (Exception ex) {
            this.logger.add(ex.getMessage());
        }
    }

    private boolean isBalanceLessThanDebt(BigDecimal balanceAmount, BigDecimal debtAmount) {
        return balanceAmount.compareTo(debtAmount) < 0;        
    }
}
