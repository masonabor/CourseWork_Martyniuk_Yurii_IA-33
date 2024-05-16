package Interfaces;

import java.util.Set;

import com.coursework.coursework.ServiceLayer.User;
import com.coursework.coursework.ServiceLayer.User.*;

public interface UsersDAOInterface {

    void setUsersDataBase(Set<User> usersDataBase);
    Set<User> getUsersDataBase();
    boolean isRegisteredUser(String login);
    User findByLogin(String login);
    void createUser(String login, String password);
    void createUser(String login, String password, ROLE role);
    void deleteUser(User user);
}
