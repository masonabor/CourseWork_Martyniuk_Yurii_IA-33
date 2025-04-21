<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.coursework.coursework.DAOs.TendersDAO" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Tender" %>
<%@ page import="com.coursework.coursework.ServiceLayer.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Редагування тендеру</title>
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
        form {
            margin-top: 20px;
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #ccc;
            text-align: left;
        }
        input[type="text"],
        textarea,
        input[type="number"],
        input[type="date"] {
            width: calc(100% - 24px);
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #555;
            border-radius: 5px;
            background-color: #444;
            color: #fff;
            box-sizing: border-box;
        }
        textarea {
            resize: vertical;
        }
        button {
            background-color: #008CBA;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin: 5px 0;
        }
        button:hover {
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
        assert user != null;

        if (!user.getUserId().equals(tender.getAuthor().getUserId())) {
            response.sendError(500, "ви не є власником тендеру");
        }
    } else {
        response.sendError(500, "Id тендеру відсутнє");
        return;
    }
%>

<div class="container">
    <h2>Редагування тендеру</h2>
    <form action="editTender" method="post">
        <input type="hidden" name="id" value="${tender.id}" />
        <label for="name">Назва тендеру:</label>
        <input type="text" id="name" name="name" value="${tender.name}" required/><br/>
        <label for="description">Опис тендеру:</label>
        <textarea id="description" name="description" required>${tender.description}</textarea><br/>
        <label for="deadline">Кінцевий термін:</label><br>
        <input type="date" id="deadline" name="deadline" value="${tender.deadline}" required><br>
        <label for="cost">Сума:</label>
        <input type="number" id="cost" name="cost" value="${tender.cost}" required/><br/>
        <button type="submit">Зберегти</button>
    </form>

    <form action="editTender" method="get" onsubmit="return confirm('Ви впевнені, що хочете видалити цей тендер?');">
        <input type="hidden" name="id" value="${tender.id}" />
        <button type="submit">Видалити</button>
    </form>

    <form action="changeTenderStatus" method="get">
        <input type="hidden" name="id" value="${tender.id}" />
        <button type="submit">
            <c:if test="${tender.status eq 'INACTIVE'}">
                Активувати
                <input type="hidden" name="newStatus" value="activate" />
            </c:if>
            <c:if test="${tender.status eq 'ACTIVE'}">
                Деактивувати
                <input type="hidden" name="newStatus" value="deactivate" />
            </c:if>
        </button>
    </form>
</div>

</body>
</html>


