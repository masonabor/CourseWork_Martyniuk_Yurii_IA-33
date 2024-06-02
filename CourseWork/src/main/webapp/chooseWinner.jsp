<%@ page import="java.util.UUID" %>
<%@ page import="com.coursework.coursework.DAOs.TendersDAO" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Tender" %>
<%@ page import="com.coursework.coursework.ServiceLayer.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вибір переможця</title>
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
        h1 {
            text-align: center;
        }
        .proposal {
            padding: 10px;
            border: 1px solid #555;
            border-radius: 5px;
            margin-bottom: 20px;
            background-color: #444;
        }
        .proposal h3 {
            margin-top: 0;
        }
        .proposal p {
            margin: 5px 0;
        }
        input[type="submit"] {
            padding: 10px 20px;
            background-color: #008CBA;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #005f7f;
        }
    </style>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("user");

    if (user == null) {
        response.sendError(500, "ви не ввійшли");
    }

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

        assert tender != null;

        if (user != tender.getAuthor()) {
            response.sendError(500, "ви не є власником тендеру");
            return;
        }
    } else {
        response.sendError(500, "Id тендеру відсутнє");
        return;
    }
%>
<div class="container">
    <div class="header">
        <c:if test="${not empty user}">
            <p>Ви увійшли як: ${user.getLogin()}</p>
            <a href="userAccount.jsp">Кабінет користувача</a>
        </c:if>
        <c:if test="${empty user}">
            <a href="loginPage.jsp">Увійти</a>
        </c:if>
    </div>
    <h1>Кандидати в переможці</h1>
    <c:if test="${not empty tender.tenderProposals}">
        <c:forEach var="proposal" items="${tender.tenderProposals}">
            <div class="proposal">
                <h3>${proposal.companyName}</h3>
                <p>${proposal.proposalDetails}</p>
                <p>${proposal.author.login}</p>
                <p><fmt:formatNumber value="${proposal.price}" type="currency" currencySymbol="₴"/></p>
                <form action="chooseWinner" method="post">
                    <input type="hidden" name="winnerId" value="${proposal.id}">
                    <input type="hidden" name="tenderId" value="${tender.id}">
                    <input type="submit" value="Вибрати переможцем">
                </form>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty tender.tenderProposals}">
        <h3>Немає тендерних пропозицій</h3>
    </c:if>
</div>
</body>
</html>

