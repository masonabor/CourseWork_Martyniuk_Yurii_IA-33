package com.coursework.coursework.Controllers;

import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.TenderProposal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/editProposal")
public class EditProposalServlet extends HttpServlet {

    private TendersDAO tendersDataBase;

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UUID tenderId;
        try {
            tenderId = UUID.fromString(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(500, "Недійсний id тендеру");
            return;
        }

        UUID proposalId;
        try {
            proposalId = UUID.fromString(request.getParameter("proposalId"));
        } catch (NumberFormatException e) {
            response.sendError(500, "Недійсний id пропозиції");
            return;
        }

        String companyName = request.getParameter("companyName");
        String proposalDetails = request.getParameter("proposalDetails");
        double price;

        try {
            price = Double.parseDouble(request.getParameter("price"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorProposal", "Недійсна ціна");
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

        TenderProposal proposal = tender.findProposalById(proposalId);
        proposal.setCompanyName(companyName);
        proposal.setProposalDetails(proposalDetails);
        proposal.setPrice(price);

        request.setAttribute("editProposalSuccess", "Тендерну пропозицію успішно оновлено");
        request.getRequestDispatcher("userAccount.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UUID tenderId;
        try {
            tenderId = UUID.fromString(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(500, "Недійсний id тендеру");
            return;
        }

        UUID proposalId;
        try {
            proposalId = UUID.fromString(request.getParameter("proposalId"));
        } catch (NumberFormatException e) {
            response.sendError(500, "Недійсний id пропозиції");
            return;
        }

        Tender tender = tendersDataBase.getTenderById(tenderId);
        tender.deleteProposalById(proposalId);

        request.setAttribute("editProposalSuccess", "Тендер успішно видалено");
        request.getRequestDispatcher("userAccount.jsp").forward(request, response);
    }
}
