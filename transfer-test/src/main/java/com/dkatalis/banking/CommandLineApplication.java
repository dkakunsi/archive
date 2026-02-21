package com.dkatalis.banking;

import java.math.BigDecimal;

import com.dkatalis.banking.exception.InsufficientBalanceException;
import com.dkatalis.banking.exception.UnsupportedTransferType;
import com.dkatalis.banking.model.Transaction;
import com.dkatalis.banking.model.TransferTransaction;
import com.dkatalis.banking.model.Transaction.TransactionType;
import com.dkatalis.banking.service.AccountService;
import com.dkatalis.banking.service.SessionService;
import com.dkatalis.banking.service.TransactionService;

public final class CommandLineApplication {

    private SessionService sessionService;

    private TransactionService transactionService;

    private AccountService accountService;

    private Context context;

    private SystemInfo systemInfo;

    public CommandLineApplication(SystemInfo systemInfo, SessionService sessionService,
            TransactionService transactionService, AccountService accountService) {
        this.sessionService = sessionService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.systemInfo = systemInfo;
    }

    public SystemInfo getSystemInfo() {
        return this.systemInfo;
    }

    public void login(String username) {
        if (!isLoggedIn(username)) {
            this.context = this.sessionService.login(username);
        }

        this.systemInfo.addLoginInfo(this.context);
    }

    private boolean isLoggedIn(String username) {
        return context != null && context.getAccountName().equals(username);
    }

    public void logout() {
        if (!hasActiveSession()) {
            this.systemInfo.addUnauthenticatedProcessInfo();
            return;
        }

        this.sessionService.logout(context.getAccountName());
        this.systemInfo.addLogoutInfo(this.context);
        this.context = null;
    }

    public boolean hasActiveSession() {
        return this.context != null;
    }

    public void deposit(BigDecimal amount) {
        if (!hasActiveSession()) {
            this.systemInfo.addUnauthenticatedProcessInfo();
            // Maybe need to fail loudly via Exception
            return;
        }
        try {
            var deposit = Transaction.of(this.context.getAccountName(), amount, TransactionType.DEPOSIT);
            this.transactionService.process(deposit);
            this.systemInfo.addpostDepositInfo(this.context);
        } catch (UnsupportedTransferType | InsufficientBalanceException ex) {
            this.systemInfo.addInfo(this.context, ex);
        }
    }

    public void withdraw(BigDecimal amount) {
        if (!hasActiveSession()) {
            this.systemInfo.addUnauthenticatedProcessInfo();
            // Maybe need to fail loudly via Exception
            return;
        }

        try {
            var withdrawal = Transaction.of(this.context.getAccountName(), amount, TransactionType.WITHDRAW);
            this.transactionService.process(withdrawal);
            this.systemInfo.addPostWithdrawInfo(this.context);
        } catch (UnsupportedTransferType | InsufficientBalanceException ex) {
            this.systemInfo.addInfo(this.context, ex);
        }
    }

    public void transfer(String receivingAccount, BigDecimal amount) {
        if (!hasActiveSession()) {
            this.systemInfo.addUnauthenticatedProcessInfo();
            // Maybe need to fail loudly via Exception
            return;
        }

        if (!isReceivingAccountExists(receivingAccount)) {
            this.systemInfo.addAccountNotFoundInfo();
            // Maybe need to fail loudly via Exception
            return;
        }

        try {
            var transfer = TransferTransaction.transfer(this.context.getAccountName(), receivingAccount, amount);
            var transaction = this.transactionService.process(transfer);
            this.systemInfo.addPostTransferInfo(this.context, (TransferTransaction) transaction);
        } catch (UnsupportedTransferType | InsufficientBalanceException ex) {
            this.systemInfo.addInfo(this.context, ex);
        }
    }

    private boolean isReceivingAccountExists(String receivingAccount) {
        return this.accountService.findOne(receivingAccount).isPresent();
    }
}
