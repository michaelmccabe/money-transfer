package moneytransfer.service;

import moneytransfer.data.AccountDao;
import moneytransfer.data.TransactionDao;
import moneytransfer.exceptions.MoneyTransferException;
import moneytransfer.model.Account;
import moneytransfer.model.Transaction;
import moneytransfer.model.Type;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {

    AccountDao accountDao;

    TransactionDao transactionDao;

    public TransactionServiceImpl(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public void addFundsToAccount(UUID accountUuid, BigDecimal amount) throws MoneyTransferException {

        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new MoneyTransferException("value being added must be greater than zero");
        }

        Account account = accountDao.get(accountUuid);
        Account newAccountDetails = new Account(accountUuid, account.getUserUuid(), account.getBalance().add(amount));

        Transaction transaction =
                new Transaction(UUID.randomUUID(), Type.DEPOSIT, null, accountUuid, "", amount, System.currentTimeMillis());
        transactionDao.makeAccountTransaction(transaction, newAccountDetails);
    }

    @Override
    public void withdrawFundsFromAccount(UUID accountUuid, BigDecimal amount) throws MoneyTransferException  {

        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new MoneyTransferException("Value withdrawn must be greater than zero");
        }

        Account account = accountDao.get(accountUuid);
        BigDecimal newAmount = account.getBalance().subtract(amount);

        if (newAmount.compareTo(BigDecimal.ZERO) < 0){
            throw new MoneyTransferException("Not enough funds available");
        }

        Account newAccountDetails = new Account(accountUuid, account.getUserUuid(), newAmount);

        Transaction transaction =
                new Transaction(UUID.randomUUID(), Type.WITHDRAWAL, null, accountUuid, "", amount, System.currentTimeMillis());
        transactionDao.makeAccountTransaction(transaction, newAccountDetails);
    }

    @Override
    public void transferFundsBetweenAccounts(UUID sourceAccountUuid, UUID destinationAccountUuid, BigDecimal amount) throws MoneyTransferException {

        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new MoneyTransferException("Value withdrawn must be greater than zero");
        }

        Account sourceAccount = accountDao.get(sourceAccountUuid);
        BigDecimal newSourceAmount = sourceAccount.getBalance().subtract(amount);

        if (newSourceAmount.compareTo(BigDecimal.ZERO) < 0){
            throw new MoneyTransferException("Not enough funds available");
        }

        Account newSourceAccount = new Account(sourceAccountUuid, sourceAccount.getUserUuid(), newSourceAmount);
        Account destAccount = accountDao.get(destinationAccountUuid);
        Account newDestAccount = new Account(destinationAccountUuid, destAccount.getUserUuid(), destAccount.getBalance().add(amount));

        Transaction transaction =
                new Transaction(null, Type.TRANSFER, sourceAccountUuid, destinationAccountUuid, "", amount, System.currentTimeMillis());

        transactionDao.makeAccountTransaction(transaction, newSourceAccount, newDestAccount);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDao.getAll();
    }

}
