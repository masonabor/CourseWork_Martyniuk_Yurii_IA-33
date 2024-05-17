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
import java.util.UUID;

@WebServlet("/editTender")
public class EditTenderServlet extends HttpServlet {

    private TendersDAO tendersDataBase;

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        if (id == null) {
            response.sendError(400, "Немає Id тендеру");
            return;
        }

        UUID tenderId = UUID.fromString(id);
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendError(400, "Користувача не знайдено");
            return;
        }

        Tender tender = tendersDataBase.getTenderById(tenderId);
        if (user != tender.getAuthor()) {
            response.sendError(400, "Ви не є власником тендеру");
            return;
        }

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String deadlineStr = request.getParameter("deadline");
        String costStr = request.getParameter("cost");

        if (name == null || description == null || deadlineStr == null || costStr == null) {
            request.setAttribute("errorEdinTender", "Введіть дані");
            request.getRequestDispatcher("editTender.jsp").forward(request, response);
            return;
        }

        LocalDate deadline;
        double cost;

        try {
            deadline = LocalDate.parse(deadlineStr);
        } catch (DateTimeParseException e) {
            request.setAttribute("errorEditTender", "Ведіть правильний формат дати");
            request.getRequestDispatcher("editTender.jsp").forward(request, response);
            return;
        }

        try {
            cost = Double.parseDouble(costStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorEditTender", "Неправильно введена ціна");
            request.getRequestDispatcher("editTender.jsp").forward(request, response);
            return;
        }

        tender.updateName(name);
        tender.updateDescription(description);
        tender.updateDeadline(deadline);
        tender.updateCost(cost);

        request.setAttribute("seccessEditMessage", "Тендер успішно оновлено");
        response.sendRedirect("tenderDetails.jsp?id=" + tender.getId());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        String id = request.getParameter("id");

        if (id == null) {
            response.sendError(400, "Немає Id тендеру");
            return;
        }

        UUID tenderId = UUID.fromString(id);
        boolean isDeletedTender;

        if (user != tendersDataBase.getTenderById(tenderId).getAuthor()) {
            response.sendError(500, "Ви не є власником");
            return;
        }

        isDeletedTender = tendersDataBase.deleteTenderById(tenderId);

        if (!isDeletedTender) {
            request.setAttribute("deleteError", "Тендер не видалено");
            request.getRequestDispatcher("editTender.jsp").forward(request, response);
        } else request.getRequestDispatcher("homePage.jsp").forward(request, response);
    }
}