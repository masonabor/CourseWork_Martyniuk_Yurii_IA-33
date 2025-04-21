package com.coursework.coursework.Controllers.TenderControllers;


import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.DAOs.UsersDAO;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/changeTenderStatus")
public class ChangeTenderStatusServlet extends HttpServlet {

    private TendersDAO tendersDataBase;
    private UsersDAO usersDataBase;

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
        usersDataBase = (UsersDAO) getServletContext().getAttribute("usersDataBase");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User user = (User) request.getSession().getAttribute("user");
        String id = request.getParameter("id");
        String newStatus = request.getParameter("newStatus");

        if (id == null) {
            response.sendError(400, "Немає Id тендеру");
            return;
        }

        UUID tenderId = UUID.fromString(id);

        if (user == null) {
            response.sendError(400, "Користувача не знайдено");
            return;
        }

        if (!tendersDataBase.isTenderInDataBase(tenderId)) {
            response.sendError(400, "Тендер з таким id відсутній у базі даних"); // тут проблема
        }

        Tender tender = tendersDataBase.getTenderById(tenderId);

        if (!(user.getUserId().equals(tender.getAuthor().getUserId()))) {
            response.sendError(400, "Ви не є власником тендеру");
            return;
        }

        if (newStatus.equals("activate")) {
            tender.updateStatus(Tender.Status.ACTIVE);
        } else tender.updateStatus(Tender.Status.INACTIVE);

        tendersDataBase.updateTender(tender);
        User updatesUser = usersDataBase.findByLogin(user.getLogin());
        request.getSession().setAttribute("user", updatesUser);

        response.sendRedirect("tenderDetails.jsp?id=" + tenderId);
    }

}
