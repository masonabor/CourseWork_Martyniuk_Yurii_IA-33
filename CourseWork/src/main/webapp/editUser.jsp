<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Редагування облікового запису</title>
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
        h1, h3 {
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
        input[type="password"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #555;
            border-radius: 5px;
            background-color: #444;
            color: #fff;
            box-sizing: border-box;
        }
        .message, .error {
            display: block;
            margin-bottom: 10px;
            color: #0f0;
        }
        .error {
            color: #f00;
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
        button:hover {
            background-color: #005f7f;
        }
        .return-button {
            background-color: #444;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            padding: 10px 20px;
            display: inline-block;
            transition: background-color 0.3s;
            margin-top: 20px;
        }

        .return-button:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Редагування облікового запису</h1>

    <form action="updateUsername" method="post">
        <h3>Редагування логіна</h3>
        <label for="newUsername">Новий логін:</label>
        <input type="text" id="newUsername" name="newUsername" required>
        <span class="message">${usernameMessage}</span>
        <span class="error">${usernameError}</span>
        <button type="submit">Оновити логін</button>
    </form>

    <form action="updatePassword" method="post">
        <h3>Редагування пароля</h3>
        <label for="newPassword">Новий пароль:</label>
        <input type="text" id="newPassword" name="newPassword" required>
        <span class="message">${passwordMessage}</span>
        <span class="error">${passwordError}</span>
        <button type="submit">Оновити пароль</button>
    </form>

    <h3>Дії з обліковим записом</h3>
    <form action="exitUser" method="get">
        <button type="submit">Вийти з облікового запису</button>
    </form>
    <form action="deleteUser" method="get">
        <button type="submit">Видалити обліковий запис</button>
    </form>

    <a href="userAccount.jsp" class="button return-button">Повернутися на сторінку користувача</a>

</div>
</body>
</html>
