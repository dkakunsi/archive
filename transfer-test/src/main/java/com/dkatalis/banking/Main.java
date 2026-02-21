package com.dkatalis.banking;

import java.math.BigDecimal;
import java.util.Scanner;

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

public class Main {

    public static void main(String[] args) {
        printWelcome();
        readInput();
    }

    private static void printWelcome() {
        System.out.println("Welcome!");
    }

    private static void readInput() {
        var application = initApplication();
        var command = "";

        var scanner = new Scanner(System.in);
        while (!"exit".equals(command)) {
            System.out.print("$ ");
            var commandLine = scanner.nextLine();
            try {
                command = dispatchCommand(application, commandLine);
            } catch (Exception ex) {
                System.out.println("Invalid command");
            }
        }
        scanner.close();
    }

    private static String dispatchCommand(CommandLineApplication application, String commandLine) {
        var systemInfo = application.getSystemInfo();

        var elements = commandLine.split(" ");
        switch (elements[0]) {
            case "login":
                systemInfo.clear();
                application.login(elements[1]);
                systemInfo.printInfo();
                break;
            case "logout":
                systemInfo.clear();
                application.logout();
                systemInfo.printInfo();
                break;
            case "deposit":
                systemInfo.clear();
                application.deposit(new BigDecimal(elements[1]));
                systemInfo.printInfo();
                break;
            case "withdraw":
                systemInfo.clear();
                application.withdraw(new BigDecimal(elements[1]));
                systemInfo.printInfo();
                break;
            case "transfer":
                systemInfo.clear();
                application.transfer(elements[1], new BigDecimal(elements[2]));
                systemInfo.printInfo();
                break;
            case "exit":
                break;
            default:
                System.out.println(String.format("Command not supported: %s", elements[0]));
                break;
        }

        return elements[0];
    }

    private static CommandLineApplication initApplication() {
        var logger = new Logger();
        var systemInfo = new SystemInfo(logger);

        var creditStrategyFactory = new CreditStrategyFactory(logger, CreditStrategyType.AUTO_TRANSFER);
        var transactionStrategyFactory = new TransactionStrategyFactory(logger);

        var balanceRepository = new InMemoryBalanceRepository();
        var balanceService = new BalanceServiceImpl(balanceRepository, creditStrategyFactory);
        creditStrategyFactory.setBalanceService(balanceService);
        transactionStrategyFactory.setBalanceService(balanceService);
        systemInfo.setBalanceService(balanceService);

        var accountRepository = new InMemoryAccountRepository();
        var accountService = new AccountServiceImpl(accountRepository, balanceService);

        var sessionRepository = new InMemorySessionRepository();
        var sessionService = new SessionServiceImpl(sessionRepository, accountService);

        var transactionRepository = new InMemoryTransactionRepository();
        var transactionService = new TransactionServiceImpl(transactionStrategyFactory, transactionRepository);
        creditStrategyFactory.setTransactionService(transactionService);
        transactionStrategyFactory.setTransactionRepository(transactionRepository);

        var debtRepository = new InMemoryDebtRepository();
        var debtService = new DebtServiceImpl(debtRepository);
        creditStrategyFactory.setDebtService(debtService);
        transactionStrategyFactory.setDebtService(debtService);
        systemInfo.setDebtService(debtService);

        return new CommandLineApplication(systemInfo, sessionService, transactionService, accountService);
    }
}
