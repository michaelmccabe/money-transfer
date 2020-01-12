package moneytransfer.data;

import moneytransfer.model.Account;
import moneytransfer.model.Transaction;
import moneytransfer.model.Type;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionDao extends H2Database {

    private static final String INSERT_TRANSACTIONS_SQL = "INSERT INTO money_transfer.transactions" +
            "  (uuid, type, accountuuid, details, amount, inserted_time) VALUES " +
            " (?, ?, ?, ?, ?, ?);";

    private static final String UPDATE_ACCOUNTS_SQL = "update money_transfer.accounts set balance = ? where uuid = ?";

    private static final BigDecimal MINUS_ONE = new BigDecimal(-1);

    private static final String GET_ALL_TRANSACTIONSS = "select uuid, type, accountuuid, details, amount, inserted_time from money_transfer.transactions";




    public List<Transaction> getAll() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_TRANSACTIONSS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String accountuuid = rs.getString("accountuuid");
                String uuid = rs.getString("uuid");
                String type = rs.getString("type");
                String details = rs.getString("details");
                BigDecimal amount = rs.getBigDecimal("amount");
                long inserted_time = rs.getLong("inserted_time");

                transactions.add(new Transaction(UUID.fromString(uuid), Type.valueOf(type),
                        null, UUID.fromString(accountuuid), details, amount, inserted_time));
            }

        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }

        return transactions;
    }


    public void makeAccountTransaction(Transaction transaction, Account account) {

        Connection connection = H2JDBCUtils.getConnection();

        try {
            connection.setAutoCommit(false); // makes entire change transactional

            PreparedStatement insertTransactionPS = connection.prepareStatement(INSERT_TRANSACTIONS_SQL);
            insertTransactionPS.setString(1, transaction.getUuid().toString());
            insertTransactionPS.setString(2, transaction.getType().toString());
            insertTransactionPS.setString(3, transaction.getAccountUuid().toString());
            insertTransactionPS.setString(4, transaction.getDetails());

            if(transaction.getType().equals(Type.WITHDRAWAL)){
                insertTransactionPS.setBigDecimal(5, transaction.getAmount().multiply(MINUS_ONE));
            }
            else {
                insertTransactionPS.setBigDecimal(5, transaction.getAmount());
            }

            insertTransactionPS.setLong(6, transaction.getTimeStamp());
            System.out.println(insertTransactionPS);
            insertTransactionPS.executeUpdate();

            PreparedStatement updateAccountPreparedStatement = connection.prepareStatement(UPDATE_ACCOUNTS_SQL);
            updateAccountPreparedStatement.setBigDecimal(1, account.getBalance());
            updateAccountPreparedStatement.setString(2, account.getUuid().toString());
            System.out.println(updateAccountPreparedStatement);
            updateAccountPreparedStatement.executeUpdate();
            connection.commit();
            return;
        } catch (SQLException e) {
            // print SQL exception information
            H2JDBCUtils.printSQLException(e);
        }
    }

    public void makeAccountTransaction(Transaction transaction, Account sourceAccount, Account destinationAccount) {

        Connection connection = H2JDBCUtils.getConnection();

        try {
            connection.setAutoCommit(false); // makes entire change transactional

            PreparedStatement sourceAccountPS = connection.prepareStatement(INSERT_TRANSACTIONS_SQL);
            sourceAccountPS.setString(1, UUID.randomUUID().toString().toString());
            sourceAccountPS.setString(2, Type.TRANSFER.toString());
            sourceAccountPS.setString(3, sourceAccount.getUuid().toString());
            sourceAccountPS.setString(4, transaction.getDetails());
            sourceAccountPS.setBigDecimal(5, transaction.getAmount());
            sourceAccountPS.setLong(6, transaction.getTimeStamp());
            sourceAccountPS.executeUpdate();

            PreparedStatement destAccountPS = connection.prepareStatement(INSERT_TRANSACTIONS_SQL);
            destAccountPS.setString(1, UUID.randomUUID().toString().toString());
            destAccountPS.setString(2, Type.TRANSFER.toString());
            destAccountPS.setString(3, destinationAccount.getUuid().toString());
            destAccountPS.setString(4, transaction.getDetails());
            destAccountPS.setBigDecimal(5, transaction.getAmount().multiply(MINUS_ONE));
            destAccountPS.setLong(6, transaction.getTimeStamp());
            destAccountPS.executeUpdate();

            PreparedStatement updateSourceAccountPreparedStatement = connection.prepareStatement(UPDATE_ACCOUNTS_SQL);
            updateSourceAccountPreparedStatement.setBigDecimal(1, sourceAccount.getBalance());
            updateSourceAccountPreparedStatement.setString(2, sourceAccount.getUuid().toString());
            updateSourceAccountPreparedStatement.executeUpdate();

            PreparedStatement updateDestinationAccountPreparedStatement = connection.prepareStatement(UPDATE_ACCOUNTS_SQL);
            updateDestinationAccountPreparedStatement.setBigDecimal(1, destinationAccount.getBalance());
            updateDestinationAccountPreparedStatement.setString(2, destinationAccount.getUuid().toString());
            updateDestinationAccountPreparedStatement.executeUpdate();

            connection.commit();
            return;
        } catch (SQLException e) {
            // print SQL exception information
            H2JDBCUtils.printSQLException(e);
        }
    }

}