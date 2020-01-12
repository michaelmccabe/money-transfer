package moneytransfer.service;

import moneytransfer.exceptions.MoneyTransferException;
import moneytransfer.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    void addFundsToAccount(UUID accountUuid, BigDecimal amount) throws MoneyTransferException;

    void withdrawFundsFromAccount(UUID accountUuid, BigDecimal amount) throws MoneyTransferException;

    void transferFundsBetweenAccounts(UUID sourceAccountUuid, UUID destinationAccountUuid, BigDecimal amount) throws MoneyTransferException;

    List<Transaction> getAllTransactions();

}
