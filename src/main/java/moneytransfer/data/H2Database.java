package moneytransfer.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class H2Database {

    protected void execute(String sqlQuery) throws SQLException {

        System.out.println(sqlQuery);
        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             Statement statement = connection.createStatement();) {
            // Step 3: Execute the query or update query
            statement.execute(sqlQuery);

        } catch (SQLException e) {
            // print SQL exception information
            H2JDBCUtils.printSQLException(e);
        }
    }
}
