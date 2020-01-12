package moneytransfer.service;

import moneytransfer.data.AccountDao;
import moneytransfer.model.Account;

import java.util.List;
import java.util.UUID;

public class AccountServiceImpl implements AccountService{

    AccountDao accountDao = new AccountDao();
    @Override
    public void createAccount(Account account) {

        accountDao.add(account);

    }

    @Override
    public Account getAccount(String id) {

        return accountDao.get(UUID.fromString(id));
    }

    @Override
    public List<Account> getAccounts() {
        return accountDao.getAll();
    }
}
