package com.dkatalis.banking;

import java.util.stream.Collectors;

import com.dkatalis.banking.model.TransferTransaction;
import com.dkatalis.banking.service.BalanceService;
import com.dkatalis.banking.service.DebtService;

public class SystemInfo {

    private Logger logger;

    private BalanceService balanceService;

    private DebtService debtService;

    public SystemInfo(Logger logger) {
        this.logger = logger;
    }

    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    public void setDebtService(DebtService debtService) {
        this.debtService = debtService;
    }

    public void addLoginInfo(Context context) {
        addInfo(String.format("Hello, %s!", context.getAccountName()));
        addBalanceInfo(context);
        addDebtInfo(context);
        addOwedDebtInfo(context);
    }

    public void addLogoutInfo(Context context) {
        addInfo(String.format("Goodbye, %s!", context.getAccountName()));
    }

    public void addpostDepositInfo(Context context) {
        addBalanceInfo(context);
        addDebtInfo(context);
        addOwedDebtInfo(context);
    }

    public void addPostWithdrawInfo(Context context) {
        addBalanceInfo(context);
        addDebtInfo(context);
        addOwedDebtInfo(context);
    }

    public void addPostTransferInfo(Context context, TransferTransaction transferTransaction) {
        if (!hasTransferInfo()) {
            addInfo(String.format("Transferred $%s to %s", transferTransaction.getAmount(), transferTransaction.getReceivingAccount()));
        } else {
            removeTransferInfo();
        }
        addBalanceInfo(context);
        addDebtInfo(context);
        addOwedDebtInfo(context);
    }

    private void removeTransferInfo() {
        this.logger.getMessages().removeIf(message -> message.startsWith("Transferred"));
    }

    private boolean hasTransferInfo() {
        var transferMessage = this.logger.getMessages().stream().filter(message -> message.startsWith("Transferred")).collect(Collectors.toList());
        return transferMessage != null && !transferMessage.isEmpty();
    }

    private void addBalanceInfo(Context context) {
        var balance = this.balanceService.getBalance(context.getAccountName());
        addInfo(String.format("Your balance is $%s", balance.getAmount()));
    }

    private void addDebtInfo(Context context) {
        var optionalDebts = this.debtService.findDebts(context.getAccountName());
        if (optionalDebts.isPresent()) {
            optionalDebts.get().forEach(debt -> addInfo(String.format("Owed $%s to %s", debt.getAmount(), debt.getCreditor())));
        }
    }

    private void addOwedDebtInfo(Context context) {
        var optionalOwedDebts = this.debtService.findOwedDebts(context.getAccountName());
        if (optionalOwedDebts.isPresent()) {
            optionalOwedDebts.get().forEach(debt -> addInfo(String.format("Owed $%s from %s", debt.getAmount(), debt.getDebtor())));
        }
    }

    public void addInfo(Context context, Exception ex) {
        addInfo(ex.getMessage());
        addBalanceInfo(context);
    }

    public void addUnauthenticatedProcessInfo() {
        addInfo("Currently you are logged out. Please login first!");
    }

    public void addAccountNotFoundInfo() {
        addInfo("The receiving account is not exists.");
    }

    private void addInfo(String message) {
        this.logger.add(message);
    }

    public void printInfo() {
        this.logger.print();
    }

    public void clear() {
        this.logger.clear();
    }
}
