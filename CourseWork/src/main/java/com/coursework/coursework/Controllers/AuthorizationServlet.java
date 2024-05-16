package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {

    private UsersDAO usersDataBase;

    @Override
    public void init() throws ServletException {
        super.init();
        usersDataBase = (UsersDAO) getServletContext().getAttribute("usersDataBase");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String addressing = request.getParameter("addressing");

        if (usersDataBase.isRegisteredUser(username)) {
            User user = usersDataBase.findByLogin(username);

            if (user != null && user.getPassword().equals(password)) {
                request.getSession().setAttribute("user", user);
                if (addressing.equals("createTender")) {
                    response.sendRedirect("createTender.jsp");
                    return;
                }
                request.getRequestDispatcher("homePage").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Неправильний логін або пароль");
                request.getRequestDispatcher("loginPage.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Користувача з таким логіном не знайдено");
            request.getRequestDispatcher("loginPage.jsp").forward(request, response);
        }
    }

}