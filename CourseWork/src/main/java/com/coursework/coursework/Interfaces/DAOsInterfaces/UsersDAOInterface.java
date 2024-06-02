package com.coursework.coursework.Interfaces.DAOsInterfaces;

import com.coursework.coursework.ServiceLayer.User;

import java.util.Set;

public interface UsersDAOInterface {
    void setUsersDataBase(Set<User> usersDataBase);
    Set<User> getUsersDataBase();
    boolean isRegisteredUser(String username);
    User findByLogin(String username);
    void createUser(String username, String password);
    void addUser(User user);
    void deleteUser(User user);
}
