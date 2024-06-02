package com.coursework.coursework.Interfaces.ModelsInterfaces;

import com.coursework.coursework.ServiceLayer.Message;
import com.coursework.coursework.ServiceLayer.User;

import java.util.ArrayList;
import java.util.UUID;

public interface ChatInterface {
    UUID getChatId();
    void setChatId(UUID chatId);

    ArrayList<Message> getChat();
    void setChat(ArrayList<Message> chat);

    ArrayList<User> getUsers();
    void setUsers(ArrayList<User> users);

    void addMessage(User user, String message);
}
