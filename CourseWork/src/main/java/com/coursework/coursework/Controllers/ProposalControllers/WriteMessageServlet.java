package com.coursework.coursework.Controllers.ProposalControllers;

import com.coursework.coursework.DAOs.ChatDAO;
import com.coursework.coursework.ServiceLayer.Chat;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/writeMessage")
public class WriteMessageServlet extends HttpServlet {
    private ChatDAO chatsDataBase;

    @Override
    public void init() {
        chatsDataBase = (ChatDAO) getServletContext().getAttribute("chatsDataBase");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        String id = request.getParameter("chatId");

        if (id == null || id.isEmpty()) {
            response.sendError(500, "Id чату відсутній");
            return;
        }

        UUID chatId = UUID.fromString(id);

        String message = request.getParameter("message");

        if (message == null || message.isEmpty()) {
            request.setAttribute("errorMessage", "Ви не написали повідомлення");
            request.getRequestDispatcher("chatPage.jsp?chatId=" + chatId).forward(request, response);
        }

        Chat chat = chatsDataBase.getChatById(chatId);
        chat.addMessage(user, message);

        request.getRequestDispatcher("chatPage.jsp?chatId=" + chatId).forward(request, response);

    }
}
