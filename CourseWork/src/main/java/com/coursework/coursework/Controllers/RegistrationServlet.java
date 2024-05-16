package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.PasswordGeneration;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.coursework.coursework.ServiceLayer.PasswordGeneration.*;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UsersDAO usersDataBase;

    @Override
    public void init() throws ServletException {
        super.init();
        usersDataBase = (UsersDAO) getServletContext().getAttribute("usersDataBase");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        if (usersDataBase.isRegisteredUser(username)) {
            request.setAttribute("errorMessage", "Користувач з таким логіном зареєстрований");
            request.getRequestDispatcher("registrationPage.jsp").forward(request, response);
        } else {
            User user = new User(username, password);
            usersDataBase.addUser(user);
            request.setAttribute("successMessage", "Ви успішно зареєструвалися");
            request.getRequestDispatcher("registrationPage.jsp").forward(request, response);
        }
    }
}
