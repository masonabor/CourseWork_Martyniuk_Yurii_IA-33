package com.coursework.coursework.Interfaces.DAOsInterfaces;

import com.coursework.coursework.ServiceLayer.Chat;
import com.coursework.coursework.ServiceLayer.User;
import java.util.UUID;

public interface ChatDAOInterface {

    Chat getChatById(UUID chatId);
    Chat createNewChat(User user1, User user2);
}
