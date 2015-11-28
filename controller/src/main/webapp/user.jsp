<%--
  Created by IntelliJ IDEA.
  User: kramar
  Date: 25.11.15
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<form method="POST" action='userController' name="frmAddUser">

    Имя : <input
        type="text" name="name"
        value="<c:out value="${user.name}" />" /> <br />
    Логин : <input
        type="text" name="login"
        value="<c:out value="${user.login}" />" /> <br />
    Пароль : <input
        type="text" name="password"
        value="<c:out value="${user.password}" />" /> <br />
    E-mail : <input
        type="text" name="email"
        value="<c:out value="${user.email}" />" /> <br />
    Телефон мобильный : <input
        type="text" name="phone_mob"
        value="<c:out value="${user.phone_mob}" />" /> <br />
    Телефон рабочий: <input
        type="text" name="phone_work"
        value="<c:out value="${user.phone_work}" />" /> <br />
    Язык: <select name="language">
        <option value="RU">Русский</option>
        <option value="EN">Английский</option>
        </select><br />

    <select name="company">
        <c:forEach var="company" items="${companies}">
            <option value=${company.id}>${company.name}</option>
        </c:forEach>
    </select><br />


    <input type="submit" value="Записать" />

</form>

</body>
</html>
