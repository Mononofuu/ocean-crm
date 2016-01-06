<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='../css/bootstrap.css' %>
</style>
<!DOCTYPE html>
<html>
<head>
    <title>Редактирование этапов</title>
</head>
<body>
<div class="col-lg-2">
    <jsp:include page="menu.jsp"/>
</div>
<div class="col-lg-10">
    <div class="row">
        <p class="h1">Редактирование этапов</p>
    </div>
    <table class="table table-hover">
        <c:forEach items="${statusList}" var="entry">
            <tr>
                <td>${entry.id}</td>
                <td>${entry.name}</td>
                <td style="background-color: ${entry.color}"></td>
                <td><a href="deal_status?action=edit&id=${entry.id}">Edit</a></td>
                <c:if test="${!entry.systemDefault}">
                    <td><a href="deal_status?action=delete&id=${entry.id}">Delete</a></td>
                </c:if>
            </tr>
        </c:forEach>
        <form action="deal_status?action=create" method="post">
            <tr>
                <td><input name="name" class="text-input" type="text" value="Название этапа"></td>
                <td>
                    Цвет:
                    <select name="color" title="Цвет">
                        <option value="#0040ff" style="background-color: #0040ff"></option>
                        <option value="#7f00ff" style="background-color: #7f00ff"></option>
                        <option value="#ffff00" style="background-color: #ffff00"></option>
                        <option value="#80ff00" style="background-color: #80ff00"></option>
                        <option value="#00ff00" style="background-color: #00ff00"></option>
                        <option value="#985f0d" style="background-color: #985f0d"></option>
                        <option value="#8b211e" style="background-color: #8b211e"></option>
                        <option value="#2aabd2" style="background-color: #2aabd2"></option>
                        <option value="#28a4c9" style="background-color: #28a4c9"></option>
                        <option value="#419641" style="background-color: #419641"></option>
                        <option value="#800080" style="background-color: #800080"></option>
                        <option value="#8a6d3b" style="background-color: #8a6d3b"></option>
                        <option value="#a24230" style="background-color: #a24230"></option>
                        <option value="#232452" style="background-color: #232452"></option>
                        <option value="#425324" style="background-color: #425324"></option>
                        <option value="#113532" style="background-color: #113532"></option>
                        <option value="#880028" style="background-color: #880028"></option>
                        <option value="#000099" style="background-color: #000099"></option>
                        <option value="#00fff0" style="background-color: #00fff0"></option>
                    </select>
                </td>
                <td>
                    <button class="btn btn-default" type="submit">Добавить этап</button>
                </td>
            </tr>
        </form>
    </table>
</div>
</body>
</html>
