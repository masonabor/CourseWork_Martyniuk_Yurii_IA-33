package com.coursework.coursework.Controllers.TenderControllers;

import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/generateURL")
public class GenerateURLServlet extends HttpServlet {

    TendersDAO tendersDataBase;

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        request.setAttribute("URL", generateURL(tender));
        request.getRequestDispatcher("tenderDetails.jsp?id=" + tenderId).forward(request, response);
    }

    private String generateURL(Tender tender) {
        if (tendersDataBase.isTenderInDataBase(tender.getId())) {
            return "http://localhost:8080/CourseWork_war_exploded/tenderDetails.jsp?id=" + tender.getId();
        } else return "Ви не є власником цього тендеру";
    }
}
