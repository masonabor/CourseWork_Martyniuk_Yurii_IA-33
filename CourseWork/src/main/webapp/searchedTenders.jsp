<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
  <title>Пошук тендерів</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #222;
      color: #ddd;
      margin: 0;
      padding: 0;
    }
    .header {
      text-align: right;
      margin: 20px;
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
    .tenders {
      margin: 20px auto;
      max-width: 800px;
    }
    .tender {
      padding: 20px;
      margin-bottom: 20px;
      background-color: #333;
      border: 1px solid #555;
      border-radius: 8px;
    }
    .tender h2 {
      margin-top: 0;
      color: #ddd;
    }
    .tender p {
      margin: 5px 0;
      color: #ddd;
    }
    h1 {
      text-align: center;
      color: #fff;
    }
  </style>
</head>
<body>
<div class="header">
  <c:if test="${not empty user}">
    <p>Ви увійшли як: ${user.getLogin()}</p>
    <a href="userAccount.jsp" class="button">Кабінет користувача</a>
  </c:if>
  <c:if test="${empty user}">
    <a href="loginPage.jsp">Увійти</a>
  </c:if>
  <a href="homePage" class="button">Повернутися до списку тендерів</a>
</div>

<h1>Список знайдених тендерів</h1>
<div class="tenders">
  <c:forEach var="tender" items="${searchedTenders}">
    <div class="tender">
      <c:set var="tenderId" value="${tender.id}" />
      <h2>${tender.name}</h2>
      <p>${tender.author.login}</p>
      <p>${tender.description}</p>
      <p>${tender.deadline}</p>
      <p><fmt:formatNumber value="${tender.cost}" type="currency" currencySymbol="₴"/></p>
      <a href="tenderDetails.jsp?id=${tenderId}" class="button">Деталі тендеру</a>
      <c:if test="${tender.author ne user and not tender.isAfterDeadline()}">
        <a href="createProposal.jsp?id=${tenderId}" class="button">Створити пропозицію</a>
        <a href="createTenderReview.jsp?id=${tenderId}" class="button">Відгукнутися</a>
      </c:if>
      <c:if test="${tender.author == user}">
        <a href="editTender.jsp?id=${tenderId}" class="button">Редагувати ваш тендер</a>
      </c:if>
    </div>
  </c:forEach>
</div>
</body>
</html>


