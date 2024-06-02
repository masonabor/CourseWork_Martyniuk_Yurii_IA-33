<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вхід до акаунту</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #222;
            color: #ddd;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 300px;
            margin: 50px auto;
            padding: 20px;
            background-color: #333;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }
        h2 {
            text-align: center;
            color: #ddd;
        }
        label {
            color: #ddd;
        }
        input[type="text"], input[type="password"] {
            width: calc(100% - 24px);
            padding: 10px;
            margin: 5px 0 20px;
            border: 1px solid #555;
            border-radius: 5px;
            background-color: #444;
            color: #fff;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            background-color: #008CBA;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #005f7f;
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
        .button:hover {
            background-color: #005f7f;
        }
        .error {
            color: red;
            margin-top: 5px;
        }
        .back-button {
            margin-top: 20px;
            background-color: #444;
        }
        .back-button:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Вхід до акаунту</h2>
    <form action="authorization" method="post">
        <label for="username">Логін:</label><br>
        <input type="text" id="username" name="username"><br>
        <label for="password">Пароль:</label><br>
        <input type="password" id="password" name="password"><br>
        <span class="error">${errorMessage}</span><br>
        <input type="hidden" id="tenderId" name="tenderId" value="${tenderId}">
        <input type="hidden" id="addressing" name="addressing" value="${addressing}">
        <input type="submit" id="login" value="Увійти">
    </form>

    <a href="registrationPage.jsp" class="button">Зареєструйтеся тут</a>
    <form action="homePage" method="get">
        <input type="submit" class="back-button" value="Повернутися на домашню сторінку">
    </form>
</div>
</body>
</html>

