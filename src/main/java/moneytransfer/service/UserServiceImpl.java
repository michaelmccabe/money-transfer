package moneytransfer.service;

import moneytransfer.data.UserDao;
import moneytransfer.model.User;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDao();

    @Override
    public User addUser(User user) {
        return userDao.add(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getAll();
    }

    @Override
    public User getUser(String id) {
        return userDao.get(UUID.fromString(id));
    }

}
