package com.coursework.coursework.DAOs;

import com.coursework.coursework.Interfaces.DAOsInterfaces.ChatDAOInterface;
import com.coursework.coursework.ServiceLayer.Chat;
import com.coursework.coursework.ServiceLayer.User;

import java.util.HashMap;
import java.util.UUID;

public class ChatDAO implements ChatDAOInterface {
    private HashMap<UUID, Chat> chatsDataBase = new HashMap<>();

    public HashMap<UUID, Chat> getChatsDataBase() {
        return chatsDataBase;
    }

    public void setChatsDataBase(HashMap<UUID, Chat> chatsDataBase) {
        this.chatsDataBase = chatsDataBase;
    }

    public Chat getChatById(UUID chatId) {
        return chatsDataBase.get(chatId);
    }

    public Chat createNewChat(User user1, User user2) {
        Chat chat = new Chat(user1, user2);
        chatsDataBase.put(chat.getChatId(), chat);
        return chat;
    }
}
