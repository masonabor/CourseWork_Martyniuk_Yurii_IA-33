<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Сторінка користувача</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }

        .container {
            width: 300px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2, h3 {
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

        label {
            color: #008CBA;
        }
        .error {
            color: red;
            margin-top: 5px;
        }
        .message {
            color: green;
            margin-top: 5px;
        }
        .button {
            height: 35px;
            width: 100%;
            margin-top: 20px;
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

    </style>
</head>
<body>
<h2>Інформація про користувача</h2>
<p>Логін: ${user.login}</p>

<h3>Тендери</h3>
<c:if test="${empty user.tenders}">
    <h2>У вас поки немає тендерів</h2>
</c:if>
<c:forEach var="tenderEntry" items="${user.tenders}">
    <div class="tender">
        <c:set var="tenderId" value="${tenderEntry.key}" />
        <h2>${tenderEntry.value.name}</h2>
        <p>${tenderEntry.value.author.login}</p>
        <p>${tenderEntry.value.description}</p>
        <p><fmt:formatNumber value="${tenderEntry.value.cost}" type="currency" currencySymbol="₴"/></p>
        <a href="tenderDetails.jsp?id=${tenderId}" class="button">Деталі тендеру</a>
        <a href="checkReviews.jsp?id=${tenderId}" class="button">Переглянути відгуки</a>
        <a href="editTender.jsp?id=${tenderId}" class="button">Редагувати ваш тендер</a>
    </div>
</c:forEach>

<h3>Тендерні пропозиції</h3>
<span class="message"${editProposalSuccess}></span><br>
<c:if test="${empty user.tenderProposals}">
    <h2>У вас поки немає пропозицій</h2>
</c:if>
<c:if test="${not empty user.tenderProposals}">
    <c:forEach var="proposalEntry" items="${user.tenderProposals.key}">
        <h2>${proposalEntry.value.companyName}</h2>
        <p>${proposalEntry.value.proposalDetails}</p>
        <p><fmt:formatNumber value="${proposalEntry.value.price}" type="currency" currencySymbol="₴"/></p>
        <a href="editProposal.jsp?proposalId=${proposalEntry.value.id}&id=${proposalEntry.value.tenderId}" class="button">Редагувати пропозицію</a>
    </c:forEach>
</c:if>

<form action="updateUsername" method="post">
    <h3>Редагування логіна</h3>
    <label for="newUsername">Новий логін:</label>
    <input type="text" id="newUsername" name="newUsername">
    <span class="message">${usernameMessage}</span><br>
    <span class="error">${usernameError}</span><br>
    <input type="submit" value="Оновити логін">
</form>

<form action="updatePassword" method="post">
    <h3>Редагування пароля</h3>
    <label for="newPassword">Новий пароль:</label>
    <input type="text" id="newPassword" name="newPassword">
    <span class="message">${passwordMessage}</span><br>
    <span class="error">${passwordError}</span><br>
    <input type="submit" value="Оновити пароль">
</form>

<h3>Вихід з  облікового запису</h3>
<form action="exitUser" method="get">
    <input type="submit" value="Вийти з облікового запису">
</form>

<h3>Видалення облікового запису</h3>
<form action="deleteUser" method="get">
    <input type="submit" value="Видалити обліковий запис">
</form>

<a href="homePage" class="button">Повернутися на домашню сторінку</a>

</body>
</html>
