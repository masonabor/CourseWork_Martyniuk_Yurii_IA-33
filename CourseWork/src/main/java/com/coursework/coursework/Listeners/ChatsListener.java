package com.coursework.coursework.Listeners;

import com.coursework.coursework.DAOs.ChatDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ChatsListener implements ServletContextListener {
    private ChatDAO chatsDataBase;

    @Override
    public void contextInitialized(ServletContextEvent context) {

        chatsDataBase = new ChatDAO();

        context.getServletContext().setAttribute("chatsDataBase", chatsDataBase);
        System.out.println("contextChatsInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent context) {
        chatsDataBase = null;

    }
}
