package com.dkatalis.banking.repository.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dkatalis.banking.model.Transaction;
import com.dkatalis.banking.model.TransferTransaction;
import com.dkatalis.banking.model.Transaction.TransactionType;
import com.dkatalis.banking.repository.TransactionRepository;

public class InMemoryTransactionRepository implements TransactionRepository {
    
    private Map<String, List<Transaction>> map;

    public InMemoryTransactionRepository() {
        this.map = new HashMap<>();
    }

    @Override
    public Transaction save(Transaction transaction) {
        var accountTransactions = this.map.get(transaction.getAccountName());
        if (accountTransactions == null) {
            accountTransactions = new ArrayList<>();
        }
        accountTransactions.add(transaction);
        this.map.put(transaction.getAccountName(), accountTransactions);

        return transaction;
    }

    @Override
    public Optional<List<Transaction>> findBySendingAccountAndReceivingAccount(String sendingAccount, String receivingAccount, TransactionType transactionType) {
        var transactions = this.map.get(sendingAccount);
        var filteredTransactions = transactions.stream()
                .filter(tx -> transactionType.equals(tx.getTransactionType()))
                .filter(tx -> {
                    var transferTx = (TransferTransaction) tx;
                    return transferTx.getReceivingAccount().equals(receivingAccount);
                }).collect(Collectors.toList());
        
        return Optional.of(filteredTransactions);
    }
}
