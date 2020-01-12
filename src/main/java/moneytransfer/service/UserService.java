package moneytransfer.service;

import moneytransfer.model.User;

import java.util.List;

public interface UserService {

    User addUser(User user);

    List<User> getUsers();

    User getUser(String id);

}
