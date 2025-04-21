package com.coursework.coursework.DAOs;

import com.coursework.coursework.Interfaces.DAOsInterfaces.UsersDAOInterface;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UsersDAO implements UsersDAOInterface {
    private final SessionFactory sessionFactory;

    public UsersDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean isRegisteredUser(String username) {
        return findByLogin(username) != null;
    }

    @Override
    public User findByLogin(String username) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery(
                            "SELECT DISTINCT u FROM User u " +
                                    "LEFT JOIN FETCH u.tenders t " +
                                    "LEFT JOIN FETCH t.author " +
                                    "LEFT JOIN FETCH u.tenderProposals p " +
                                    "LEFT JOIN FETCH p.tender pt " +
                                    "LEFT JOIN FETCH pt.author " +
                                    "WHERE u.login = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();

            return user;
        }
    }


    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
        }
    }

    public void updateUserTender(Tender tender) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(tender);
            tx.commit();

        }
    }

    @Override
    public synchronized void createUser(String username, String password) {
        if (!isRegisteredUser(username)) {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                session.persist(new User(username, password));
                tx.commit();
            }
        }
    }

    @Override
    public synchronized void addUser(User user) {
        if (!isRegisteredUser(user.getLogin())) {
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                session.persist(user);
                tx.commit();
            }
        }
    }

    @Override
    public synchronized void deleteUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(user);
            tx.commit();
        }
    }
}