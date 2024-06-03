package com.coursework.coursework.Interfaces.DAOsInterfaces;

import com.coursework.coursework.ServiceLayer.User;

public interface UsersDAOInterface {
    boolean isRegisteredUser(String username);
    User findByLogin(String username);
    void createUser(String username, String password);
    void addUser(User user);
    void deleteUser(User user);
}
