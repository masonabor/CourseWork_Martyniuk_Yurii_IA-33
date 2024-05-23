package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/exitUser")
public class ExitUserServlet extends HttpServlet {

    private UsersDAO usersDataBase;

    @Override
    public void init() throws ServletException {
        usersDataBase = (UsersDAO) getServletContext().getAttribute("usersDataBase");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user != null && usersDataBase.isRegisteredUser(user.getLogin())) {
            request.getSession().invalidate();
            response.sendRedirect("homePage");
        } else {
            response.sendError(500, "Помилка при виході з облікового запису");
        }
    }
}