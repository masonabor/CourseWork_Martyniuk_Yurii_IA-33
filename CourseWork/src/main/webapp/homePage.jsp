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
    .tenders {
      margin-bottom: 40px;
    }
    .tender {
      margin-bottom: 20px;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
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
    .create-tender-button {
      display: inline-block;
      margin-bottom: 20px;
    }
    #keyword {
      width: 100%;
      padding: 10px;
      margin-bottom: 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    #tenderList {
      list-style: none;
      padding: 0;
    }
    #tenderList li {
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
      margin-bottom: 5px;
      cursor: pointer;
    }
    #tenderList li:hover {
      background-color: #f0f0f0;
    }
  </style>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script>
    $(document).ready(function() {
      $("#keyword").keyup(function() {
        let query = $(this).val();
        if (query.length > 2) {
          $.ajax({
            url: 'searchTender',
            method: 'GET',
            data: { keyword: query },
            success: function(response) {
              $("#tenderList").empty();
              let tenders = JSON.parse(response);
              tenders.forEach(function(tender) {
                let listItem = $("<li></li>").text(tender.name);
                listItem.data("id", tender.id);
                listItem.click(function() {
                  let tenderId = $(this).data("id");
                  window.location.href = "tenderDetails.jsp?id=" + tenderId;
                });
                $("#tenderList").append(listItem);
              });
            }
          });
        } else {
          $("#tenderList").empty();
        }
      });
    });
  </script>
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

  <h1>Список тендерів</h1>
  <label for="keyword">
    <input type="text" id="keyword" placeholder="Введіть ключове слово для пошуку">
  </label>
  <ul id="tenderList"></ul>

  <div class="tenders">
    <c:forEach var="tenderEntry" items="${tenders}">
      <div class="tender">
        <c:set var="tenderId" value="${tenderEntry.key}" />
        <h2>${tenderEntry.value.name}</h2>
        <p>${tenderEntry.value.getAuthor().login}</p>
        <p>${tenderEntry.value.description}</p>
        <p><fmt:formatNumber value="${tenderEntry.value.cost}" type="currency" currencySymbol="₴"/></p>
        <a href="tenderDetails.jsp?id=${tenderId}" class="button">Деталі тендеру</a>
        <a href="createProposal.jsp?id=${tenderId}" class="button">Створити пропозицію</a>
        <c:if test="${tenderEntry.value.getAuthorId() == user.getUserId()}">
          <a href="editTender.jsp?id=${tenderId}" class="button">Редагувати ваш тендер</a>
        </c:if>
      </div>
    </c:forEach>
  </div>
</div>
</body>
</html>
