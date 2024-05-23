<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.coursework.coursework.DAOs.TendersDAO" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Tender" %>
<%@ page import="com.coursework.coursework.ServiceLayer.User" %>
<%@ page import="com.coursework.coursework.ServiceLayer.TenderProposal" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Редагування пропозиції</title>
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
    User user = (User) request.getSession().getAttribute("user");

    if (user == null) {
        response.sendError(500, "Ви не ввійшли");
    }

    String id = request.getParameter("id");
    String proposalId = request.getParameter("proposalId");

    if (id != null && !id.isEmpty() && proposalId != null && !proposalId.isEmpty()) {
        UUID tenderId = UUID.fromString(id);
        UUID propId = UUID.fromString(proposalId);
        TendersDAO tendersDAO = (TendersDAO) request.getServletContext().getAttribute("tendersDataBase");
        Tender tender = tendersDAO.getTenderById(tenderId);

        if (tender == null) {
            response.sendError(500, "Тендер за таким id не знайдено");
            return;
        }

        TenderProposal proposal = tender.findProposalById(propId);
        request.setAttribute("proposal", proposal);

    } else {
        response.sendError(500, "Id тендеру відсутнє");
    }



%>

<div class="container">
    <h2>Редагування пропозиції</h2>
    <form action="editProposal" method="post">
        <input type="hidden" name="id" value="${proposal.tenderId}">
        <input type="hidden" name="proposalId" value="${proposal.id}">
        <label for="companyName">Назва компанії:</label><br>
        <input type="text" id="companyName" name="companyName" required><br>
        <label for="proposalDetails">Деталі пропозиції:</label><br>
        <textarea id="proposalDetails" name="proposalDetails" rows="4" required></textarea><br>
        <label for="price">Ціна:</label><br>
        <input type="number" id="price" name="price" step="1000.0" required><br>
        <button type="submit">Створити пропозицію</button>
    </form>

    <form action="editProposal" method="get" onsubmit="return confirm('Ви впевнені, що хочете видалити цю пропозицію?');">
        <input type="hidden" name="proposalId" value="${proposal.id}" />
        <button type="submit">Видалити</button>
    </form>
</div>

</body>
</html>

