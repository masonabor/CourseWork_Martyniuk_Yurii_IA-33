<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сторінка користувача</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #222;
            color: #ddd;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #333;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }

        h2, h3 {
            color: #008CBA;
        }

        label {
            color: #008CBA;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .message {
            color: green;
            margin-top: 5px;
        }

        .button {
            height: 30px;
            width: 100%;
            margin-top: 10px;
            background-color: #008CBA;
            color: #fff;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .button:hover {
            background-color: #005f7f;
        }

        .tender, .proposal {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #444;
            border: 1px solid #555;
            border-radius: 8px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Інформація про користувача</h2>
    <p>Логін: ${user.login}</p>
    <a href="editUser.jsp" class="button">Редагувати обліковий запис</a>
    <h2>Тендери</h2>
    <span class="message">${chooseWinnerSuccess}</span><br>

    <form action="searchUserTenders" method="GET">
        <label for="keyword"></label>
        <input type="text" id="keyword" name="keyword" placeholder="Введіть ключові слова">
        <input type="submit" value="Пошук">
    </form>

    <c:if test="${empty user.tenders}">
        <h3>У вас поки немає тендерів</h3>
    </c:if>
    <c:forEach var="tenderEntry" items="${user.tenders}">
        <div class="tender">
            <c:set var="tenderId" value="${tenderEntry.key}" />
            <h2>${tenderEntry.value.name}</h2>
            <p>${tenderEntry.value.author.login}</p>
            <p>${tenderEntry.value.description}</p>
            <p><fmt:formatNumber value="${tenderEntry.value.cost}" type="currency" currencySymbol="₴"/></p>
            <c:if test="${not empty tenderEntry.value.winnerProposal}">
                <a href="winnerProposal.jsp?id=${tenderId}" class="button">Переможець тендеру</a>
            </c:if>
            <a href="tenderDetails.jsp?id=${tenderId}" class="button">Деталі тендеру</a>
            <a href="checkReviews.jsp?id=${tenderId}" class="button">Переглянути відгуки</a>
            <a href="editTender.jsp?id=${tenderId}" class="button">Редагувати ваш тендер</a>
            <c:if test="${tenderEntry.value.isAfterDeadline() and empty tenderEntry.value.winnerProposal}">
                <a href="chooseWinner.jsp?id=${tenderId}" class="button">Вибір переможця</a>
            </c:if>
        </div>
    </c:forEach>

    <h2>Тендерні пропозиції</h2>
    <span class="message">${editProposalSuccess}</span><br>
    <c:if test="${empty user.tenderProposals}">
        <h3>У вас поки немає пропозицій</h3>
    </c:if>
    <c:if test="${not empty user.tenderProposals}">
        <c:forEach var="proposalEntry" items="${user.tenderProposals}">
            <div class="proposal">
                <h2>${proposalEntry.value.companyName}</h2>
                <p>${proposalEntry.value.proposalDetails}</p>
                <p><fmt:formatNumber value="${proposalEntry.value.price}" type="currency" currencySymbol="₴"/></p>
                <a href="editProposal.jsp?proposalId=${proposalEntry.value.id}&id=${proposalEntry.value.tenderId}" class="button">Редагувати пропозицію</a>
                <c:if test="${not empty proposalEntry.value.chatId}">
                    <a href="chatPage.jsp?chatId=${proposalEntry.value.chatId}" class="button">Написати автору пропозиції</a>
                </c:if>
            </div>
        </c:forEach>
    </c:if>

    <a href="homePage" class="button">Повернутися на домашню сторінку</a>
</div>
</body>
</html>

