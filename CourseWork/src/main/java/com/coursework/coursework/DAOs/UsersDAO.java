package com.coursework.coursework.DAOs;

import Interfaces.UsersDAOInterface;
import com.coursework.coursework.ServiceLayer.User;
import com.coursework.coursework.ServiceLayer.User.ROLE;

import java.util.*;

public class UsersDAO implements UsersDAOInterface {
    private Set<User> usersDataBase = new TreeSet<>(Comparator.comparing(User::getLogin));

    public void setUsersDataBase(Set<User> usersDataBase) {
        this.usersDataBase = usersDataBase;
    }

    public Set<User> getUsersDataBase() {
        return usersDataBase;
    }

    public boolean isRegisteredUser(String username) {
        for (User user: usersDataBase) {
            if (username.equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    public User findByLogin(String usernaem) {
        for (User user : usersDataBase) {
            if (user.getLogin().equals(usernaem)) {
                return user;
            }
        }
        return null;
    }

    public synchronized void createUser(String username, String password) {
        if (!isRegisteredUser(username)) {
            usersDataBase.add(new User(username, password));
        }
    }

    public synchronized void createUser(String username, String password, ROLE role) {
        if (!isRegisteredUser(username)) {
            usersDataBase.add(new User(username, password, role));
        }
    }

    public synchronized void addUser(User user) {
        if (!isRegisteredUser(user.getLogin())) {
            usersDataBase.add(user);
        }
    }


    public synchronized void deleteUser(User user) {
        if (isRegisteredUser(user.getLogin())) {
            usersDataBase.remove(user);
        }
    }
}