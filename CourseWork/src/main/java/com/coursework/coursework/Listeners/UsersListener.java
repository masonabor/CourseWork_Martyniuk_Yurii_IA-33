package com.coursework.coursework.Listeners;

import com.coursework.coursework.DAOs.UsersDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class UsersListener implements ServletContextListener {

    private UsersDAO usersDataBase;

    @Override
    public void contextInitialized(ServletContextEvent context) {

        usersDataBase = new UsersDAO();

        usersDataBase.createUser("Brigadir", "12345677");
        usersDataBase.createUser("user", "12345678");


        context.getServletContext().setAttribute("usersDataBase", usersDataBase);
        System.out.println("contextUserInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        usersDataBase = null;

    }
}