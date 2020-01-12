package moneytransfer.data;

import moneytransfer.model.Account;
import moneytransfer.model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountDao extends H2Database implements Dao<Account> {

    private static final String UPDATE_ACCOUNTS_SQL = "update money_transfer.accounts set balance = ? where uuid = ?";

    private static final String INSERT_ACCOUNTS_SQL = "INSERT INTO money_transfer.accounts" +
            "  (uuid, useruuid, balance) VALUES " +
            " (?, ?, ?);";

    private static final String GET_ACCOUNT = "select uuid,useruuid,balance from money_transfer.accounts where uuid =?";

    private static final String GET_ALL_ACCOUNTS = "select uuid,useruuid,balance from money_transfer.accounts";

    @Override
    public Account get(UUID uuid) {
        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT)) {
            preparedStatement.setString(1, uuid.toString());
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String userUuid = rs.getString("useruuid");
                BigDecimal balance = rs.getBigDecimal("balance");
                return new Account(uuid, UUID.fromString(userUuid), balance);
            }
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }

        return null;
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ACCOUNTS)) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String userUuid = rs.getString("useruuid");
                String uuid = rs.getString("uuid");
                BigDecimal balance = rs.getBigDecimal("balance");
                accounts.add(new Account(UUID.fromString(uuid), UUID.fromString(userUuid), balance));
            }

        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }

        return accounts;
    }

    @Override
    public Account add(Account account) {

        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNTS_SQL)) {
            preparedStatement.setString(1, account.getUuid().toString());
            preparedStatement.setString(2, account.getUserUuid().toString());
            preparedStatement.setBigDecimal(3, account.getBalance());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();

            return account;
        } catch (SQLException e) {
            // print SQL exception information
            H2JDBCUtils.printSQLException(e);
        }

        return account;
    }

    @Override
    public Account update(Account account) {
        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNTS_SQL)) {
            preparedStatement.setBigDecimal(1, account.getBalance());
            preparedStatement.setString(2, account.getUuid().toString());
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            return account;
        } catch (SQLException e) {
            // print SQL exception information
            H2JDBCUtils.printSQLException(e);
        }
        return account;
    }

    @Override
    public void delete(Account account) {

    }


}
