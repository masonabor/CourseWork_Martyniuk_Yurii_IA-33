package com.coursework.coursework.Controllers.UserControllers;

import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/searchUserTenders")
public class SearchUserTendersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        String keyword = request.getParameter("keyword");

        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect("homePage");
            return;
        }

        List<Tender> searchResults = user.searchUserTenders(keyword);

        request.setAttribute("searchedTenders", searchResults);

        request.getRequestDispatcher("searchedTenders.jsp").forward(request, response);
    }
}