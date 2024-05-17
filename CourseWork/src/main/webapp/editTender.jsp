<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.coursework.coursework.DAOs.TendersDAO" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Tender" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Tender.Status" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Редагування тендеру</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #333;
        }

        form {
            margin-top: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #666;
        }

        input[type="text"],
        textarea,
        input[type="number"],
        input[type="date"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        textarea {
            resize: vertical;
        }

        button[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button[type="submit"]:hover {
            background-color: #0056b3;
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
        } else {
            request.setAttribute("tender", tender);
        }
    } else {
        response.sendError(500, "Id тендеру відсутнє");
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
            <c:choose>
                <c:when test="${tender.status == Status.ACTIVE}">
                    Деактивувати
                    <input type="hidden" name="newStatus" value="deactivate" />
                </c:when>
                <c:otherwise>
                    Активувати
                    <input type="hidden" name="newStatus" value="activate" />
                </c:otherwise>
            </c:choose>
        </button>
    </form>
</div>

</body>
</html>

