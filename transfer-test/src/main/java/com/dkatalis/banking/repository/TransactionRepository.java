package com.dkatalis.banking.repository;

import java.util.List;
import java.util.Optional;

import com.dkatalis.banking.model.Transaction;
import com.dkatalis.banking.model.Transaction.TransactionType;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

    Optional<List<Transaction>> findBySendingAccountAndReceivingAccount(String sendingAccount, String receivingAccount, TransactionType transactionType);

}
