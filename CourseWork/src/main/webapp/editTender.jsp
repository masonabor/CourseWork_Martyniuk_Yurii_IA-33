<%@ page import="java.util.UUID" %>
<%@ page import="com.coursework.coursework.DAOs.TendersDAO" %>
<%@ page import="com.coursework.coursework.ServiceLayer.Tender" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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

<body>
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
</body>
</html>