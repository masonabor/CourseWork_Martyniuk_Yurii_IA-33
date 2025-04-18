package com.coursework.coursework.Configuration;

import com.coursework.coursework.ServiceLayer.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration config = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class);

            sessionFactory = config.buildSessionFactory();
        } catch (HibernateException e ) {
            System.err.println("HibernateException: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void shutdown() {
        sessionFactory.close();
    }
}
