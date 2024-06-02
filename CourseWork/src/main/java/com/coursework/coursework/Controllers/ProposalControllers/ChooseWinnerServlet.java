package com.coursework.coursework.Controllers.ProposalControllers;

import com.coursework.coursework.DAOs.ChatDAO;
import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.ServiceLayer.Chat;
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

@WebServlet("/chooseWinner")
public class ChooseWinnerServlet extends HttpServlet {

    TendersDAO tendersDataBase;
    ChatDAO chatsDataBase;

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
        chatsDataBase = (ChatDAO) getServletContext().getAttribute("chatsDataBase");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String proposalId = request.getParameter("winnerId");
        String id = request.getParameter("tenderId");

        if (proposalId == null) {
            response.sendError(400, "Немає Id пропозиції");
            return;
        }

        if (id == null) {
            response.sendError(400, "Немає Id тендеру");
            return;
        }

        UUID winnerId = UUID.fromString(proposalId);
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

        TenderProposal winnerProposal = tender.findProposalById(winnerId);
        Chat chat = chatsDataBase.createNewChat(user, winnerProposal.getAuthor());
        winnerProposal.setChatId(chat.getChatId());
        tender.setWinnerProposal(winnerProposal);

        request.setAttribute("chooseWinnerSuccess", "Переможця тендеру \"" + tender.getName() + "\" успішно обрано");
        request.getRequestDispatcher("userAccount.jsp").forward(request, response);

    }
}