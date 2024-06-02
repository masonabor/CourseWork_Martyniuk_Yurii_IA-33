package com.coursework.coursework.Controllers.UserControllers;

import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.coursework.coursework.ServiceLayer.PasswordHashing.hashPassword;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {

    private UsersDAO usersDataBase;

    @Override
    public void init() {
        usersDataBase = (UsersDAO) getServletContext().getAttribute("usersDataBase");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String addressing = request.getParameter("addressing");

        if (usersDataBase.isRegisteredUser(username)) {
            User user = usersDataBase.findByLogin(username);

            if (user != null && user.getPassword().equals(hashPassword(password))) {
                request.getSession().setAttribute("user", user);

                if (addressing.equals("createTender")) {
                    request.getRequestDispatcher("createTender.jsp").forward(request, response);
                    return;
                }
                if (addressing.equals("review")) {
                    String tenderId = request.getParameter("tenderId");
                    request.getRequestDispatcher("createTenderReview.jsp?id=" + tenderId).forward(request, response);
                    return;
                }
                if (addressing.equals("createProposal")) {
                    String tenderId = request.getParameter("tenderId");
                    request.getRequestDispatcher("createProposal.jsp?id=" + tenderId).forward(request, response);
                    return;
                }

                response.sendRedirect("homePage");

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

