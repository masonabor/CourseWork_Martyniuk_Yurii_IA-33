package com.coursework.coursework.Controllers.ReviewControllers;

import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.TenderReview;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

@WebServlet("/createReview")
public class CreateReviewServlet extends HttpServlet {

    private TendersDAO tendersDataBase;
    private static final String PHONE_NUMBER_PATTERN = "\\+?\\d{1,3}?\\s?-?\\d{1,4}?\\s?-?\\d{1,4}?\\s?-?\\d{1,9}";

    @Override
    public void init() {
        tendersDataBase = (TendersDAO) getServletContext().getAttribute("tendersDataBase");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        String id = request.getParameter("tenderId");

        if (id == null) {
            response.sendError(500, "Недійсний id тендеру");
            return;
        }

        UUID tenderId = UUID.fromString(id);
        String companyName= request.getParameter("companyName");

        if (companyName == null || companyName.isEmpty()) {
            request.setAttribute("errorReview", "Назва компанії повинна бути заповнена");
            request.getRequestDispatcher("createTenderReview.jsp").forward(request, response);
            return;
        }

        String review = request.getParameter("review");

        if (review == null || review.isEmpty()) {
            request.setAttribute("errorReview", "Поле відгуку пусте");
            request.getRequestDispatcher("createTenderReview.jsp").forward(request, response);
            return;
        }

        String phoneNumber = request.getParameter("phoneNumber");

        if (!isValidPhoneNumber(phoneNumber)) {
            request.setAttribute("errorReview", "Введіть правильний номер (без введення +380)");
            request.getRequestDispatcher("createTenderReview.jsp").forward(request, response);
            return;
        }

        Tender tender = tendersDataBase.getTenderById(tenderId);

        if (tender == null) {
            response.sendError(404, "Тендер не знайдено");
            return;
        }

        TenderReview tenderReview = new TenderReview(tender, user, review, phoneNumber, companyName);
        tendersDataBase.addReview(tenderReview);

        request.setAttribute("successReviewMessage", "Відгук успішно створено");
        request.getRequestDispatcher("homePage").forward(request, response);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.matches(PHONE_NUMBER_PATTERN, phoneNumber);
    }
}
