package com.coursework.coursework.Controllers.UserControllers;

import com.coursework.coursework.DAOs.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UsersDAO usersDataBase;

    @Override
    public void init() {
        usersDataBase = (UsersDAO) getServletContext().getAttribute("usersDataBase");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        if (usersDataBase.isRegisteredUser(username)) {
            request.setAttribute("errorMessage", "Користувач з таким логіном зареєстрований");
            request.getRequestDispatcher("registrationPage.jsp").forward(request, response);
        } else {
            usersDataBase.createUser(username, password);
            request.setAttribute("successMessage", "Ви успішно зареєструвалися");
            request.getRequestDispatcher("registrationPage.jsp").forward(request, response);
        }
    }
}
