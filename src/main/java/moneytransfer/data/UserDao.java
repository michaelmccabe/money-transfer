package moneytransfer.data;

import moneytransfer.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDao extends H2Database implements Dao<User> {

    private static final String INSERT_USERS_SQL = "INSERT INTO money_transfer.users" +
            "  (uuid, name) VALUES " +
            " (?, ?);";

    private static final String GET_USER = "select uuid,name from money_transfer.users where uuid =?";

    private static final String GET_ALL_USERS = "select uuid,name from money_transfer.users";

    @Override
    public User get(UUID uuid) {
        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER);) {
            preparedStatement.setString(1, uuid.toString());
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(uuid + "," + name );
                return new User(uuid,name);
            }
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }

        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String uuid = rs.getString("uuid");
                System.out.println(uuid + "," + name );
                users.add(new User(UUID.fromString(uuid),name));
            }

        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }

        return users;
    }

    @Override
    public User add(User user) {

        System.out.println(INSERT_USERS_SQL);
        // Step 1: Establishing a Connection
        try (Connection connection = H2JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getUuid().toString());
            preparedStatement.setString(2, user.getName());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();

            return user;
        } catch (SQLException e) {
            // print SQL exception information
            H2JDBCUtils.printSQLException(e);
        }
    return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }
}
