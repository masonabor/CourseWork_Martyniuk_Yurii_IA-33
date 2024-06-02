<%@ page import="com.coursework.coursework.ServiceLayer.User" %>
<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Відгук на тендер</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #222;
            color: #ddd;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: #333;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }
        h1 {
            text-align: center;
            color: #fff;
        }
        form {
            margin-top: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #bbb;
        }
        input[type="text"],
        textarea,
        input[type="number"],
        input[type="date"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #555;
            border-radius: 5px;
            background-color: #444;
            color: #fff;
            box-sizing: border-box;
        }
        textarea {
            resize: vertical;
        }
        button[type="submit"] {
            background-color: #008CBA;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button[type="submit"]:hover {
            background-color: #005f7f;
        }
    </style>
</head>
<body>
<%
    String id = request.getParameter("id");

    if (id != null && !id.isEmpty()) {
        UUID tenderId = UUID.fromString(id);
        request.setAttribute("tenderId", tenderId);
    } else {
        response.sendError(500, "Id тендеру відсутнє");
        return;
    }

    User user = (User) request.getSession().getAttribute("user");

    if (user == null) {
        request.setAttribute("addressing", "review");
        request.setAttribute("tenderId", UUID.fromString(request.getParameter("id")));
        request.getRequestDispatcher("loginPage.jsp").forward(request, response);
        return;
    }
%>
<div class="container">
    <h1>Відгук на тендер</h1>
    <form action="createReview" method="get">
        <input type="hidden" id="tenderId" name="tenderId" value="${tenderId}">
        <label for="companyName">Назва компанії:</label>
        <input type="text" id="companyName" name="companyName" required>
        <label for="review">Перелік власних компетенцій:</label>
        <textarea id="review" name="review" rows="4" required></textarea>
        <label for="phoneNumber">Номер телефону:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" required>
        <button type="submit">Надіслати відгук</button>
    </form>
</div>
</body>
</html>
