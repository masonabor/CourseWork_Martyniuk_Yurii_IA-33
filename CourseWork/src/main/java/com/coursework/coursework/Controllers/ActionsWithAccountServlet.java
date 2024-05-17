package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userActions")
public class ActionsWithAccountServlet extends HttpServlet {

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
        String action = request.getParameter("action");

        if (user == null) {
            System.out.println("user is null");
        }

        if (user != null) {

            if (action.equals("updateUsername")) {

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

            } else if (action.equals("updatePassword")) {

                String newPassword = request.getParameter("newPassword");

                if (newPassword != null && !newPassword.isEmpty()) {
                    user.setPassword(newPassword);
                    request.getSession().setAttribute("user", user);
                    request.setAttribute("passwordMessage", "Пароль успішно змінений");

                } else {
                    if (newPassword == null) {
                        request.setAttribute("passwordError", "Ви не ввели новий пароль");
                    }
                }
                request.getRequestDispatcher("userAccount.jsp").forward(request, response);
            }

        } else {
            response.sendError(500, "Об'єкт користувача нульовий");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User user = (User) request.getSession().getAttribute("user");
        String action = request.getParameter("action");

        if (user != null) {
            if (action.equals("logout")) {
                request.getSession().invalidate();
                response.sendRedirect("homePage");

            } else if (action.equals("delete") && usersDataBase.isRegisteredUser(user.getLogin())) {
                usersDataBase.deleteUser(user);
                request.getSession().invalidate();
                response.sendRedirect("homePage.jsp");

            } else {
                response.sendError(500, "Помилка при виході або видаленні обікового заису");
            }

        } else {
            response.sendError(500, "Неочікувана помилка");
        }
    }
}