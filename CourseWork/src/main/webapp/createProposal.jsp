<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Створення тендерної пропозиції</title>
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

        h1 {
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
        input[type="number"] {
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
<div class="container">
    <h1>Створення тендерної пропозиції</h1>
    <form action="createProposal" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <label for="companyName">Назва компанії:</label><br>
        <input type="text" id="companyName" name="companyName" required><br>
        <label for="proposalDetails">Деталі пропозиції:</label><br>
        <textarea id="proposalDetails" name="proposalDetails" rows="4" required></textarea><br>
        <label for="price">Ціна:</label><br>
        <input type="number" id="price" name="price" step="1000.0" required><br>
        <button type="submit">Створити пропозицію</button>
    </form>
</div>
</body>
</html>