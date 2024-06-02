package com.coursework.coursework.Controllers.UserControllers;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {

    private UsersDAO usersDataBase;

    @Override
    public void init() {
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

            String newPassword = request.getParameter("newPassword");

            if (newPassword != null && !newPassword.isEmpty() && !usersDataBase.isRegisteredUser(user.getLogin())) {
                user.setPassword(newPassword);
                request.getSession().setAttribute("user", user);
                request.setAttribute("passwordMessage", "Пароль успішно змінений");

            } else {
                if (newPassword == null) {
                    request.setAttribute("passwordError", "Ви не ввели новий пароль");
                }
            }
            request.getRequestDispatcher("editUser.jsp").forward(request, response);

        } else {
            response.sendError(500, "Об'єкт користувача нульовий");
        }
    }
}