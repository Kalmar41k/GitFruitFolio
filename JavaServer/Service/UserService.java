package org.example.serverfruitfolio.Service;

import org.example.serverfruitfolio.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(int id);
    User createUser(User user);

    void deleteUserById(int id);
}
