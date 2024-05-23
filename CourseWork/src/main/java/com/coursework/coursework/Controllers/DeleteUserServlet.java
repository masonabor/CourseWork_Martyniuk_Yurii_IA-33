package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    private UsersDAO usersDataBase;

    @Override
    public void init() throws ServletException {
        super.init();
        usersDataBase = (UsersDAO) getServletContext().getAttribute("usersDataBase");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User user = (User) request.getSession().getAttribute("user");


        if (user != null && usersDataBase.isRegisteredUser(user.getLogin())) {

            usersDataBase.deleteUser(user);
            request.getSession().invalidate();
            response.sendRedirect("homePage.jsp");

        } else {
            response.sendError(500, "Помилка при видаленні облікового запису");
        }
    }
}