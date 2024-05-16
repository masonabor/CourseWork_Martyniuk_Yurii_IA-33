<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.coursework.coursework.DAOs.TendersDAO" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Tender" %>
<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Деталі тендеру</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .header {
            text-align: right;
            margin-bottom: 20px;
        }
        .header a {
            text-decoration: none;
            color: blue;
        }
        .header a:hover {
            text-decoration: underline;
        }
        .tender {
            margin-bottom: 20px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .proposal {
            margin-left: 20px;
            padding: 5px;
            border-left: 2px solid #ccc;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 10px;
            background-color: #008CBA;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .button:hover {
            background-color: #005f7f;
        }
        .message {
            color: green;
            margin-top: 5px;
        }
    </style>
</head>
<body>

<%
    String id = request.getParameter("id");

    if (id != null && !id.isEmpty()) {
        UUID tenderId = UUID.fromString(request.getParameter("id"));
        TendersDAO tendersDAO = (TendersDAO) request.getServletContext().getAttribute("tendersDataBase");
        Tender tender = tendersDAO.getTenderById(tenderId);

        if (tender == null) {
            response.sendError(500, "Тендер за таким id не знайдено");
        } else {
            request.setAttribute("tender", tender);
        }
    } else {
        response.sendError(500, "Id тендеру відсутнє");
    }
%>

<div class="container">
    <div class="header">
        <c:if test="${not empty user}">
            <p>Ви увійшли як: ${user.getLogin()}</p>
            <a href="userAccount.jsp" class="button">Кабінет користувача</a>
        </c:if>
        <c:if test="${empty user}">
            <a href="loginPage.jsp">Увійти</a>
        </c:if>
    </div>

    <h1>Деталі тендеру</h1>
    <div class="tender">
        <h2>${tender.getName()}</h2>
        <p>${tender.getDescription()}</p>
        <p><fmt:formatNumber value="${tender.getCost()}" type="currency" currencySymbol="₴"/></p>
        <p>${tender.getStatus()}</p>
    </div>

    <span class="message">${proposalSuccess}</span><br>
    <span class="message">${successEditMessage}</span><br>
    <a href="createProposal.jsp?id=${tender.getId()}" class="button">Створити пропозицію</a>

    <h2>Тендерні пропозиції</h2>
    <div class="proposals">
        <c:if test="${not empty tender.getTenderProposals()}">
            <c:forEach var="proposal" items="${tender.getTenderProposals()}">
                <div class="proposal">
                    <h3>${proposal.getCompanyName()}</h3>
                    <p>${proposal.getProposalDetails()}</p>
                    <p>${proposal.getAuthor().getLogin()}</p>
                    <p><fmt:formatNumber value="${proposal.getPrice()}" type="currency" currencySymbol="₴"/></p>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty tender.getTenderProposals()}">
            <h3>Немає тендерних пропозицій</h3>
        </c:if>
    </div>

    <a href="homePage" class="button">Повернутися до списку тендерів</a>
</div>
</body>
</html>