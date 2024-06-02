<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Tender" %>
<%@ page import="com.coursework.coursework.DAOs.TendersDAO" %>
<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Переможець тендеру</title>
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
            request.setAttribute("winnerProposal", tender.getWinnerProposal());
            request.setAttribute("tenderName", tender.getName());
        }
    } else {
        response.sendError(500, "Id тендеру відсутнє");
        return;
    }
%>

<div class="container">
    <h1>Переможець тендеру "${tenderName}"</h1>
    <div class="proposal">
        <p>Компанія: ${winnerProposal.companyName}</p>
        <p>Автор: ${winnerProposal.author.login}</p>
        <p>Деталі пропозиції: ${winnerProposal.proposalDetails}</p>
        <p>Ціна: <fmt:formatNumber value="${winnerProposal.price}" type="currency" currencySymbol="₴"/></p>
    </div>
    <a href="chatPage.jsp?chatId=${winnerProposal.chatId}" class="button">Написати автору пропозиції</a>
    <a href="homePage" class="button">Повернутися до списку тендерів</a>
</div>

</body>
</html>
