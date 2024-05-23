package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateUsername")
public class UpdateUsernameServlet extends HttpServlet {

    private UsersDAO usersDataBase;

    @Override
    public void init() throws ServletException {
        super.init();
        usersDataBase = (UsersDAO) getServletContext().getAttribute("usersDataBase");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            System.out.println("user is null");
        }

        if (user != null) {

            String newUsername = request.getParameter("newUsername");

            if (newUsername != null && !newUsername.isEmpty() && !usersDataBase.isRegisteredUser(newUsername)) {
                user.setLogin(newUsername);
                request.getSession().setAttribute("user", user);
                request.setAttribute("usernameMessage", "Логін успішно змінений");

            } else {
                if (newUsername == null || newUsername.isEmpty()) {
                    request.setAttribute("usernameError", "Ви не ввели новий логін");
                } else {
                    request.setAttribute("usernameError", "Цей логін вже зайнятий");
                }
            }
            request.getRequestDispatcher("userAccount.jsp").forward(request, response);

        } else {
            response.sendError(500, "Об'єкт користувача нульовий");
        }
    }
}