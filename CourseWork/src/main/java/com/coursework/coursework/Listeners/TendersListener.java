package com.coursework.coursework.Listeners;

import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDate;

@WebListener
public class TendersListener implements ServletContextListener {

    private TendersDAO tendersDataBase;

    @Override
    public void contextInitialized(ServletContextEvent context) {

        tendersDataBase = new TendersDAO();

        tendersDataBase.createTender("Створення Укрзалізниці", "В процесі", LocalDate.of(2024, 8, 17), 10000000, new User("Brigadir", "12345677"));

        context.getServletContext().setAttribute("tendersDataBase", tendersDataBase);
        System.out.println("contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        tendersDataBase = null;

    }
}