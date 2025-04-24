package com.coursework.coursework.Listeners;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.DAOs.ChatDAO;
import com.coursework.coursework.ServiceLayer.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebListener
public class AppContextListener implements ServletContextListener {

    private SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent context) {
        try {
            Configuration config = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Tender.class)
                    .addAnnotatedClass(TenderProposal.class)
                    .addAnnotatedClass(TenderReview.class)
                    .addAnnotatedClass(Chat.class)
                    .addAnnotatedClass(Message.class);

            sessionFactory = config.buildSessionFactory();

            UsersDAO usersDAO = new UsersDAO(sessionFactory);
            TendersDAO tendersDAO = new TendersDAO(sessionFactory);
            ChatDAO chatDAO = new ChatDAO(sessionFactory);

            context.getServletContext().setAttribute("usersDataBase", usersDAO);
            context.getServletContext().setAttribute("tendersDataBase", tendersDAO);
            context.getServletContext().setAttribute("chatsDataBase", chatDAO);

            System.out.println("App context initialized with Hibernate");

        } catch (Exception e) {
            throw new RuntimeException("Hibernate initialization failed", e);

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent context) {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
