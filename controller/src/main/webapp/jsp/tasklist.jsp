<!DOCTYPE html>
<!--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
-->
<html>
<head>
    <title>Список Задач</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="../jsp/menu.jsp" />
    <link href="../resources/css/tasklist.css" rel="stylesheet" type="text/css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/jquery-ui.min.js"></script>
    <script src="../resources/js/tasklist.js"></script>
</head>
<body>
    <div class="filter row">
        Фильтры
    </div>
    <div class="selectview row">
        <div class="view todo">To-do Line</div>
        <div class="view day">День</div>
        <div class="view week">Неделя</div>
        <div class="view month">Месяц</div>
        <div class="view list">Список</div>
        <a class="row addcontact" href="#">Добавить задачу</a>
        <div class="tasks todotasks">
            To-Do-Line<br>
            <c:forEach var="task" items="${tasklist}">
                ${task.comment};<br>
            </c:forEach>
        </div>
        <div class="tasks daytasks">
            День
        </div>
        <div class="tasks weektasks">
            Неделя
        </div>
        <div class="tasks monthtasks">
            Месяц
        </div>
        <div class="tasks listtasks">
            Список
        </div>
    </div>
    
</body>
</html>
