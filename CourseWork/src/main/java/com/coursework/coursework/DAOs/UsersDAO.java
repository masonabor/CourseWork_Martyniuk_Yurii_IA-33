package com.coursework.coursework.DAOs;

import com.coursework.coursework.Interfaces.DAOsInterfaces.UsersDAOInterface;
import com.coursework.coursework.ServiceLayer.User;

import java.util.*;

public class UsersDAO implements UsersDAOInterface {
    private Set<User> usersDataBase = new TreeSet<>(Comparator.comparing(User::getLogin));

    @Override
    public void setUsersDataBase(Set<User> usersDataBase) {
        this.usersDataBase = usersDataBase;
    }

    @Override
    public Set<User> getUsersDataBase() {
        return usersDataBase;
    }

    @Override
    public boolean isRegisteredUser(String username) {
        for (User user: usersDataBase) {
            if (username.equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findByLogin(String usernaem) {
        for (User user : usersDataBase) {
            if (user.getLogin().equals(usernaem)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public synchronized void createUser(String username, String password) {
        if (!isRegisteredUser(username)) {
            usersDataBase.add(new User(username, password));
        }
    }

    @Override
    public synchronized void addUser(User user) {
        if (!isRegisteredUser(user.getLogin())) {
            usersDataBase.add(user);
        }
    }

    @Override
    public synchronized void deleteUser(User user) {
        if (isRegisteredUser(user.getLogin())) {
            usersDataBase.remove(user);
        }
    }
}