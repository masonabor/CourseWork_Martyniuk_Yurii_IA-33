package com.coursework.coursework.Listeners;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.User.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class UserListener implements ServletContextListener {

    private UsersDAO usersDataBase;

    @Override
    public void contextInitialized(ServletContextEvent context) {

        usersDataBase = new UsersDAO();

        usersDataBase.createUser("Brigadir", "12345677");
        usersDataBase.createUser("Ded Tekila", "777",  ROLE.ADMIN);
        usersDataBase.createUser("Kolya", "dota");
        usersDataBase.createUser("Maks", "wertyble");
        usersDataBase.createUser("Belyi", "team123");

        context.getServletContext().setAttribute("usersDataBase", usersDataBase);
        System.out.println("contextUserInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        usersDataBase = null;

    }
}