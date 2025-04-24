package com.coursework.coursework.DAOs;

import com.coursework.coursework.Interfaces.DAOsInterfaces.ChatDAOInterface;
import com.coursework.coursework.ServiceLayer.Chat;
import com.coursework.coursework.ServiceLayer.Message;
import com.coursework.coursework.ServiceLayer.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.UUID;

public class ChatDAO implements ChatDAOInterface {
    private final SessionFactory sessionFactory;

    public ChatDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Chat getChatById(UUID chatId) {
        try (Session session = sessionFactory.openSession()) {
            Chat chat = session.createQuery(
                            "SELECT c FROM Chat c WHERE c.chatId = :chatId", Chat.class)
                    .setParameter("chatId", chatId)
                    .uniqueResult();

            if (chat != null) {
                Hibernate.initialize(chat.getChat());
                Hibernate.initialize(chat.getUsers());
            }

            return chat;
        }
    }

    public Chat createNewChat(User user1, User user2) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Chat chat = new Chat(user1, user2);
            session.persist(chat);
            tx.commit();
            return chat;
        }
    }

    public void saveMessage(Message message) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(message);
            tx.commit();
        }
    }

    public void updateChat(Chat chat) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(chat);
            tx.commit();
        }
    }
}
