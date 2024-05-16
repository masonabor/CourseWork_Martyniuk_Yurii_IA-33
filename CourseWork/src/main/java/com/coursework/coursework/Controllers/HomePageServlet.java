package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.ServiceLayer.Tender;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/homePage")
public class HomePageServlet extends HttpServlet {

    private TendersDAO tendersDataBase;

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<UUID, Tender> tenders = tendersDataBase.getAllTenders();
        request.setAttribute("tenders", tenders);
        request.getRequestDispatcher("homePage.jsp").forward(request, response);


    }
}
