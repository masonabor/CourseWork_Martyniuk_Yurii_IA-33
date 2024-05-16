<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="static com.coursework.coursework.ServiceLayer.PasswordGeneration.generatePassword" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Tender" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Реєстрація</title>
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
        .button:hover {
            background-color: #005f7f;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Зареєструйте свій акаунт тут</h2>
    <form action="registration" method="post">
        <label for="login">Логін:</label><br>
        <label for="username"></label><input type="text" id="username" name="username"><br>
        <label for="password">Пароль:</label><br>
        <input type="password" id="password" name="password" value="<%=generatePassword()%>"><br>
        <span class="error">${errorMessage}</span><br>
        <span class="message">${successMessage}</span><br>
        <input type="submit" id="login" value="Зареєстуватися">
    </form>
    <a href="loginPage.jsp" class="button">Ввійти</a>
    <a href="homePage" class="button">Повернутися на домашню сторінку</a>
</div>
</body>
</html>