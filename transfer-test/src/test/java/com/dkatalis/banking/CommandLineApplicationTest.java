package com.dkatalis.banking;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import com.dkatalis.banking.model.Account;
import com.dkatalis.banking.model.Balance;
import com.dkatalis.banking.model.Debt;
import com.dkatalis.banking.model.TransferTransaction;
import com.dkatalis.banking.model.Transaction.TransactionType;
import com.dkatalis.banking.repository.AccountRepository;
import com.dkatalis.banking.repository.BalanceRepository;
import com.dkatalis.banking.repository.DebtRepository;
import com.dkatalis.banking.repository.SessionRepository;
import com.dkatalis.banking.repository.TransactionRepository;
import com.dkatalis.banking.repository.inmemory.InMemoryAccountRepository;
import com.dkatalis.banking.repository.inmemory.InMemoryBalanceRepository;
import com.dkatalis.banking.repository.inmemory.InMemoryDebtRepository;
import com.dkatalis.banking.repository.inmemory.InMemorySessionRepository;
import com.dkatalis.banking.repository.inmemory.InMemoryTransactionRepository;
import com.dkatalis.banking.service.impl.AccountServiceImpl;
import com.dkatalis.banking.service.impl.BalanceServiceImpl;
import com.dkatalis.banking.service.impl.DebtServiceImpl;
import com.dkatalis.banking.service.impl.SessionServiceImpl;
import com.dkatalis.banking.service.impl.TransactionServiceImpl;
import com.dkatalis.banking.strategy.credit.CreditStrategyFactory;
import com.dkatalis.banking.strategy.credit.CreditStrategyFactory.CreditStrategyType;
import com.dkatalis.banking.strategy.transaction.TransactionStrategyFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandLineApplicationTest {

    private CommandLineApplication commandLineApplication;

    private SessionRepository sessionRepository;

    private AccountRepository accountRepository;

    private BalanceRepository balanceRepository;

    private TransactionRepository transactionRepository;

    private DebtRepository debtRepository;

    private Logger logger;

    @Before
    public void setup() {
        this.logger = new Logger();
        var infoPrinter = new SystemInfo(logger);

        var transactionStrategyFactory = new TransactionStrategyFactory(this.logger);
        var creditStrategyFactory = new CreditStrategyFactory(this.logger, CreditStrategyType.AUTO_TRANSFER);

        this.balanceRepository = new InMemoryBalanceRepository();
        var balanceService = new BalanceServiceImpl(this.balanceRepository, creditStrategyFactory);
        creditStrategyFactory.setBalanceService(balanceService);
        infoPrinter.setBalanceService(balanceService);

        this.accountRepository = new InMemoryAccountRepository();
        var accountService = new AccountServiceImpl(this.accountRepository, balanceService);

        this.sessionRepository = new InMemorySessionRepository();
        var sessionService = new SessionServiceImpl(this.sessionRepository, accountService);

        this.transactionRepository = new InMemoryTransactionRepository();
        var transactionService = new TransactionServiceImpl(transactionStrategyFactory, this.transactionRepository);
        transactionStrategyFactory.setBalanceService(balanceService);
        transactionStrategyFactory.setTransactionRepository(this.transactionRepository);

        this.debtRepository = new InMemoryDebtRepository();
        var debtService = new DebtServiceImpl(this.debtRepository);
        creditStrategyFactory.setDebtService(debtService);
        transactionStrategyFactory.setDebtService(debtService);
        infoPrinter.setDebtService(debtService);

        creditStrategyFactory.setTransactionService(transactionService);

        this.commandLineApplication = new CommandLineApplication(infoPrinter, sessionService, transactionService, accountService);
    }

    @After
    public void destroy() {
        this.logger.clear();
    }

    @Test
    public void testLogin_WhenAccountExists_ShouldSuccess() {
        // Add Alice account
        var username = "Alice";
        this.accountRepository.save(Account.of(username));

        var beforeLoginSessionCount = this.sessionRepository.count(username);
        this.commandLineApplication.login(username);
        var afterLoginSessionCount = this.sessionRepository.count(username);
        assertThat(afterLoginSessionCount, is(beforeLoginSessionCount + 1));

        // validate active session
        var activeSession = this.sessionRepository.getActiveSession(username);
        assertThat(activeSession.isPresent(), is(true));
        assertThat(activeSession.get().getAccountName(), is(username));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(2));
        assertThat(messages.get(0), is("Hello, Alice!"));
        assertThat(messages.get(1), is("Your balance is $0"));
    }

    @Test
    public void testLogin_WhenAccountNotExists_ShouldSuccess_AccountAndBalanceCreated() {
        var username = "Alice";
        var optionalAccount = this.accountRepository.findOne(username);
        assertThat(optionalAccount.isPresent(), is(false));

        var beforeLoginSessionCount = this.sessionRepository.count(username);
        this.commandLineApplication.login(username);
        var afterLoginSessionCount = this.sessionRepository.count(username);
        assertThat(afterLoginSessionCount, is(beforeLoginSessionCount + 1));

        // validate active session
        var activeSession = this.sessionRepository.getActiveSession(username);
        assertThat(activeSession.isPresent(), is(true));
        assertThat(activeSession.get().getAccountName(), is(username));

        // validate the new created account
        optionalAccount = this.accountRepository.findOne(username);
        assertThat(optionalAccount.isPresent(), is(true));

        var optionalBalance = this.balanceRepository.findOne(username);
        assertThat(optionalBalance.isPresent(), is(true));
        assertThat(optionalBalance.get().getAmount(), is(BigDecimal.valueOf(0)));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(2));
        assertThat(messages.get(0), is("Hello, Alice!"));
        assertThat(messages.get(1), is("Your balance is $0"));
    }

    @Test
    public void testLogin_WhenAlreadyLogin_ShouldSuccess() {
        // Add Alice account
        var username = "Alice";
        this.accountRepository.save(Account.of(username));

        var beforeLoginSessionCount = this.sessionRepository.count(username);
        this.commandLineApplication.login(username);
        var afterFirstLoginSessionCount = this.sessionRepository.count(username);
        assertThat(afterFirstLoginSessionCount, is(beforeLoginSessionCount + 1));
        this.logger.clear();

        // Second login should not create a new session data
        this.commandLineApplication.login(username);
        var afterSecondLoginSessionCount = this.sessionRepository.count(username);
        assertThat(afterSecondLoginSessionCount, is(afterFirstLoginSessionCount));

        var activeSession = this.sessionRepository.getActiveSession(username);
        assertThat(activeSession.isPresent(), is(true));
        assertThat(activeSession.get().getAccountName(), is(username));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(2));
        assertThat(messages.get(0), is("Hello, Alice!"));
        assertThat(messages.get(1), is("Your balance is $0"));
    }

    @Test
    public void testLogout_WhenAlreadyLoggedIn_ShouldSuccess() {
        var username = "Alice";
        this.commandLineApplication.login(username);

        var optionalActiveSession = this.sessionRepository.getActiveSession(username);
        assertThat(optionalActiveSession.isPresent(), is(true));

        this.logger.clear();

        this.commandLineApplication.logout();
        assertThat(this.commandLineApplication.hasActiveSession(), is(false));
        optionalActiveSession = this.sessionRepository.getActiveSession(username);
        assertThat(optionalActiveSession.isPresent(), is(false));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Goodbye, Alice!"));
    }

    @Test
    public void testLogout_WhenNotLoggedInYet_ShouldPrintMessage() {
        this.commandLineApplication.logout();

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Currently you are logged out. Please login first!"));
    }

    @Test
    public void testDeposit_WhenNoDebt_ShouldSuccess() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username));

        // Init session and context
        this.commandLineApplication.login(username);

        var optionalBalance = this.balanceRepository.findOne(username);
        assertThat(optionalBalance.get().getAmount(), is(BigDecimal.valueOf(0)));

        this.logger.clear();
        var amount = BigDecimal.valueOf(100);
        this.commandLineApplication.deposit(amount);

        optionalBalance = this.balanceRepository.findOne(username);
        assertThat(optionalBalance.get().getAmount(), is(BigDecimal.valueOf(100)));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Your balance is $100"));
    }

    @Test
    public void testDeposit_WhenNotLoggedIn_ShouldPrintMessage() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(100)));

        var withdrawAmount = BigDecimal.valueOf(50);
        this.commandLineApplication.deposit(withdrawAmount);

        var optionalBalance = this.balanceRepository.findOne(username);
        assertThat(optionalBalance.isPresent(), is(true));
        assertThat(optionalBalance.get().getAmount(), is(BigDecimal.valueOf(100)));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Currently you are logged out. Please login first!"));
    }

    @Test
    public void testDeposit_WhenAccountHasDebt_ShouldSuccess_AndAutoTransferToCreditor() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(0)));

        var crediturAccountName = "Bob";
        this.accountRepository.save(Account.of(crediturAccountName));
        this.balanceRepository.save(Balance.of(crediturAccountName, BigDecimal.valueOf(100)));

        // Init debt data
        var debtAmount = BigDecimal.valueOf(30);
        this.debtRepository.save(Debt.of(crediturAccountName, username, debtAmount));

        // Init session and context
        this.commandLineApplication.login(username);
        this.logger.clear();

        var depositAmount = BigDecimal.valueOf(50);
        this.commandLineApplication.deposit(depositAmount);

        var optionalDebtorBalance = this.balanceRepository.findOne(username);
        assertThat(optionalDebtorBalance.isPresent(), is(true));
        assertThat(optionalDebtorBalance.get().getAmount(), is(BigDecimal.valueOf(20)));

        var optionalCrediturBalance = this.balanceRepository.findOne(crediturAccountName);
        assertThat(optionalCrediturBalance.isPresent(), is(true));
        assertThat(optionalCrediturBalance.get().getAmount(), is(BigDecimal.valueOf(130)));

        var optionalDebt = this.debtRepository.findOne(username, crediturAccountName);
        assertThat(optionalDebt.isPresent(), is(true));
        assertThat(optionalDebt.get().isPaid(), is(true));

        var optionalTransactions = this.transactionRepository.findBySendingAccountAndReceivingAccount(username, crediturAccountName, TransactionType.AUTO_TRANSFER);
        assertThat(optionalTransactions.isPresent(), is(true));
        var transaction = optionalTransactions.get().get(0);
        assertTrue(transaction instanceof TransferTransaction);
        var transferTransaction = (TransferTransaction) transaction;
        assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(30)));
        assertThat(transferTransaction.getSendingAccount(), is(username));
        assertThat(transferTransaction.getReceivingAccount(), is(crediturAccountName));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(2));
        assertThat(messages.get(0), is("Transferred $30 to Bob"));
        assertThat(messages.get(1), is("Your balance is $20"));
    }

    @Test
    public void testDeposit_WhenAccountHasDebt_ShouldSuccess_AndAutoTransferToCreditor_DebtPaidNoCredit() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(0)));

        var crediturAccountName = "Bob";
        this.accountRepository.save(Account.of(crediturAccountName));
        this.balanceRepository.save(Balance.of(crediturAccountName, BigDecimal.valueOf(100)));

        // Init debt data
        var debtAmount = BigDecimal.valueOf(30);
        this.debtRepository.save(Debt.of(crediturAccountName, username, debtAmount));

        // Init session and context
        this.commandLineApplication.login(username);
        this.logger.clear();

        var depositAmount = BigDecimal.valueOf(30);
        this.commandLineApplication.deposit(depositAmount);

        var optionalDebtorBalance = this.balanceRepository.findOne(username);
        assertThat(optionalDebtorBalance.isPresent(), is(true));
        assertThat(optionalDebtorBalance.get().getAmount(), is(BigDecimal.valueOf(0)));

        var optionalCrediturBalance = this.balanceRepository.findOne(crediturAccountName);
        assertThat(optionalCrediturBalance.isPresent(), is(true));
        assertThat(optionalCrediturBalance.get().getAmount(), is(BigDecimal.valueOf(130)));

        var optionalDebt = this.debtRepository.findOne(username, crediturAccountName);
        assertThat(optionalDebt.isPresent(), is(true));
        assertThat(optionalDebt.get().isPaid(), is(true));

        var optionalTransactions = this.transactionRepository.findBySendingAccountAndReceivingAccount(username, crediturAccountName, TransactionType.AUTO_TRANSFER);
        assertThat(optionalTransactions.isPresent(), is(true));
        var transaction = optionalTransactions.get().get(0);
        assertTrue(transaction instanceof TransferTransaction);
        var transferTransaction = (TransferTransaction) transaction;
        assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(30)));
        assertThat(transferTransaction.getSendingAccount(), is(username));
        assertThat(transferTransaction.getReceivingAccount(), is(crediturAccountName));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(2));
        assertThat(messages.get(0), is("Transferred $30 to Bob"));
        assertThat(messages.get(1), is("Your balance is $0"));
    }

    @Test
    public void testDeposit_WhenAccountHasDebt_ShouldSuccess_AndAutoTransferToCreditor_DebtNotPaidNoCredit() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(0)));

        var crediturAccountName = "Bob";
        this.accountRepository.save(Account.of(crediturAccountName));
        this.balanceRepository.save(Balance.of(crediturAccountName, BigDecimal.valueOf(100)));

        // Init debt data
        var debtAmount = BigDecimal.valueOf(50);
        this.debtRepository.save(Debt.of(crediturAccountName, username, debtAmount));

        // Init session and context
        this.commandLineApplication.login(username);
        this.logger.clear();

        var depositAmount = BigDecimal.valueOf(30);
        this.commandLineApplication.deposit(depositAmount);

        var optionalDebtorBalance = this.balanceRepository.findOne(username);
        assertThat(optionalDebtorBalance.isPresent(), is(true));
        assertThat(optionalDebtorBalance.get().getAmount(), is(BigDecimal.valueOf(0)));

        var optionalCrediturBalance = this.balanceRepository.findOne(crediturAccountName);
        assertThat(optionalCrediturBalance.isPresent(), is(true));
        assertThat(optionalCrediturBalance.get().getAmount(), is(BigDecimal.valueOf(130)));

        var optionalDebt = this.debtRepository.findOne(username, crediturAccountName);
        assertThat(optionalDebt.isPresent(), is(true));
        assertThat(optionalDebt.get().isPaid(), is(false));
        assertThat(optionalDebt.get().getAmount(), is(BigDecimal.valueOf(20)));

        var optionalTransactions = this.transactionRepository.findBySendingAccountAndReceivingAccount(username, crediturAccountName, TransactionType.AUTO_TRANSFER);
        assertThat(optionalTransactions.isPresent(), is(true));
        var transaction = optionalTransactions.get().get(0);
        assertTrue(transaction instanceof TransferTransaction);
        var transferTransaction = (TransferTransaction) transaction;
        assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(30)));
        assertThat(transferTransaction.getSendingAccount(), is(username));
        assertThat(transferTransaction.getReceivingAccount(), is(crediturAccountName));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(3));
        assertThat(messages.get(0), is("Transferred $30 to Bob"));
        assertThat(messages.get(1), is("Your balance is $0"));
        assertThat(messages.get(2), is("Owed $20 to Bob"));
    }

    @Test
    public void testWithdraw_WhenBalanceEnough_ShouldSuccess() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(100)));

        // Init session and context
        this.commandLineApplication.login(username);
        this.logger.clear();

        var withdrawAmount = BigDecimal.valueOf(20);
        this.commandLineApplication.withdraw(withdrawAmount);

        var optionalBalance = this.balanceRepository.findOne(username);
        assertThat(optionalBalance.isPresent(), is(true));
        assertThat(optionalBalance.get().getAmount(), is(BigDecimal.valueOf(80)));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Your balance is $80"));
    }

    @Test
    public void testWithdraw_WhenBalanceNotEnough_ShouldFailAndPrintExceptionMessage() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(10)));

        // Init session and context
        this.commandLineApplication.login(username);
        this.logger.clear();

        var withdrawAmount = BigDecimal.valueOf(20);
        this.commandLineApplication.withdraw(withdrawAmount);

        var optionalBalance = this.balanceRepository.findOne(username);
        assertThat(optionalBalance.isPresent(), is(true));
        assertThat(optionalBalance.get().getAmount(), is(BigDecimal.valueOf(10)));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(2));
        assertThat(messages.get(0), is("Your balance is not sufficient."));
        assertThat(messages.get(1), is("Your balance is $10"));
    }

    @Test
    public void testWithdraw_WhenNotLoggedIn_ShouldPrintMessage() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(100)));

        var withdrawAmount = BigDecimal.valueOf(50);
        this.commandLineApplication.withdraw(withdrawAmount);

        var optionalBalance = this.balanceRepository.findOne(username);
        assertThat(optionalBalance.isPresent(), is(true));
        assertThat(optionalBalance.get().getAmount(), is(BigDecimal.valueOf(100)));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Currently you are logged out. Please login first!"));
    }

    @Test
    public void testTransfer_WhenAccountHasNoDebt_ShouldSuccess() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(100)));

        var receivingAccountName = "Bob";
        this.accountRepository.save(Account.of(receivingAccountName));
        this.balanceRepository.save(Balance.of(receivingAccountName, BigDecimal.valueOf(10)));

        // Init session and context
        this.commandLineApplication.login(username);
        this.logger.clear();

        var transferAmount = BigDecimal.valueOf(30);
        this.commandLineApplication.transfer(receivingAccountName, transferAmount);

        var optionalSendingBalance = this.balanceRepository.findOne(username);
        assertThat(optionalSendingBalance.isPresent(), is(true));
        assertThat(optionalSendingBalance.get().getAmount(), is(BigDecimal.valueOf(70)));

        var optionalReceivingBalance = this.balanceRepository.findOne(receivingAccountName);
        assertThat(optionalReceivingBalance.isPresent(), is(true));
        assertThat(optionalReceivingBalance.get().getAmount(), is(BigDecimal.valueOf(40)));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(2));
        assertThat(messages.get(0), is("Transferred $30 to Bob"));
        assertThat(messages.get(1), is("Your balance is $70"));
    }

    @Test
    public void testTransfer_WhenNotLoggedIn_ShouldPrintMessage() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(100)));

        var receivingAccountName = "Bob";
        this.accountRepository.save(Account.of(receivingAccountName));
        this.balanceRepository.save(Balance.of(receivingAccountName, BigDecimal.valueOf(10)));

        var transferAmount = BigDecimal.valueOf(50);
        this.commandLineApplication.transfer(receivingAccountName, transferAmount);

        var optionalSendingBalance = this.balanceRepository.findOne(username);
        assertThat(optionalSendingBalance.isPresent(), is(true));
        assertThat(optionalSendingBalance.get().getAmount(), is(BigDecimal.valueOf(100)));

        var optionalReceivingBalance = this.balanceRepository.findOne(receivingAccountName);
        assertThat(optionalReceivingBalance.isPresent(), is(true));
        assertThat(optionalReceivingBalance.get().getAmount(), is(BigDecimal.valueOf(10)));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Currently you are logged out. Please login first!"));
    }

    @Test
    public void testTransfer_WhenReceivingAccountNotExists_ShouldFailAndPrintExceptionMessage() {
        // Init data
        var username = "Alice";
        this.accountRepository.save(Account.of(username));
        this.balanceRepository.save(Balance.of(username, BigDecimal.valueOf(100)));

        var receivingAccountName = "Bob";

        // Init session and context
        this.commandLineApplication.login(username);
        this.logger.clear();

        var transferAmount = BigDecimal.valueOf(30);
        this.commandLineApplication.transfer(receivingAccountName, transferAmount);

        var optionalSendingBalance = this.balanceRepository.findOne(username);
        assertThat(optionalSendingBalance.isPresent(), is(true));
        assertThat(optionalSendingBalance.get().getAmount(), is(BigDecimal.valueOf(100)));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("The receiving account is not exists."));
    }

    @Test
    public void testTransfer_WhenAccountHashDebt_ShouldSuccessAndAutoTransferToCreditor() {
        // Init data
        var aliceUsername = "Alice";
        this.accountRepository.save(Account.of(aliceUsername));
        this.balanceRepository.save(Balance.of(aliceUsername, BigDecimal.valueOf(0)));

        var bobUsername = "Bob";
        this.accountRepository.save(Account.of(bobUsername));
        this.balanceRepository.save(Balance.of(bobUsername, BigDecimal.valueOf(100)));

        // Init debt data
        var debtAmount = BigDecimal.valueOf(30);
        this.debtRepository.save(Debt.of(bobUsername, aliceUsername, debtAmount));

        // Init session and context
        this.commandLineApplication.login(bobUsername);
        this.logger.clear();

        var transferAmount = BigDecimal.valueOf(20);
        this.commandLineApplication.transfer(aliceUsername, transferAmount);

        // validate result
        var optionalDebtorBalance = this.balanceRepository.findOne(aliceUsername);
        assertThat(optionalDebtorBalance.isPresent(), is(true));
        assertThat(optionalDebtorBalance.get().getAmount(), is(BigDecimal.ZERO));

        var optionalCrediturBalance = this.balanceRepository.findOne(bobUsername);
        assertThat(optionalCrediturBalance.isPresent(), is(true));
        assertThat(optionalCrediturBalance.get().getAmount(), is(BigDecimal.valueOf(100)));

        var optionalDebt = this.debtRepository.findOne(aliceUsername, bobUsername);
        assertThat(optionalDebt.isPresent(), is(true));
        assertThat(optionalDebt.get().isPaid(), is(false));
        assertThat(optionalDebt.get().getAmount(), is(BigDecimal.valueOf(10)));

        var optionalTransactions = this.transactionRepository.findBySendingAccountAndReceivingAccount(aliceUsername, bobUsername, TransactionType.AUTO_TRANSFER);
        assertThat(optionalTransactions.isPresent(), is(true));
        var transaction = optionalTransactions.get().get(0);
        assertTrue(transaction instanceof TransferTransaction);
        var transferTransaction = (TransferTransaction) transaction;
        if (transferTransaction.getTransactionType().equals(TransactionType.TRANSFER)) {
            assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(30)));
            assertThat(transferTransaction.getSendingAccount(), is(bobUsername));
            assertThat(transferTransaction.getReceivingAccount(), is(aliceUsername));
        } else {
            // Auto transfer
            assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(20)));
            assertThat(transferTransaction.getSendingAccount(), is(aliceUsername));
            assertThat(transferTransaction.getReceivingAccount(), is(bobUsername));
        }

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(2));
        assertThat(messages.get(0), is("Your balance is $100"));
        assertThat(messages.get(1), is("Owed $10 from Alice"));
    }

    @Test
    public void testTransfer_WhenAccountHashDebt_ShouldSuccessAndAutoTransferToCreditor_DebtPaidDebtorBalanceIsZero() {
        // Init data
        var aliceUsername = "Alice";
        this.accountRepository.save(Account.of(aliceUsername));
        this.balanceRepository.save(Balance.of(aliceUsername, BigDecimal.valueOf(0)));

        var bobUsername = "Bob";
        this.accountRepository.save(Account.of(bobUsername));
        this.balanceRepository.save(Balance.of(bobUsername, BigDecimal.valueOf(100)));

        // Init debt data
        var debtAmount = BigDecimal.valueOf(30);
        this.debtRepository.save(Debt.of(bobUsername, aliceUsername, debtAmount));

        // Init session and context
        this.commandLineApplication.login(bobUsername);
        this.logger.clear();

        var transferAmount = BigDecimal.valueOf(30);
        this.commandLineApplication.transfer(aliceUsername, transferAmount);

        // validate result
        var optionalDebtorBalance = this.balanceRepository.findOne(aliceUsername);
        assertThat(optionalDebtorBalance.isPresent(), is(true));
        assertThat(optionalDebtorBalance.get().getAmount(), is(BigDecimal.ZERO));

        var optionalCrediturBalance = this.balanceRepository.findOne(bobUsername);
        assertThat(optionalCrediturBalance.isPresent(), is(true));
        assertThat(optionalCrediturBalance.get().getAmount(), is(BigDecimal.valueOf(100)));

        var optionalDebt = this.debtRepository.findOne(aliceUsername, bobUsername);
        assertThat(optionalDebt.isPresent(), is(true));
        assertThat(optionalDebt.get().isPaid(), is(true));

        var optionalTransactions = this.transactionRepository.findBySendingAccountAndReceivingAccount(aliceUsername, bobUsername, TransactionType.AUTO_TRANSFER);
        assertThat(optionalTransactions.isPresent(), is(true));
        var transaction = optionalTransactions.get().get(0);
        assertTrue(transaction instanceof TransferTransaction);
        var transferTransaction = (TransferTransaction) transaction;
        if (transferTransaction.getTransactionType().equals(TransactionType.TRANSFER)) {
            assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(30)));
            assertThat(transferTransaction.getSendingAccount(), is(bobUsername));
            assertThat(transferTransaction.getReceivingAccount(), is(aliceUsername));
        } else {
            // Auto transfer
            assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(30)));
            assertThat(transferTransaction.getSendingAccount(), is(aliceUsername));
            assertThat(transferTransaction.getReceivingAccount(), is(bobUsername));
        }

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Your balance is $100"));
    }

    @Test
    public void testTransfer_WhenAccountHashDebt_ShouldSuccessAndAutoTransferToCreditor_DebtPaidDebtorBalanceIsNotZero() {
        // Init data
        var aliceUsername = "Alice";
        this.accountRepository.save(Account.of(aliceUsername));
        this.balanceRepository.save(Balance.of(aliceUsername, BigDecimal.valueOf(0)));

        var bobUsername = "Bob";
        this.accountRepository.save(Account.of(bobUsername));
        this.balanceRepository.save(Balance.of(bobUsername, BigDecimal.valueOf(100)));

        // Init debt data
        var debtAmount = BigDecimal.valueOf(30);
        this.debtRepository.save(Debt.of(bobUsername, aliceUsername, debtAmount));

        // Init session and context
        this.commandLineApplication.login(bobUsername);
        this.logger.clear();

        var transferAmount = BigDecimal.valueOf(50);
        this.commandLineApplication.transfer(aliceUsername, transferAmount);

        // validate result
        var optionalDebtorBalance = this.balanceRepository.findOne(aliceUsername);
        assertThat(optionalDebtorBalance.isPresent(), is(true));
        assertThat(optionalDebtorBalance.get().getAmount(), is(BigDecimal.valueOf(20)));

        var optionalCrediturBalance = this.balanceRepository.findOne(bobUsername);
        assertThat(optionalCrediturBalance.isPresent(), is(true));
        assertThat(optionalCrediturBalance.get().getAmount(), is(BigDecimal.valueOf(80)));

        var optionalDebt = this.debtRepository.findOne(aliceUsername, bobUsername);
        assertThat(optionalDebt.isPresent(), is(true));
        assertThat(optionalDebt.get().isPaid(), is(true));

        var optionalTransactions = this.transactionRepository.findBySendingAccountAndReceivingAccount(aliceUsername, bobUsername, TransactionType.AUTO_TRANSFER);
        assertThat(optionalTransactions.isPresent(), is(true));
        var transaction = optionalTransactions.get().get(0);
        assertTrue(transaction instanceof TransferTransaction);
        var transferTransaction = (TransferTransaction) transaction;
        if (transferTransaction.getTransactionType().equals(TransactionType.TRANSFER)) {
            assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(50)));
            assertThat(transferTransaction.getSendingAccount(), is(bobUsername));
            assertThat(transferTransaction.getReceivingAccount(), is(aliceUsername));
        } else {
            // Auto transfer
            assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(30)));
            assertThat(transferTransaction.getSendingAccount(), is(aliceUsername));
            assertThat(transferTransaction.getReceivingAccount(), is(bobUsername));
        }

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Your balance is $80"));
    }

    @Test
    public void testTransfer_WhenBalanceNotSufficient_TransferRestBalanceAndCreateDebt() {
        // Init data
        var aliceUsername = "Alice";
        this.accountRepository.save(Account.of(aliceUsername));
        this.balanceRepository.save(Balance.of(aliceUsername, BigDecimal.valueOf(30)));

        var bobUsername = "Bob";
        this.accountRepository.save(Account.of(bobUsername));
        this.balanceRepository.save(Balance.of(bobUsername, BigDecimal.valueOf(100)));

        // Init session and context
        this.commandLineApplication.login(aliceUsername);
        this.logger.clear();

        var transferAmount = BigDecimal.valueOf(50);
        this.commandLineApplication.transfer(bobUsername, transferAmount);

        // validate result
        var optionalDebtorBalance = this.balanceRepository.findOne(aliceUsername);
        assertThat(optionalDebtorBalance.isPresent(), is(true));
        assertThat(optionalDebtorBalance.get().getAmount(), is(BigDecimal.valueOf(0)));

        var optionalCrediturBalance = this.balanceRepository.findOne(bobUsername);
        assertThat(optionalCrediturBalance.isPresent(), is(true));
        // assertThat(optionalCrediturBalance.get().getAmount(), is(BigDecimal.valueOf(130)));

        var optionalDebt = this.debtRepository.findOne(aliceUsername, bobUsername);
        assertThat(optionalDebt.isPresent(), is(true));
        assertThat(optionalDebt.get().isPaid(), is(false));
        assertThat(optionalDebt.get().getAmount(), is(BigDecimal.valueOf(20)));

        var optionalTransactions = this.transactionRepository.findBySendingAccountAndReceivingAccount(aliceUsername, bobUsername, TransactionType.TRANSFER);
        assertThat(optionalTransactions.isPresent(), is(true));
        var transaction = optionalTransactions.get().get(0);
        assertTrue(transaction instanceof TransferTransaction);
        var transferTransaction = (TransferTransaction) transaction;
        assertThat(transferTransaction.getAmount(), is(BigDecimal.valueOf(30)));
        assertThat(transferTransaction.getSendingAccount(), is(aliceUsername));
        assertThat(transferTransaction.getReceivingAccount(), is(bobUsername));

        // assert messages
        var messages = this.logger.getMessages();
        assertThat(messages.size(), is(3));
        assertThat(messages.get(0), is("Transferred $30 to Bob"));
        assertThat(messages.get(1), is("Your balance is $0"));
        assertThat(messages.get(2), is("Owed $20 to Bob"));
    }
}