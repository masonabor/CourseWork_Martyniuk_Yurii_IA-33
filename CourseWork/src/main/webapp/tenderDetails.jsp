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
            background-color: #222;
            color: #ddd;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #333;
            border-radius: 8px;
        }
        .header {
            text-align: right;
            margin-bottom: 20px;
        }
        .header a {
            color: #fff;
            text-decoration: none;
            padding: 10px 20px;
            background-color: #444;
            border-radius: 5px;
            transition: background-color 0.3s;
            display: inline-block;
            margin-bottom: 10px;
        }
        .header a:hover {
            background-color: #555;
        }
        .tender, .proposal {
            margin-bottom: 20px;
            padding: 20px;
            background-color: #444;
            border: 1px solid #555;
            border-radius: 8px;
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
            return;
        } else {
            request.setAttribute("tender", tender);
        }
    } else {
        response.sendError(500, "Id тендеру відсутнє");
        return;
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
        <p>${tender.deadline}</p>
        <p><fmt:formatNumber value="${tender.cost}" type="currency" currencySymbol="₴"/></p>
        <p>${tender.status}</p>
        <c:if test="${not empty user and tender.authorId == user.userId}">
            <a href="checkReviews.jsp?id=${tender.id}" class="button">Переглянути відгуки</a>
            <a href="editTender.jsp?id=${tender.id}" class="button">Редагувати ваш тендер</a>
            <a href="generateURL?id=${tender.id}" class="button">Згенерувати посилання на тендер</a>
            <p>${URL}</p>
        </c:if>
    </div>

    <span class="message">${proposalSuccess}</span><br>
    <span class="message">${successEditMessage}</span><br>
    <span class="message">${successReviewMessage}</span><br>
    <c:if test="${tender.author ne user and not tender.isAfterDeadline()}">
        <a href="createProposal.jsp?id=${tender.id}" class="button">Створити пропозицію</a>
        <a href="createTenderReview.jsp?id=${tender.id}" class="button">Відгукнутися</a>
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
                    <c:if test="${user.userId eq proposal.author.userId}">
                        <a href="editProposal.jsp?proposalId=${proposal.id}&id=${proposal.tenderId}" class="button">Редагувати пропозицію</a>
                    </c:if>
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
