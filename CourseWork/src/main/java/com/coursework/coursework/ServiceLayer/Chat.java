package com.coursework.coursework.ServiceLayer;

import com.coursework.coursework.Interfaces.ModelsInterfaces.ChatInterface;

import java.util.ArrayList;
import java.util.UUID;

public class Chat implements ChatInterface {
    private UUID chatId;
    private ArrayList<Message> chat;
    private ArrayList<User> users;

    public Chat(User user1, User user2) {
        this.chatId = UUID.randomUUID();
        this.chat = new ArrayList<>();
        this.users = new ArrayList<>();
        this.users.add(user1);
        this.users.add(user2);
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public ArrayList<Message> getChat() {
        return chat;
    }

    public void setChat(ArrayList<Message> chat) {
        this.chat = chat;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addMessage(User user, String message) {
        chat.add(new Message(user, message));
    }
}
