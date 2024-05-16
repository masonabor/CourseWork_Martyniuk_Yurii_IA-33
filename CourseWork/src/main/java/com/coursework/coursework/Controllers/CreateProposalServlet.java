package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.TenderProposal;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/createProposal")
public class CreateProposalServlet extends HttpServlet {

    private TendersDAO tendersDataBase;

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            request.setAttribute("errorMessage", "Для відгуку на тендер потрібно авторизуватися");
            request.getRequestDispatcher("loginPage.jsp").forward(request, response);
            return;
        }

        UUID tenderId;
        try {
            tenderId = UUID.fromString(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(500, "Недійсний id тендеру");
            return;
        }

        String proposalDetails = request.getParameter("proposalDetails");
        String companyName = request.getParameter("companyName");
        double price;

        try {
            price = Double.parseDouble(request.getParameter("price"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorProposal", "Недійсна ціна");
            request.getRequestDispatcher("createProposal.jsp").forward(request, response);
            return;
        }

        if (proposalDetails.length() <= 100) {
            request.setAttribute("errorProposal", "Опис повинен бути більше 100 символів");
            request.getRequestDispatcher("createProposal.jsp").forward(request, response);
            return;
        }
        if (companyName.isEmpty()) {
            request.setAttribute("errorProposal", "Назва компанії повинна бути заповнена");
            request.getRequestDispatcher("createProposal.jsp").forward(request, response);
            return;
        }

        Tender tender = tendersDataBase.getTenderById(tenderId);
        if (tender == null) {
            response.sendError(404, "Тендер не знайдено");
            return;
        }
        if (price > tender.getCost()) {
            request.setAttribute("errorProposal", "Ціна пропозиції повинна бути меншою, ніж бюджет на тендер");
            request.getRequestDispatcher("createProposal.jsp").forward(request, response);
            return;
        }

        TenderProposal proposal = new TenderProposal(tenderId, companyName, proposalDetails, price, user);
        tender.addTenderProposal(proposal);
        user.addTenderProposal(proposal);

        request.setAttribute("proposalSuccess", "Тендерну пропозицію успішно створено ");
        request.getRequestDispatcher("tenderDetails.jsp?tenderId=" + tenderId).forward(request, response);
    }
}
