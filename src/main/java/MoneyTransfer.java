import com.google.gson.Gson;
import moneytransfer.data.AccountDao;
import moneytransfer.data.H2JDBCUtils;
import moneytransfer.data.TransactionDao;
import moneytransfer.exceptions.MoneyTransferException;
import moneytransfer.model.*;
import moneytransfer.service.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static spark.Spark.post;
import static spark.Spark.get;
import org.h2.tools.RunScript;

public class MoneyTransfer {

    public static void main(String[] args) {
        MoneyTransfer moneyTransfer = new MoneyTransfer();
        moneyTransfer.initialiseUsersService();
        moneyTransfer.initialiseAccountsService();
        moneyTransfer.initialiseTransactionsService();
    }

    private void initialiseTransactionsService() {
        final TransactionService transactionService = new TransactionServiceImpl(new AccountDao(), new TransactionDao());

        post("/deposit", (request, response) -> {
            response.type("application/json");
            TransactionJson transaction = new Gson().fromJson(request.body(), TransactionJson.class);

            try {
                transactionService.addFundsToAccount(UUID.fromString(transaction.getAccountId()), transaction.getAmount());
            }
            catch (MoneyTransferException ex){
                return new Gson().toJson(new Response(Status.ERROR, ex.getMessage()));
            }
            return new Gson().toJson(new Response(Status.SUCCESS));
        });

        post("/withdrawal", (request, response) -> {
            response.type("application/json");
            TransactionJson transaction = new Gson().fromJson(request.body(), TransactionJson.class);
            try {
            transactionService.withdrawFundsFromAccount(UUID.fromString(transaction.getAccountId()), transaction.getAmount());
            }
            catch (MoneyTransferException ex){
                return new Gson().toJson(new Response(Status.ERROR, ex.getMessage()));
            }
            return new Gson().toJson(new Response(Status.SUCCESS));
        });

        post("/transfer", (request, response) -> {
            response.type("application/json");
            TransactionJson transaction = new Gson().fromJson(request.body(), TransactionJson.class);
            try {
                transactionService.transferFundsBetweenAccounts(UUID.fromString(transaction.getAccountId()), UUID.fromString(transaction.getDestinationAccountId()), transaction.getAmount());
            }
            catch (MoneyTransferException ex){
                return new Gson().toJson(new Response(Status.ERROR, ex.getMessage()));
            }
            return new Gson().toJson(new Response(Status.SUCCESS));
        });

        get("/transactions", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new Response(Status.SUCCESS, new Gson().toJsonTree(transactionService.getAllTransactions())));
        });
    }

    private void initialiseAccountsService() {
        final AccountService accountService = new AccountServiceImpl();

        get("/accounts", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new Response(Status.SUCCESS, new Gson().toJsonTree(accountService.getAccounts())));
        });

        get("/accounts/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new Response(Status.SUCCESS, new Gson().toJsonTree(accountService.getAccount(request.params(":id")))));
        });

        post("/accounts", (request, response) -> {
            response.type("application/json");
            Account account = new Gson().fromJson(request.body(), Account.class);
            accountService.createAccount(account);
            return new Gson().toJson(new Response(Status.SUCCESS, new Gson().toJsonTree(account)));
        });

    }

    private void initialiseUsersService() {
        final UserService userService = new UserServiceImpl();

        get("/users", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(new Response(Status.SUCCESS, new Gson().toJsonTree(userService.getUsers())));
        });

        get("/users/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new Response(Status.SUCCESS, new Gson().toJsonTree(userService.getUser(request.params(":id")))));
        });

        post("/users", (request, response) -> {
            response.type("application/json");
            User user = new Gson().fromJson(request.body(), User.class);
            user= userService.addUser(user);
            return new Gson().toJson(new Response(Status.SUCCESS, new Gson().toJsonTree(user)));
        });
    }

    public MoneyTransfer()  {
        try {
            initialiseDatabase(H2JDBCUtils.getConnection());
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }

    }

    private void initialiseDatabase(Connection connection) throws SQLException {
        try {
            File script = new File(getClass().getResource("/setupdb.sql").getFile());
            RunScript.execute(connection, new FileReader(script));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Database initialize script error");
        }
    }

}
