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
            <p>Ви увійшли як: ${user.login}</p>
            <a href="userAccount.jsp" class="button">Кабінет користувача</a>
        </c:if>
        <c:if test="${empty user}">
            <a href="loginPage.jsp">Увійти</a>
        </c:if>
    </div>

    <h1>Деталі тендеру</h1>
    <div class="tender">
        <h2>${tender.name}</h2>
        <p>${tender.description}</p>
        <p><fmt:formatNumber value="${tender.cost}" type="currency" currencySymbol="₴"/></p>
        <p>${tender.status}</p>
        <c:if test="${not empty user and tender.authorId == user.userId}">
            <a href="checkReviews.jsp?id=${tender.id}" class="button">Переглянути відгуки</a>
            <a href="editTender.jsp?id=${tender.id}" class="button">Редагувати ваш тендер</a>
        </c:if>
        <c:if test="${not empty user and tender.authorId == user.userId}">
            <a href="generateURL?id=${tender.id}" class="button">Згенерувати посилання на тендер</a>
            <p>${URL}</p>
        </c:if>
    </div>

    <span class="message">${proposalSuccess}</span><br>
    <span class="message">${successEditMessage}</span><br>
    <c:if test="${(empty user) or (user.userId ne tender.authorId)}">
        <a href="createProposal.jsp?id=${tender.id}" class="button">Створити пропозицію</a>
    </c:if>
    <h2>Тендерні пропозиції</h2>
    <div class="proposals">
        <c:if test="${not empty tender.tenderProposals}">
            <c:forEach var="proposal" items="${tender.tenderProposals}">
                <div class="proposal">
                    <h3>${proposal.companyName}</h3>
                    <p>${proposal.proposalDetails}</p>
                    <p>${proposal.author.login}</p>
                    <p><fmt:formatNumber value="${proposal.price}" type="currency" currencySymbol="₴"/></p>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty tender.tenderProposals}">
            <h3>Немає тендерних пропозицій</h3>
        </c:if>
    </div>

    <a href="homePage" class="button">Повернутися до списку тендерів</a>
</div>
</body>
</html>
