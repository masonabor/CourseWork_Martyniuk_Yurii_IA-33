<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Пошук тендерів</title>
  <style>
    body { font-family: Arial, sans-serif; }
    .header a { text-decoration: none; color: blue; }
    .header a:hover { text-decoration: underline; }
    .tenders { margin-bottom: 40px; }
    .tender { margin-bottom: 20px; padding: 10px; border: 1px solid #ccc; border-radius: 5px; }
    .button { display: inline-block; padding: 10px 20px; margin-top: 10px; background-color: #008CBA; color: #fff; text-decoration: none; border-radius: 5px; transition: background-color 0.3s; }
    .button:hover { background-color: #005f7f; }
    #tenderList li { padding: 10px; border: 1px solid #ccc; border-radius: 5px; margin-bottom: 5px; cursor: pointer; }
    #tenderList li:hover { background-color: #f0f0f0; }
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
  </div>
<a href="homePage" class="button">Повернутися до списку тендерів</a>
<h1>Список знайдених тендерів</h1>
<div class="tenders">
  <c:forEach var="tender" items="${searchedTenders}">
    <div class="tender">
      <c:set var="tenderId" value="${tender.id}" />
      <h2>${tender.name}</h2>
      <p>${tender.author.login}</p>
      <p>${tender.description}</p>
      <p><fmt:formatNumber value="${tender.cost}" type="currency" currencySymbol="₴"/></p>
      <a href="tenderDetails.jsp?id=${tenderId}" class="button">Деталі тендеру</a>
      <c:if test="${tender.author ne user}">
        <a href="createProposal.jsp?id=${tenderId}" class="button">Створити пропозицію</a>
      </c:if>
      <c:if test="${tender.author ne user}">
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
