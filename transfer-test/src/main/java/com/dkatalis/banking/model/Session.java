package com.dkatalis.banking.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public final class Session {

    private String accountName;

    private LocalDateTime openAt;

    private LocalDateTime closeAt;

    private Session(String accountName) {
        this.accountName = accountName;
        this.openAt = LocalDateTime.now();
    }

    public void close() {
        this.closeAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return this.closeAt == null;
    }

    public static Session of(Account account) {
        return new Session(account.getAccountName());
    }
}
