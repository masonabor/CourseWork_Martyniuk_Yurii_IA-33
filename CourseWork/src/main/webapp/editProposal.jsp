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
        h2 {
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
        button[type="submit"], button[type="reset"] {
            background-color: #008CBA;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-right: 10px;
        }
        button[type="submit"]:hover, button[type="reset"]:hover {
            background-color: #005f7f;
        }
        .delete-button {
            background-color: #ff4d4d;
        }
        .delete-button:hover {
            background-color: #cc0000;
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

    String proposalId = request.getParameter("proposalId");
    String id = request.getParameter("id");

    if (id != null && !id.isEmpty() && proposalId != null && !proposalId.isEmpty()) {
        UUID tenderId = UUID.fromString(id);
        UUID propId = UUID.fromString(proposalId);
        TendersDAO tendersDAO = (TendersDAO) request.getServletContext().getAttribute("tendersDataBase");
        Tender tender = tendersDAO.getTenderById(tenderId);

        if (tender == null) {
            response.sendError(500, "Тендер за даним id не знайдено");
            return;
        }

        request.setAttribute("tender", tender);
        TenderProposal proposal = tender.findProposalById(propId);

        if (proposal == null) {
            response.sendError(500, "Пропозиція за даним id не знайдена");
            return;
        }
        request.setAttribute("proposal", proposal);

    } else {
        response.sendError(500, "Id тендеру відсутнє");
        return;
    }
%>

<div class="container">
    <h2>Редагування пропозиції</h2>
    <form action="editProposal" method="post">
        <input type="hidden" name="tenderId" value="${tender.id}">
        <input type="hidden" name="proposalId" value="${proposal.id}">
        <label for="companyName">Назва компанії:</label>
        <input type="text" id="companyName" name="companyName" value="${proposal.companyName}" required>
        <label for="proposalDetails">Деталі пропозиції:</label>
        <textarea id="proposalDetails" name="proposalDetails" rows="4" required>${proposal.proposalDetails}</textarea>
        <label for="price">Ціна:</label>
        <input type="number" id="price" name="price" step="1000.0" value="${proposal.price}" required>
        <div style="text-align: center;">
            <button type="submit">Зберегти зміни</button>
            <button type="reset">Скинути</button>
        </div>
    </form>

    <form action="editProposal" method="get" onsubmit="return confirm('Ви впевнені, що хочете видалити цю пропозицію?');">
        <input type="hidden" name="tenderId" value="${tender.id}">
        <input type="hidden" name="proposalId" value="${proposal.id}">
        <div style="text-align: center; margin-top: 10px;">
            <button type="submit" class="delete-button">Видалити</button>
        </div>
    </form>

</div>

</body>
</html>


