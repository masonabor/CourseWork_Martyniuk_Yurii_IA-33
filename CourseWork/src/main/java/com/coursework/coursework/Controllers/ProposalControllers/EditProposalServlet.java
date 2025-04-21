package com.coursework.coursework.Controllers.ProposalControllers;

import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.DAOs.UsersDAO;
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

@WebServlet("/editProposal")
public class EditProposalServlet extends HttpServlet {

    private TendersDAO tendersDataBase;
    private UsersDAO usersDataBase;

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
        usersDataBase = (UsersDAO) getServletContext().getAttribute("usersDataBase");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        String tenderIdStr = request.getParameter("tenderId");
        String proposalIdStr = request.getParameter("proposalId");

        if (tenderIdStr == null || proposalIdStr == null) {
            response.sendError(400, "ID тендеру або пропозиції не вказано");
            return;
        }

        UUID tenderId = UUID.fromString(tenderIdStr);
        UUID proposalId = UUID.fromString(proposalIdStr);

        Tender tender = tendersDataBase.getTenderById(tenderId);

        if (tender == null) {
            response.sendError(400, "Тендер не знайдено");
            return;
        }

        TenderProposal proposal = tender.findProposalById(proposalId);

        if (proposal == null) {
            response.sendError(400, "Пропозицію не знайдено");
            return;
        }

        String companyName = request.getParameter("companyName");
        String proposalDetails = request.getParameter("proposalDetails");
        double price;

        try {
            price = Double.parseDouble(request.getParameter("price"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorProposal", "Недійсна ціна");
            request.getRequestDispatcher("editProposal.jsp").forward(request, response);
            return;
        }

        if (companyName.isEmpty()) {
            request.setAttribute("errorProposal", "Назва компанії повинна бути заповнена");
            request.getRequestDispatcher("editProposal.jsp").forward(request, response);
            return;
        }

        if (price > tender.getCost()) {
            request.setAttribute("errorProposal", "Ціна пропозиції повинна бути меншою, ніж бюджет на тендер");
            request.getRequestDispatcher("editProposal.jsp").forward(request, response);
            return;
        }

        proposal.setCompanyName(companyName);
        proposal.setProposalDetails(proposalDetails);
        proposal.setPrice(price);

        User updatedUser = usersDataBase.findByLogin(user.getLogin());
        request.setAttribute("updatedUser", updatedUser);

        request.setAttribute("editProposalSuccess", "Тендерну пропозицію успішно оновлено");
        request.getRequestDispatcher("userAccount.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tenderIdStr = request.getParameter("tenderId");
        String proposalIdStr = request.getParameter("proposalId");

        if (tenderIdStr == null || proposalIdStr == null) {
            response.sendError(400, "ID тендеру або пропозиції не вказано");
            return;
        }

        UUID tenderId = UUID.fromString(tenderIdStr);
        UUID proposalId = UUID.fromString(proposalIdStr);

        Tender tender = tendersDataBase.getTenderById(tenderId);

        if (tender == null) {
            response.sendError(400, "Тендер не знайдено");
            return;
        }

        TenderProposal proposal = tender.findProposalById(proposalId);

        if (proposal == null) {
            response.sendError(400, "Пропозицію не знайдено");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        tender.deleteProposalById(proposal.getId(), user);
        user.deleteUserProposal(proposal);

        User updatedUser = usersDataBase.findByLogin(user.getLogin());
        request.setAttribute("updatedUser", updatedUser);

        request.setAttribute("editProposalSuccess", "Пропозицію успішно видалено");
        request.getRequestDispatcher("userAccount.jsp").forward(request, response);
    }
}
