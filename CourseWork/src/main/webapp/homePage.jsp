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
    body { font-family: Arial, sans-serif; }
    .container { max-width: 800px; margin: 0 auto; padding: 20px; }
    .header { text-align: right; margin-bottom: 20px; }
    .header a { text-decoration: none; color: blue; }
    .header a:hover { text-decoration: underline; }
    .tenders { margin-bottom: 40px; }
    .tender { margin-bottom: 20px; padding: 10px; border: 1px solid #ccc; border-radius: 5px; }
    .button { display: inline-block; padding: 10px 20px; margin-top: 10px; background-color: #008CBA; color: #fff; text-decoration: none; border-radius: 5px; transition: background-color 0.3s; }
    .button:hover { background-color: #005f7f; }
    .create-tender-button { display: inline-block; margin-bottom: 20px; }
    #keyword { width: 100%; padding: 10px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 5px; }
    #tenderList { list-style: none; padding: 0; }
    #tenderList li { padding: 10px; border: 1px solid #ccc; border-radius: 5px; margin-bottom: 5px; cursor: pointer; }
    #tenderList li:hover { background-color: #f0f0f0; }
  </style>
</head>
<body>
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
  <div class="create-tender-button">
    <a href="createTender.jsp" class="button">Створити тендер</a>
  </div>
  <form action="searchTender" method="GET">
    <label for="keyword"></label>
    <input type="text" id="keyword" name="keyword" placeholder="Введіть ключові слова">
    <input type="submit" value="Пошук" class="button">
  </form>
  <h1>Список тендерів</h1>
  <div class="tenders">
    <c:forEach var="tenderEntry" items="${tenders}">
      <div class="tender">
        <c:set var="tenderId" value="${tenderEntry.key}" />
        <h2>${tenderEntry.value.name}</h2>
        <p>${tenderEntry.value.author.login}</p>
        <p>${tenderEntry.value.description}</p>
        <p><fmt:formatNumber value="${tenderEntry.value.cost}" type="currency" currencySymbol="₴"/></p>
        <a href="tenderDetails.jsp?id=${tenderId}" class="button">Деталі тендеру</a>
        <c:if test="${tenderEntry.value.author ne user}">
          <a href="createProposal.jsp?id=${tenderId}" class="button">Створити пропозицію</a>
        </c:if>
        <c:if test="${tenderEntry.value.author ne user}">
          <a href="createTenderReview.jsp?id=${tenderId}" class="button">Відгукнутися</a>
        </c:if>
        <c:if test="${tenderEntry.value.author == user}">
          <a href="editTender.jsp?id=${tenderId}" class="button">Редагувати ваш тендер</a>
        </c:if>
      </div>
    </c:forEach>
  </div>
</div>
</body>
</html>
