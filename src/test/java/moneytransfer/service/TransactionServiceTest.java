package moneytransfer.service;

import moneytransfer.data.AccountDao;
import moneytransfer.data.TransactionDao;
import moneytransfer.exceptions.MoneyTransferException;
import moneytransfer.model.Account;
import moneytransfer.model.Transaction;
import moneytransfer.model.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private static final UUID USER_UUID = UUID.fromString("1c65bd00-0cee-4331-9025-77b3435d5f1f");
    private static final UUID ACCOUNT_UUID = UUID.fromString("01882618-2c7d-4530-9ada-0800d4a89abb");
    private static final UUID DESTINATION_ACCOUNT_UUID = UUID.fromString( "ff5bdd19-a996-3c19-8206-06c59e0b29e9");
    private static final BigDecimal DEPOSIT_AMOUNT = new BigDecimal(16.42);
    private static final BigDecimal WITHDRAW_AMOUNT = new BigDecimal(16.42);
    private static final BigDecimal TRANSFER_AMOUNT = new BigDecimal(6.42);
    private static final BigDecimal ACCOUNT_BALANCE = new BigDecimal(86.42);
    private static final BigDecimal SMALL_ACCOUNT_BALANCE = new BigDecimal(1.42);
    private static final BigDecimal DESTINATION_ACCOUNT_BALANCE = new BigDecimal(16.28);

    TransactionService underTest;

    @Mock
    AccountDao accountDao;

    @Mock
    TransactionDao transactionDao;

    private Account user_account = new Account(ACCOUNT_UUID, USER_UUID, ACCOUNT_BALANCE);
    private Account small_user_account = new Account(ACCOUNT_UUID, USER_UUID, SMALL_ACCOUNT_BALANCE);
    private Account destination_account = new Account(DESTINATION_ACCOUNT_UUID, USER_UUID, DESTINATION_ACCOUNT_BALANCE);


    @BeforeEach
    void setup(){
        underTest = new TransactionServiceImpl(accountDao, transactionDao);
    }

    @Test
    public void testAddFundsToAccountCallsTransactionAndAccountDaos() throws MoneyTransferException {
        when(accountDao.get(ACCOUNT_UUID)).thenReturn(user_account);
        underTest.addFundsToAccount(ACCOUNT_UUID, DEPOSIT_AMOUNT);
        verify(transactionDao).makeAccountTransaction(any(Transaction.class), any(Account.class));
        verify(accountDao).get(ACCOUNT_UUID);
    }

    @Test
    public void testWithdrawFundsFromAccountCallsTransactionAndAccountDaos() throws MoneyTransferException {
        when(accountDao.get(ACCOUNT_UUID)).thenReturn(user_account);
        underTest.withdrawFundsFromAccount(ACCOUNT_UUID, WITHDRAW_AMOUNT);
        verify(transactionDao).makeAccountTransaction(any(Transaction.class), any(Account.class));
        verify(accountDao).get(ACCOUNT_UUID);
    }

    @Test
    public void testWithdrawFundsThrowsExceptionIfNotEnoughFunds() {
        when(accountDao.get(ACCOUNT_UUID)).thenReturn(small_user_account);
        Exception exception =  Assertions.assertThrows(MoneyTransferException.class, () -> {
            underTest.withdrawFundsFromAccount(ACCOUNT_UUID, WITHDRAW_AMOUNT);
        });
        String expectedMessage = "Not enough funds available";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testTransferFundsCallsTransactionAndAccountDao()throws MoneyTransferException {

        when(accountDao.get(ACCOUNT_UUID)).thenReturn(user_account);
        when(accountDao.get(DESTINATION_ACCOUNT_UUID)).thenReturn(destination_account);
        underTest.transferFundsBetweenAccounts(ACCOUNT_UUID, DESTINATION_ACCOUNT_UUID, TRANSFER_AMOUNT);
        verify(transactionDao).makeAccountTransaction(any(Transaction.class), any(Account.class), any(Account.class));
        verify(accountDao).get(ACCOUNT_UUID);
        verify(accountDao).get(DESTINATION_ACCOUNT_UUID);
    }


    @Test
    public void testTransferFundsBetweenAccountsThrowsExceptionIfNotEnoughFunds() {
        when(accountDao.get(ACCOUNT_UUID)).thenReturn(small_user_account);
        Exception exception =  Assertions.assertThrows(MoneyTransferException.class, () -> {
            underTest.transferFundsBetweenAccounts(ACCOUNT_UUID, DESTINATION_ACCOUNT_UUID, WITHDRAW_AMOUNT);
        });
        String expectedMessage = "Not enough funds available";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }







}