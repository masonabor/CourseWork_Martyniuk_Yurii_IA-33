<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Домашня сторінка</title>
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
    .create-tender-button {
      text-align: right;
      margin-bottom: 20px;
    }
    .create-tender-button a {
      color: #fff;
      text-decoration: none;
      padding: 10px 20px;
      background-color: #008CBA;
      border-radius: 5px;
      transition: background-color 0.3s;
    }
    .create-tender-button a:hover {
      background-color: #005f7f;
    }
    form {
      text-align: center;
      margin-bottom: 40px;
    }
    input[type="text"] {
      width: calc(100% - 24px);
      padding: 10px;
      margin-bottom: 20px;
      border: 1px solid #555;
      border-radius: 5px;
      background-color: #444;
      color: #fff;
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
    .tenders {
      margin-bottom: 40px;
    }
    .tender {
      padding: 10px;
      border: 1px solid #555;
      border-radius: 5px;
      margin-bottom: 20px;
      background-color: #444;
    }
    .tender h2 {
      margin-top: 0;
    }
    .tender p {
      margin: 5px 0;
    }
    .tender a {
      color: #fff;
      text-decoration: none;
      padding: 5px 10px;
      background-color: #008CBA;
      border-radius: 5px;
      transition: background-color 0.3s;
    }
    .tender a:hover {
      background-color: #005f7f;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="header">
    <c:if test="${not empty user}">
      <p>Ви увійшли як: ${user.getLogin()}</p>
      <a href="userAccount.jsp">Кабінет користувача</a>
    </c:if>
    <c:if test="${empty user}">
      <a href="loginPage.jsp">Увійти</a>
    </c:if>
    <div class="create-tender-button">
      <a href="createTender.jsp">Створити тендер</a>
    </div>
  </div>

  <form action="searchTender" method="GET">
    <label for="keyword"></label>
    <input type="text" id="keyword" name="keyword" placeholder="Введіть ключові слова">
    <input type="submit" value="Пошук">
  </form>

  <h1>Список тендерів</h1>
  <div class="tenders">
    <c:forEach var="tenderEntry" items="${tenders}">
      <div class="tender">
        <c:if test="${tenderEntry.value.status eq 'ACTIVE'}">
          <c:set var="tenderId" value="${tenderEntry.key}" />
          <h2>${tenderEntry.value.name}</h2>
          <p>${tenderEntry.value.author.login}</p>
          <p>${tenderEntry.value.description}</p>
          <p><fmt:formatNumber value="${tenderEntry.value.cost}" type="currency" currencySymbol="₴"/></p>
          <a href="tenderDetails.jsp?id=${tenderId}" class="button">Деталі тендеру</a>
          <c:if test="${tenderEntry.value.author ne user and not tenderEntry.value.isAfterDeadline()}">
            <a href="createProposal.jsp?id=${tenderId}" class="button">Створити пропозицію</a>
            <a href="createTenderReview.jsp?id=${tenderId}" class="button">Відгукнутися</a>
          </c:if>
          <c:if test="${tenderEntry.value.author == user}">
            <a href="editTender.jsp?id=${tenderId}" class="button">Редагувати ваш тендер</a>
          </c:if>
        </c:if>
      </div>
    </c:forEach>
  </div>
</div>
</body>
</html>
