package com.dkatalis.banking.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Account {

    private String accountName;

    private LocalDateTime createdAt;

    private Account(String accountName) {
        this.accountName = accountName;
        this.createdAt = LocalDateTime.now();
    }

    public static Account of(String username) {
        return new Account(username);
    }
}
