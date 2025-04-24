<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.coursework.coursework.ServiceLayer.User" %>
<%@ page import="com.coursework.coursework.DAOs.ChatDAO" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Chat" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Повідомлення</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #222;
            color: #ddd;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        .container {
            max-width: 1000px;
            margin: 20px 30%;
            padding: 20px 20px 60px;
            background-color: #333;
            border-radius: 8px;
            flex: 1;
            display: flex;
            flex-direction: column;
        }
        .messages {
            flex: 1;
            display: flex;
            flex-direction: column;
            gap: 10px;
            overflow-y: auto;
            width: 100%;
            padding: 10px;
        }
        .message-item {
            padding: 10px;
            border-radius: 10px;
            max-width: 60%;
            word-wrap: break-word;
            clear: both;
            display: inline-block;
        }
        .from-user {
            background-color: #28a745;
            color: white;
            align-self: flex-end;
            text-align: right;
            border-top-right-radius: 0;
        }
        .from-other {
            background-color: #444;
            color: #ddd;
            align-self: flex-start;
            text-align: left;
            border-top-left-radius: 0;
        }
        .form-container {
            width: 100%;
            background-color: #333;
            border-top: 1px solid #555;
            padding: 10px 20px;
            display: flex;
            justify-content: center;
            position: fixed;
            bottom: 0;
        }
        .form-container form {
            display: flex;
            max-width: 1000px;
            width: 100%;
        }
        .form-container input[type="text"] {
            flex: 1;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #555;
            border-radius: 4px;
            margin-right: 10px;
            background-color: #444;
            color: #ddd;
        }
        .form-container input[type="submit"] {
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            background-color: #28a745;
            color: white;
            cursor: pointer;
        }
        .form-container input[type="submit"]:hover {
            background-color: #218838;
        }
        .error {
            color: red;
            margin-top: 5px;
        }
    </style>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("user");

    if (user == null) {
        response.sendError(500, "Ви не ввійшли");
        return;
    }

    String id = request.getParameter("chatId");

    if (id == null || id.isEmpty()) {
        response.sendError(500, "Id чату відсутнє");
        return;
    }

    UUID chatId = UUID.fromString(id);
    ChatDAO chatsDataBase = (ChatDAO) request.getServletContext().getAttribute("chatsDataBase");
    Chat chat = chatsDataBase.getChatById(chatId);
    Set<String> usersLogin = chat.getUsers()
            .stream()
            .map(User::getLogin)
            .collect(Collectors.toSet());

    if (!usersLogin.contains(user.getLogin())) {
        response.sendError(500, "Ви не є учасником цього чату");
        return;
    }

    request.setAttribute("chat", chat);
    request.setAttribute("userLogin", user.getLogin());
%>
<div class="container">
    <div class="messages">
        <c:choose>
            <c:when test="${empty chat.chat}">
                <h2>У цьому чаті ще немає повідомлень</h2>
            </c:when>
            <c:otherwise>
                <c:forEach var="message" items="${chat.chat}">
                    <c:choose>
                        <c:when test="${message.userLogin == userLogin}">
                            <div class="message-item from-user">
                                <p><strong>${message.userLogin}:</strong> ${message.message}</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="message-item from-other">
                                <p><strong>${message.userLogin}:</strong> ${message.message}</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<div class="form-container">
    <form action="writeMessage" method="post">
        <label>
            <span class="error">${errorMessage}</span><br>
            <input type="text" name="message" placeholder="Введіть ваше повідомлення" required>
        </label>
        <input type="hidden" name="chatId" value="${chat.chatId}">
        <input type="submit" value="Відправити">
    </form>
</div>
</body>
</html>
