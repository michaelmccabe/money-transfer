package moneytransfer.service;

import moneytransfer.model.Account;
import moneytransfer.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    void createAccount(Account account);

    Account getAccount(String id);

    List<Account> getAccounts();
}
