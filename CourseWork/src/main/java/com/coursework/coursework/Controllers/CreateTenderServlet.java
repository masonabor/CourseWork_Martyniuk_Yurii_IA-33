package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/createTender")
public class CreateTenderServlet extends HttpServlet {

    private TendersDAO tendersDataBase;

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            request.setAttribute("errorMessage", "Для відгуку на тендер потрібно авторизуватися");
            request.setAttribute("addressing", "createTender");
            request.getRequestDispatcher("loginPage.jsp").forward(request, response);
            return;
        }

        String name = request.getParameter("tenderName");
        String description = request.getParameter("tenderDescription");
        String deadlineStr = request.getParameter("tenderDeadline");
        String costStr = request.getParameter("tenderCost");

        if (name == null || description == null || deadlineStr == null || costStr == null) {
            request.setAttribute("errorTender", "Введіть дані");
            request.getRequestDispatcher("createTender.jsp").forward(request, response);
            return;
        }

        LocalDate deadline;
        double cost;

        try {
            deadline = LocalDate.parse(deadlineStr);
        } catch (DateTimeParseException e) {
            request.setAttribute("errorTender", "Ведіть правильний формат дати");
            request.getRequestDispatcher("createTender.jsp").forward(request, response);
            return;
        }

        try {
            cost = Double.parseDouble(costStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorTender", "Неправильно введена ціна");
            request.getRequestDispatcher("createTender.jsp").forward(request, response);
            return;
        }

        Tender tender = new Tender(name, description, deadline, cost, user);
        tendersDataBase.addTender(tender);
        user.addTender(tender);
        response.sendRedirect("tenderDetails.jsp?id=" + tender.getId());
    }
}
